package fr.gamagora.jponzo.rtrace4j.rendering;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.naming.OperationNotSupportedException;

import fr.gamagora.jponzo.rtrace4j.model.ModelService;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ICamera;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IInterInfo;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ILight;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IPrimitive;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IRay;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IScene;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ISphere;
import fr.gamagora.jponzo.rtrace4j.utils.impl.DebugUtils;
import fr.gamagora.jponzo.rtrace4j.utils.impl.MathUtils;
import fr.gamagora.jponzo.rtrace4j.utils.impl.Vec3;
import fr.gamagora.jponzo.rtrace4j.utils.impl.VectorUtils;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

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

		IScene scene = ModelService.getScene();
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
			intersect = scene.intersect(ray);
			if (intersect != null) {
				IPrimitive primitive = intersect.getPrimitive();
				float diffVal = primitive.getDiffVal();
				float specVal = primitive.getSpecVal();
				float transVal = primitive.getTransVal();

				//Compute diffuse, specular & transparent colors
				float diffMin = 0f; 
				float diffMax = diffVal;
				float specMin = diffVal; 
				float specMax = diffVal + specVal;
				float transMin = diffVal + specVal; 
				float transMax = diffVal + specVal + transVal;
				float r = (float) Math.random();

				IVec3 color = null;
				if (r < diffMax && r > diffMin) {
					color = illumDiffuse(scene, intersect, maxLightConsidered, maxRec);
				}
				else if (r < specMax && r > specMin) {
					color = reflectRay(scene, ray, intersect, maxRec);
				}
				else if (r < transMax && r > transMin) {
					color = refractRay(scene, ray, intersect, maxRec);
				}

				if (color != null) {
					return color;
				} else {
					return DebugUtils.getShadowColor();
				}
			}
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		}
		return DebugUtils.getClearColor();
	}

	/**
	 * Recursive Call performing a ray refraction across the scene given the ray and the intersection informations
	 * @param scene the given scene
	 * @param ray the incident ray to refract
	 * @param intersect the given intersection
	 * @param maxRec max recursion stages
	 * @return the color of the recursively refracted ray
	 */
	private static IVec3 refractRay(IScene scene, IRay ray, IInterInfo intersect, int maxRec) {
		float n1 = 1;		//Air refraction
		float n2 = intersect.getPrimitive().getRefractIndex();

		IVec3 displacedIntersect;
		IVec3 intersectPt = intersect.getPoint();
		IVec3 n = intersect.getNormal();

		//Detect in/out hits
		IVec3 refractedDir;
		if (n.dot(ray.getDirection()) > 0) {
			refractedDir = VectorUtils.computeRefractedRay(ray.getDirection().normalized(), n, n1, n2);
			displacedIntersect = intersectPt.sum(n.mult(VectorUtils.EPS * 1000));
		} else {
			refractedDir = VectorUtils.computeRefractedRay(ray.getDirection().normalized(), n, n2, n1);
			displacedIntersect = intersectPt.sum(n.mult(-VectorUtils.EPS * 1000));
		}

		IRay refractedRay = ModelService.createRay(displacedIntersect, refractedDir);
		IVec3 refColor = castRay(scene, refractedRay, maxRec - 1);
		if (refColor != null) {
			return refColor.mult(0.9f);
		} else {
			return null;
		}
	}

	/**
	 * Recursive Call performing a ray reflection across the scene given the ray and the intersection informations
	 * @param scene scene the given scene
	 * @param ray the incident ray to reflect
	 * @param intersect the given intersection info
	 * @param maxRec max recursion stages
	 * @return the color of the recursively reflected ray
	 */
	private static IVec3 reflectRay(IScene scene, IRay ray, IInterInfo intersect, int maxRec) {
		IVec3 n = intersect.getNormal();
		IVec3 displacedIntersect = intersect.getPoint().sum(n.mult(VectorUtils.EPS * 1000));

		IVec3 reflectedDir = VectorUtils.computeReflectedRay(ray.getDirection(), n);

		IRay reflectedRay = ModelService.createRay(displacedIntersect , reflectedDir);
		IVec3 refColor = castRay(scene, reflectedRay, maxRec - 1);
		if (refColor != null) {
			return refColor.mult(0.8f);
		} else {
			return null;
		}
	}

	/**
	 * Compute the actual intensity to apply at the given intersection, given the normal at this position and the light considered.
	 * The Lambertian model is used
	 * @param scene the given scene
	 * @param intersect the given intersection
	 * @param light the light considered
	 * @return the actual light intensity at this intersection
	 * @throws OperationNotSupportedException 
	 */
	private static IVec3 lambertianPointLight(IScene scene, IInterInfo intersect, ILight light) throws OperationNotSupportedException {
		IVec3 n = intersect.getNormal();
		IVec3 displacedIntersect = intersect.getPoint().sum(n.mult(VectorUtils.EPS * 1000));
		IVec3 position = displacedIntersect;
		if (!scene.isVisibleFrom(position, light.getPosition())) {
			return ModelService.createVec3(0, 0, 0);
		}

		IVec3 I = position;
		IVec3 L = VectorUtils.computeDirection(I, light.getPosition()).normalized();
		float d2 = VectorUtils.computeDistance(I, light.getPosition());
		d2 *= d2;
		float nDotL = n.dot(L);
		if (nDotL < 0) {	//means light comes from the wrong side of the side of the face
			return ModelService.createVec3(0, 0, 0);
		}
		IVec3 intensity = light.getIntensity();
		IVec3 lambertIntensity = intensity.mult((nDotL) / d2);
		return lambertIntensity;
	}

	/**
	 * Compute the actual intensity to apply at the given intersection, given the normal at this position and the spheric light considered.
	 * The Lambertian model is used.
	 * @param scene the given scene
	 * @param intersect the given intersection
	 * @param light the light considered
	 * @param nbSample number of randomized points on the sphere border
	 * @return the actual light intensity at this intersection
	 * @throws OperationNotSupportedException
	 */
	private static IVec3 lambertianSphericLight(IScene scene,  IInterInfo intersect, ILight light, int nbSample) throws OperationNotSupportedException {
		IVec3 lambertIntensity = ModelService.createVec3(0, 0, 0);
		float r1, r2;
		for (int i = 0; i < nbSample; i++) {
			r1 = (float) Math.random();
			r2 = (float) Math.random();
			IVec3 randPt = MathUtils.randomizePointOnSphere(
					light.getPosition().getX(), 
					light.getPosition().getY(), 
					light.getPosition().getZ(), 
					light.getRadius(), r1, r2);
			lambertIntensity = lambertIntensity.sum(
					lambertianPointLight(
							scene, intersect, 
							ModelService.createLight(randPt, light.getIntensity(), light.getRadius())));
		}

		return lambertIntensity.mult(1f / (float) nbSample);
	}

	/**
	 * Compute the diffuse color at the given intersection according to the light & primitives positions in the scene and
	 * the intersection informations
	 * @param scene the given scene
	 * @param intersect the given intersection
	 * @param maxLight max number of lights we take account of
	 * @param maxRec max recursion stages
	 * @return the diffuse color (taking account of the illumination)
	 * @throws OperationNotSupportedException
	 */
	private static IVec3 illumDiffuse(IScene scene, IInterInfo intersect, int maxLight, int maxRec) throws OperationNotSupportedException {
		IPrimitive primitive = intersect.getPrimitive();
		List<ILight> lightsLeft = new ArrayList<ILight>(scene.getLights());
		int nbLight = scene.getLights().size();

		//Direct Light
		IVec3 x = ModelService.createVec3(0, 0, 0);
		for (int i = 0; i < Math.min(maxLight, nbLight); i++) {
			int randIndex = (int) (Math.random() * lightsLeft.size());
			ILight light = lightsLeft.get(randIndex);
			lightsLeft.remove(randIndex);
			IVec3 lamberIntensity = lambertianSphericLight(scene, intersect, light, smoothShadowSampling).mult((float)nbLight / (float)maxLight);
			x = x.sum(lamberIntensity);
		}

		//cast indirect ligth rays Integ( LI(x, w') )dw' * f(w, w')
		for (int i = 0; i < indirectLightSampling; i++) {
			IVec3 interPt = intersect.getPoint();
			float r1 = (float) Math.random();
			float r2 = (float) Math.random();
			IVec3 randPt = MathUtils.randomizePointOnHemisphere(
					interPt,
					intersect.getNormal(), 
					r1 , r2);
			IVec3 dir = VectorUtils.computeDirection(interPt, randPt);
			IRay indirectRay = ModelService.createRay(intersect.getPoint(), dir);
			IVec3 indirectColor = castRay(scene, indirectRay, maxRec - 1);
			if (!indirectColor.equals(ModelService.createVec3(0, 0, 0))) {
				x = x.sum(indirectColor.mult(dir.dot(intersect.getNormal()) / (float) Math.PI));
			}
		}

		IVec3 blendLightIntsty = ModelService.createVec3(
				MathUtils.logNormalize(x.getX()),  
				MathUtils.logNormalize(x.getY()), 
				MathUtils.logNormalize(x.getZ()));	
		
		IVec3 outColor = primitive.getColor().mult(blendLightIntsty);
		return outColor;
	}
}
