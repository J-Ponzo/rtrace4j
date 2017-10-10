package fr.gamagora.jponzo.rtrace4j.rendering;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import fr.gamagora.jponzo.rtrace4j.collision.CollisionSystem;
import fr.gamagora.jponzo.rtrace4j.model.ModelService;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ICamera;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IInterInfo;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ILight;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IMaterial;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IPrimitive;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IRay;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ISampleInfo;
import fr.gamagora.jponzo.rtrace4j.utils.impl.DebugUtils;
import fr.gamagora.jponzo.rtrace4j.utils.impl.IOUtils;
import fr.gamagora.jponzo.rtrace4j.utils.impl.MathUtils;
import fr.gamagora.jponzo.rtrace4j.utils.impl.VectorUtils;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;
import fr.jponzo.gamagora.rtrace4j.scene.SceneManager;
import fr.jponzo.gamagora.rtrace4j.scene.interfaces.IScene;

public class RenderingSystem {
	public static int nbThreads = 8;
	public static int smoothShadowSampling = 1;
	public static int maxLightConsidered = 1;
	public static int indirectLightSampling = 1;
	public static int rayPerPixel = 10;
	public static int maxRayBounces = 5;

	/**
	 * Recursively cast rays across the scene in order to fill the frame buffer with pixel colors.
	 * Each pixels are shared among a fixed number of threads.
	 * @param w the width of the image
	 * @param h the height of the image
	 * @param imgTable the table storing the pixels
	 * @param camPosition the position of the camera
	 * @param fovRad the field of view of the camera (expressed in rads)
	 * @param scene the scene through witch the rays are cast
	 */
	public static void parallLaunchRays() {
		List<Thread> threads = new ArrayList<Thread>();
		List<CastRaysTask> runnables = new ArrayList<CastRaysTask>();

		IScene scene = SceneManager.getScene();
		ICamera camera = scene.getCamera();
		int w = camera.getWidth();
		int h = camera.getHeight(); 
		int[][][] imgTable = camera.getImgTable();

		long time = System.currentTimeMillis();

		//Init Threads
		for (int id = 0; id < nbThreads; id++) {
			runnables.add(new CastRaysTask(id, nbThreads, scene, maxRayBounces));
		}
		for (Runnable runnable : runnables) {
			threads.add(new Thread(runnable));
		}

		//Launch threads
		for (Thread thread : threads) {
			thread.start();
		}

		//Wait threads
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		//Gather computed color values
		for (CastRaysTask runnable : runnables) {
			IVec3[][] imgTableFromThread = runnable.getImgTable();
			for (int i = runnable.getId(); i < h; i += runnable.getNbThreads()) {
				for (int j = 0; j < w; j++) {
					IVec3 color = imgTableFromThread[i][j];
					if (color != null) {
						imgTable[h - 1 - i][j][0] = (int) (color.getX() * 255f);
						imgTable[h - 1 - i][j][1] = (int) (color.getY() * 255f);
						imgTable[h - 1 - i][j][2] = (int) (color.getZ() * 255f);
					}
				}
			}
		}

		long elapsed = System.currentTimeMillis() - time;
		System.out.println("Rendered time " + elapsed +"ms ");
	}

	/**
	 * Recursively cast a single ray across the scene in order to find to determine the color of
	 * the corresponding pixel
	 * @param scene the scene through witch the rays are cast 
	 * @param ray the ray to cast
	 * @param maxRec max recursion stages
	 * @return the color of the corresponding pixel
	 */
	public static IVec3 castRay(IScene scene, IRay ray, int maxRec) {
		//prevent endless looping
		if (maxRec < 0) {
			return DebugUtils.getMaxRecursiveStagesColor();
		}

		//Look for intersection
		IInterInfo intersect;
		try {
			intersect = CollisionSystem.intersect(ray);
			if (intersect != null) {
				IPrimitive primitive = intersect.getPrimitive();
				IMaterial material = primitive.getMaterial();
				ISampleInfo sampleInfo = material.sample(ray, intersect);

				IVec3 indirectLighting = ModelService.createVec3(0, 0, 0);
				float contrib = sampleInfo.getContribution();
				contrib = IOUtils.clamp(0, 1, contrib);				//TODO hacky workaround preventing fire flies
				if (contrib > 0) {
					IRay bouncingRay = sampleInfo.getRay();
					IVec3 Li = castRay(scene, bouncingRay, maxRec - 1);
					indirectLighting = material.getAlbedo().mult(Li).mult(contrib);	
				}

				int nbLights = scene.getLights().size();
				int indLight = (int) (Math.random() * nbLights);
				IVec3 Lo = computeLo(scene, intersect, scene.getLights().get(indLight));
				IVec3 directLighting = material.getAlbedo().mult(Lo);

				return directLighting.sum(indirectLighting);
			}
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		}
		return DebugUtils.getClearColor();
	}

	/**
	 * Compute the direct illumination of given light on scene and a light
	 * @param scene the given scene
	 * @param intersect the given intersect
	 * @param light the given light
	 * @return the amount of direct light received from this light
	 * @throws OperationNotSupportedException
	 */
	private static IVec3 computeLo(IScene scene, IInterInfo intersect, ILight light) throws OperationNotSupportedException {
		IVec3 intensity = ModelService.createVec3(0, 0, 0);
		float r1, r2;
		for (int i = 0; i < smoothShadowSampling; i++) {
			r1 = (float) Math.random();
			r2 = (float) Math.random();
			IVec3 randPt = MathUtils.randomizePointOnSphere(
					light.getPosition().getX(), 
					light.getPosition().getY(), 
					light.getPosition().getZ(), 
					light.getRadius(), r1, r2);

			float V = 1;
			IVec3 n = intersect.getNormal();
			IVec3 displacedIntersect = intersect.getPoint().sum(n.mult(VectorUtils.EPS * 1000));
			IVec3 position = displacedIntersect;
			if (!scene.isVisibleFrom(position, randPt)) {
				V = 0;
			}

			IVec3 I = position;
			IVec3 L = VectorUtils.computeDirection(I, randPt).normalized();
			float d2 = VectorUtils.computeDistance(I, randPt);
			d2 *= d2;
			IMaterial mat = intersect.getPrimitive().getMaterial();
			float G = mat.f(L, n) / d2;
			if (G < 0) {	//means light comes from the wrong side of the side of the face
				G = 0;
			}
			intensity = intensity.sum(light.getIntensity().mult(V * G / (float)Math.PI));
		}

		intensity = intensity.mult(1f / ((float) smoothShadowSampling));
		intensity.mult(1f / (float) scene.getLights().size());
		return intensity;
	}
}
