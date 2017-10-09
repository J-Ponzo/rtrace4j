package fr.gamagora.jponzo.rtrace4j.rendering;

import fr.gamagora.jponzo.rtrace4j.model.ModelService;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ICamera;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IRay;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;
import fr.jponzo.gamagora.rtrace4j.scene.interfaces.IScene;

/**
 * This tasks compute 1/nbThreads pixels of the actual image. In order to smooth the complexity 
 * on each subtasks, subimages are no continuous pixel spaces but horizontal slices. In other way,
 * we compute only the id + x * nbThreads lines of the image.
 * For convenience, the subimage size is the same that the actual image size. It's a waste of memory
 * as long as we focus only 1/nbThreads pixels. But the it ease development & maintainability.
 * @author jponzo
 *
 */
public class CastRaysTask implements Runnable {
	private IScene scene;
	private int maxRec;
	private IVec3[][] imgTable;
	private int nbThreads;
	private int id;

	@Override
	public void run() {
		ICamera camera = scene.getCamera();
		int w = camera.getWidth();
		int h = camera.getHeight(); 
		imgTable = new IVec3[h][w];
		IVec3 camPosition = camera.getPosition(); 
		float fovRad = camera.getFov(); 

		IVec3 origin = camPosition;
		IVec3 direction;

		int nbRay = 0;
		int share = (int) ((float) (((h * w) / nbThreads) / 10f));
		int targetNb = share;
		float rx, ry;
		for (int i = id; i < h; i += nbThreads) {
			for (int j = 0; j < w; j++) {
				IVec3 pixelColor = ModelService.createVec3(0, 0, 0);
				for (int k = 0; k < RenderingSystem.rayPerPixel; k++) {
					//Find the direction of the ray passing through the current pixel
					rx = (float) Math.random() - 0.5f;
					ry = (float) Math.random() - 0.5f;
					direction = ModelService.createVec3(
							(float) ((float) j + rx - (float) w / 2.0),
							(float) ((float) i + ry - (float) h / 2.0),
							(float) (- (float) w / (2.0 * Math.tan(fovRad / 2.0)))
							);
					IRay ray = ModelService.createRay(origin, direction);
					IVec3 rayColor  = RenderingSystem.castRay(scene, ray, maxRec);
					pixelColor = pixelColor.sum(rayColor);
				}
				imgTable[i][j] = pixelColor.mult(1f / RenderingSystem.rayPerPixel);
				imgTable[i][j].setX((float) Math.pow(imgTable[i][j].getX(), 1 / 2.2));
				imgTable[i][j].setY((float) Math.pow(imgTable[i][j].getY(), 1 / 2.2));
				imgTable[i][j].setZ((float) Math.pow(imgTable[i][j].getZ(), 1 / 2.2));
				
				nbRay++;

				if (nbRay > targetNb) {
					targetNb += share;
					System.out.println("Thread" + id + " : " + (float)nbRay / (float) ((w * h) / nbThreads) * 100.0 + "% " + nbRay + "/" + (w * h) / nbThreads + " rays cast");
				}
			}
		}
	}

	public CastRaysTask(int id, int nbThreads, IScene scene, int maxRec) {
		super();
		this.id = id;
		this.nbThreads = nbThreads;
		this.scene = scene;
		this.maxRec = maxRec;
	}

	public IVec3[][] getImgTable() {
		return imgTable;
	}

	public int getId() {
		return this.id;
	}

	public int getNbThreads() {
		return this.nbThreads;
	}
}

