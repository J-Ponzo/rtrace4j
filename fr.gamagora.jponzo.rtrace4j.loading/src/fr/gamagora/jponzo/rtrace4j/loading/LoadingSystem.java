package fr.gamagora.jponzo.rtrace4j.loading;

import java.util.Random;

import fr.gamagora.jponzo.rtrace4j.model.ModelService;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ICamera;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ICube;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ILight;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IMaterial;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IPlane;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ISphere;
import fr.gamagora.jponzo.rtrace4j.utils.impl.IOUtils;
import fr.gamagora.jponzo.rtrace4j.utils.impl.Vec3;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;
import fr.jponzo.gamagora.rtrace4j.scene.SceneManager;
import fr.jponzo.gamagora.rtrace4j.scene.interfaces.IScene;

public class LoadingSystem {
	public static int maxTreeDepth = 5;

	/**
	 * Emulate the scene loading. In fact the so called stub scene is build pragmatically the returned
	 * @return the programmatically built scene
	 */
	public static IScene loadStubScene() {
		IScene scene = 
				//				loadSimpleFresnelScene();
				//				loadManuScene();
				loadBladeRunnerScene();
		//				loadRandomSphereScene();
		//				loadGoldTempleScene();
		//						loadSimpleScene();
		//				loadSimpleMirrorScene();
		return scene;
	}

	private static IScene loadBladeRunnerScene() {
		IScene scene = SceneManager.createScene();

		ICube cube;
		ISphere sphere;
		IPlane plane;
		ILight light;
		IMaterial diffYellowStoneMat = ModelService.createDiffuseMaterial();
		diffYellowStoneMat.setAlbedo(ModelService.createVec3(0.7843f, 0.4901f, 0.1f));
		IMaterial transMat = ModelService.createTransparentMaterial();
		//		transMat.setAlbedo(ModelService.createVec3(0.7843f, 0.7843f, 0.7843f));
		transMat.setAlbedo(ModelService.createVec3(0.75f, 0.75f, 0.9f));
		IMaterial diffGreyMat = ModelService.createSpecularMaterial();
		diffGreyMat.setAlbedo(ModelService.createVec3(0.9f, 0.9f, 0.9f));

		IVec3 origin;
		IVec3 normal;
		IVec3 center;
		IVec3 position;
		IVec3 intensity;
		float radius;

		//Set Camera
		int width = 1024;
		int height = 1024;
		float fov = (int) (60 * (Math.PI / 180.0));
		position = ModelService.createVec3(0, 30, 120);
		ICamera camera = ModelService.createCamera(width, height, fov, position);
		scene.setCamera(camera);

//		//Add Light 1
//		position = ModelService.createVec3(35, 30, 7.5f);
//		intensity = ModelService.createVec3(9000, 8500, 7000);
//		light = ModelService.createLight(position, intensity, 5);
//		light.setName("Light_1");
//		scene.addLight(light);
//
//		//Add Light 2
//		position = ModelService.createVec3(-35, 30, 7.5f);
//		intensity = ModelService.createVec3(9000, 8500, 7000);
//		light = ModelService.createLight(position, intensity, 5);
//		light.setName("Light_2");
//		scene.addLight(light);

		float fact = 2000f;
		
		//Add Light 1
		position = ModelService.createVec3(35, 40, 32.5f);
		intensity = ModelService.createVec3(9f * fact, 8.5f * fact, 7f * fact);
		light = ModelService.createLight(position, intensity, 5);
		light.setName("Light_1");
		scene.addLight(light);

		//Add Light 2
		position = ModelService.createVec3(-35, 40, 32.5f);
		intensity = ModelService.createVec3(9f * fact, 8.5f * fact, 7f * fact);
		light = ModelService.createLight(position, intensity, 5);
		light.setName("Light_2");
		scene.addLight(light);

		//Add Light 3
		position = ModelService.createVec3(35, 40, 57.5f);
		intensity = ModelService.createVec3(9f * fact, 8.5f * fact, 7f * fact);
		light = ModelService.createLight(position, intensity, 5);
		light.setName("Light_3");
		scene.addLight(light);

		//Add Light 4
		position = ModelService.createVec3(-35, 40, 57.5f);
		intensity = ModelService.createVec3(9f * fact, 8.5f * fact, 7f * fact);
		light = ModelService.createLight(position, intensity, 5);
		light.setName("Light_4");
		scene.addLight(light);

		//Add Light 3
		position = ModelService.createVec3(35, 40, 82.5f);
		intensity = ModelService.createVec3(9f * fact, 8.5f * fact, 7f * fact);
		light = ModelService.createLight(position, intensity, 5);
		light.setName("Light_3");
		scene.addLight(light);

		//Add Light 4
		position = ModelService.createVec3(-35, 40, 82.5f);
		intensity = ModelService.createVec3(9f * fact, 8.5f * fact, 7f * fact);
		light = ModelService.createLight(position, intensity, 5);
		light.setName("Light_4");
		scene.addLight(light);

		//Add Way
		center = ModelService.createVec3(0, -10, -10);
		cube = ModelService.creatCube(center, 30, 30, 30);
		cube.setName("Way_Cube");
		cube.setMaterial(diffYellowStoneMat);
		scene.addPrimitive(cube);

		//Add Stairs
		center = ModelService.createVec3(0, -10, 10);
		cube = ModelService.creatCube(center, 30, 25, 10);
		cube.setName("Step1_Cube");
		cube.setMaterial(diffYellowStoneMat);
		scene.addPrimitive(cube);

		center = ModelService.createVec3(0, -10, 20);
		cube = ModelService.creatCube(center, 30, 20, 10);
		cube.setName("Step2_Cube");
		cube.setMaterial(diffYellowStoneMat);
		scene.addPrimitive(cube);

		center = ModelService.createVec3(0, -10, 30);
		cube = ModelService.creatCube(center, 30, 15, 10);
		cube.setName("Step3_Cube");
		cube.setMaterial(diffYellowStoneMat);
		scene.addPrimitive(cube);

		center = ModelService.createVec3(0, -10, 40);
		cube = ModelService.creatCube(center, 30, 10, 10);
		cube.setName("Step4_Cube");
		cube.setMaterial(diffYellowStoneMat);
		scene.addPrimitive(cube);

		center = ModelService.createVec3(0, -10, 50);
		cube = ModelService.creatCube(center, 30, 5, 10);
		cube.setName("Step5_Cube");
		cube.setMaterial(diffYellowStoneMat);
		scene.addPrimitive(cube);

		center = ModelService.createVec3(0, -12.5f, 60);
		cube = ModelService.creatCube(center, 30, 5, 10);
		cube.setName("Step5_Cube");
		cube.setMaterial(diffYellowStoneMat);
		scene.addPrimitive(cube);

		//Add plane Ceil 1
		origin = ModelService.createVec3(0, 75, 0);
		normal = ModelService.createVec3(0, -1, 0);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Ceil");
		plane.setMaterial(diffYellowStoneMat);
		scene.addPrimitive(plane);

		//Add plane Left wall
		origin = ModelService.createVec3(-45, 0, 0);
		normal = ModelService.createVec3(1, 0, 0);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Left_Wall");
		plane.setMaterial(diffYellowStoneMat);
		scene.addPrimitive(plane);

		//Add plane right wall
		origin = ModelService.createVec3(45, 0, 0);
		normal = ModelService.createVec3(-1, 0, 0);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Right_Wall");
		plane.setMaterial(diffYellowStoneMat);
		scene.addPrimitive(plane);

		//Left Ridge
		center = ModelService.createVec3(-30, -5, 20);
		cube = ModelService.creatCube(center, 30, 30, 200);
		cube.setName("LRidge_Cube");
		cube.setMaterial(diffYellowStoneMat);
		scene.addPrimitive(cube);

		//Right Ridge
		center = ModelService.createVec3(30, -5, 20);
		cube = ModelService.creatCube(center, 30, 30, 200);
		cube.setName("RRidge_Cube");
		cube.setMaterial(diffYellowStoneMat);
		scene.addPrimitive(cube);

		//Corridor Left Wall
		center = ModelService.createVec3(-30, 5, -120);
		cube = ModelService.creatCube(center, 30, 200, 200);
		cube.setName("LCorrWall_Cube");
		cube.setMaterial(diffYellowStoneMat);
		scene.addPrimitive(cube);

		//Corridor Right Wall
		center = ModelService.createVec3(30, 5, -120);
		cube = ModelService.creatCube(center, 30, 200, 200);
		cube.setName("RCorrWall_Cube");
		cube.setMaterial(diffYellowStoneMat);
		scene.addPrimitive(cube);

		//Add plane Back Wall
		origin = ModelService.createVec3(0, 0, 150);
		normal = ModelService.createVec3(0, 0, -1);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Back_Wall");
		plane.setMaterial(diffYellowStoneMat);
		scene.addPrimitive(plane);

		//Corridor Ceil
		center = ModelService.createVec3(0, 75, -120);
		cube = ModelService.creatCube(center, 30, 50, 200);
		cube.setName("RCorrWall_Cube");
		cube.setMaterial(diffYellowStoneMat);
		scene.addPrimitive(cube);

		//Left Statue 1
		center = ModelService.createVec3(-30, 35, 20);
		createStatue(scene, center, transMat, diffGreyMat, diffYellowStoneMat);

		//Left Statue 2
		center = ModelService.createVec3(-30, 35, 45);
		createStatue(scene, center, transMat, diffGreyMat, diffYellowStoneMat);

		//Left Statue 3
		center = ModelService.createVec3(-30, 35, 70);
		createStatue(scene, center, transMat, diffGreyMat, diffYellowStoneMat);

		//Right Statue 1
		center = ModelService.createVec3(30, 35, 20);
		createStatue(scene, center, transMat, diffGreyMat, diffYellowStoneMat);

		//Right Statue 2
		center = ModelService.createVec3(30, 35, 45);
		createStatue(scene, center, transMat, diffGreyMat, diffYellowStoneMat);

		//Right Statue 3
		center = ModelService.createVec3(30, 35, 70);
		createStatue(scene, center, transMat, diffGreyMat, diffYellowStoneMat);

		return scene;
	}

	private static void createStatue(IScene scene, IVec3 center, IMaterial transMat, IMaterial diffGreyMat, IMaterial diffYellowStoneMat) {
		ICube cube;
		ISphere sphere;
		//Statue 1
		IVec3 cubeCenter = center;
		cube = ModelService.creatCube(cubeCenter, 15, 50, 15);
		cube.setName("LStatue1_Cube");
		cube.setMaterial(transMat);
		scene.addPrimitive(cube);

		//Statue 1 Sphere 1
		IVec3 basesphere1Center = ModelService.createVec3(center.getX(), center.getY() + 25f, center.getZ());
		cube = ModelService.creatCube(basesphere1Center, 17.5f, 10f, 17.5f);
		cube.setName("Base");
		cube.setMaterial(diffYellowStoneMat);
		scene.addPrimitive(cube);

		//Statue 1 Sphere 1
		IVec3 sphere1Center = ModelService.createVec3(center.getX(), center.getY() + 10f, center.getZ());
		sphere = ModelService.creatSphere(sphere1Center, 5);
		sphere.setName("LStatue1_Sphere1");
		sphere.setMaterial(diffGreyMat);
		scene.addPrimitive(sphere);

		// Statue 1 Sphere 2
		IVec3 sphere2Center = ModelService.createVec3(center.getX(), center.getY() - 5f, center.getZ());
		sphere = ModelService.creatSphere(sphere2Center, 5);
		sphere.setName("LStatue1_Sphere2");
		sphere.setMaterial(diffGreyMat);
		scene.addPrimitive(sphere);

		//Statue 1 Sphere 1
		IVec3 topsphere1Center = ModelService.createVec3(center.getX(), center.getY() - 20f, center.getZ());
		cube = ModelService.creatCube(topsphere1Center, 17.5f, 10f, 17.5f);
		cube.setName("Top");
		cube.setMaterial(diffYellowStoneMat);
		scene.addPrimitive(cube);
	}

	private static IScene loadManuScene() {
		IScene scene = SceneManager.createScene();

		ISphere sphere;
		IPlane plane;
		IMaterial diffGreyMat = ModelService.createDiffuseMaterial();
		diffGreyMat.setAlbedo(ModelService.createVec3(0.7843f, 0.7843f, 0.7843f));
		IMaterial diffRedMat = ModelService.createDiffuseMaterial();
		diffRedMat.setAlbedo(ModelService.createVec3(0.7843f, 0.4901f, 0.4901f));
		IMaterial diffBlueMat = ModelService.createDiffuseMaterial();
		diffBlueMat.setAlbedo(ModelService.createVec3(0.4901f, 0.4901f, 0.7843f));
		IMaterial specMat = ModelService.createSpecularMaterial();
		specMat.setAlbedo(ModelService.createVec3(0.7843f, 0.7843f, 0.7843f));
		IMaterial transMat = ModelService.createTransparentMaterial();
		transMat.setAlbedo(ModelService.createVec3(0.7843f, 0.7843f, 0.7843f));
		ILight light;
		IVec3 origin;
		IVec3 normal;
		IVec3 center;
		IVec3 position;
		IVec3 intensity;
		float radius;

		//Set Camera
		int width = 1024;
		int height = 1024;
		float fov = (int) (60 * (Math.PI / 180.0));
		position = ModelService.createVec3(0, 5, -5);
		ICamera camera = ModelService.createCamera(width, height, fov, position);
		scene.setCamera(camera);

		//Add Light 1
		position = ModelService.createVec3(25, -5, -80);
		intensity = ModelService.createVec3(150000 / 255, 150000 / 255, 255000 / 255);
		light = ModelService.createLight(position, intensity, 1);
		light.setName("Light_1");
		scene.addLight(light);

		//Add Sphere 1
		center = ModelService.createVec3(-20, 10, -85);
		radius = 10;
		sphere = ModelService.creatSphere(center, radius);
		sphere.setName("Sphere1");
		sphere.setMaterial(diffGreyMat);
		scene.addPrimitive(sphere);

		//Add Sphere 2
		center = ModelService.createVec3(0, -10, -85);
		radius = 10;
		sphere = ModelService.creatSphere(center, radius);
		sphere.setName("Sphere2");
		sphere.setMaterial(specMat);
		scene.addPrimitive(sphere);

		//Add Sphere 3
		center = ModelService.createVec3(-3, 3, -45);
		radius = 4;
		sphere = ModelService.creatSphere(center, radius);
		sphere.setName("Sphere3");
		sphere.setMaterial(transMat);
		scene.addPrimitive(sphere);

		//Add plane Floor
		origin = ModelService.createVec3(0, -20, 0);
		normal = ModelService.createVec3(0, 1, 0);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Floor");
		plane.setMaterial(diffBlueMat);
		scene.addPrimitive(plane);

		//Add plane Ceil 1
		origin = ModelService.createVec3(-30, 100, 0);
		normal = ModelService.createVec3(-1, -1, 0);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Ceil1");
		plane.setMaterial(diffGreyMat);
		scene.addPrimitive(plane);

		//Add plane Ceil 2
		origin = ModelService.createVec3(30, 100, 0);
		normal = ModelService.createVec3(1, -1, 0);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Ceil2");
		plane.setMaterial(diffGreyMat);
		scene.addPrimitive(plane);

		//Add plane Left wall
		origin = ModelService.createVec3(-50, 50, 0);
		normal = ModelService.createVec3(1, 0, 0);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Left_Wall");
		plane.setMaterial(diffGreyMat);
		scene.addPrimitive(plane);

		//Add plane right wall
		origin = ModelService.createVec3(50, 50, 0);
		normal = ModelService.createVec3(-1, 0, 0);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Right_Wall");
		plane.setMaterial(diffRedMat);
		scene.addPrimitive(plane);

		//Add plane Back Wall
		origin = ModelService.createVec3(0, 50, -150);
		normal = ModelService.createVec3(0, 0, 1);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Back_Wall");
		plane.setMaterial(diffGreyMat);
		scene.addPrimitive(plane);

		//Add plane Front Wall
		origin = ModelService.createVec3(0, 50, 50);
		normal = ModelService.createVec3(0, 0, -1);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Front_Wall");
		plane.setMaterial(diffGreyMat);
		scene.addPrimitive(plane);

		return scene;
	}

	private static IScene loadRandomSphereScene() {
		IVec3 sphereCloudCenter = ModelService.createVec3(0, 0, -20);
		float cubeSize = 10;
		int nbSpheres = 1000;

		IScene scene = SceneManager.createScene();
		IMaterial diffGoldMat = ModelService.createDiffuseMaterial();
		diffGoldMat.setAlbedo(ModelService.createVec3(0.8f, 0.6f, 0.1f));
		ISphere sphere;
		ILight light;
		IVec3 center;
		IVec3 position;
		IVec3 intensity;
		float radius;

		//Set Camera
		int width = 1024;
		int height = 1024;
		float fov = (int) (60 * (Math.PI / 180.0));
		position = ModelService.createVec3(0, 0, 0);
		ICamera camera = ModelService.createCamera(width, height, fov, position);
		scene.setCamera(camera);

		//Add Light 1
		position = ModelService.createVec3(10, 50, -5);
		intensity = ModelService.createVec3(1000, 1000, 1000);
		light = ModelService.createLight(position, intensity, 5);
		light.setName("Light_1");
		scene.addLight(light);

		//Add Light 2
		position = ModelService.createVec3(-10, 50, -5);
		intensity = ModelService.createVec3(1000, 1000, 1000);
		light = ModelService.createLight(position, intensity, 5);
		light.setName("Light_2");
		scene.addLight(light);

		//Spheres
		Random rand = new Random(42);
		for (int i = 0; i < nbSpheres; i++) {
			center = new Vec3(
					rand.nextFloat() * cubeSize - cubeSize / 2, 
					rand.nextFloat() * cubeSize - cubeSize / 2, 
					rand.nextFloat() * cubeSize - cubeSize / 2);
			center = center.sum(sphereCloudCenter);
			radius = IOUtils.clamp(0.1f, 1f, rand.nextFloat() * 0.3f);
			sphere = ModelService.creatSphere(center, radius);
			sphere.setMaterial(diffGoldMat);
			sphere.setName("Gold_Sphere");
			scene.addPrimitive(sphere);
		}

		return scene;
	}

	private static IScene loadSimpleScene() {
		IScene scene = SceneManager.createScene();

		ISphere sphere;
		IPlane plane;
		ILight light;
		IMaterial diffWhiteMat = ModelService.createDiffuseMaterial();
		IMaterial diffPinkMat = ModelService.createDiffuseMaterial();
		diffPinkMat.setAlbedo(ModelService.createVec3(0.5f,  0, 0.5f));
		IMaterial diffYellowMat = ModelService.createDiffuseMaterial();
		diffYellowMat.setAlbedo(ModelService.createVec3(0.5f,  0.5f, 0f));
		IMaterial diffCyanMat = ModelService.createDiffuseMaterial();
		diffCyanMat.setAlbedo(ModelService.createVec3(0f,  0.5f, 0.5f));
		IMaterial diffGoldMat = ModelService.createDiffuseMaterial();
		diffGoldMat.setAlbedo(ModelService.createVec3(0.8f, 0.6f, 0.1f));
		IMaterial diffBlueMat = ModelService.createDiffuseMaterial();
		diffBlueMat.setAlbedo(ModelService.createVec3(0f, 0f, 0.5f));

		IVec3 origin;
		IVec3 normal;
		IVec3 center;
		IVec3 position;
		IVec3 intensity;
		float radius;

		//Set Camera
		int width = 1024;
		int height = 1024;
		float fov = (int) (60 * (Math.PI / 180.0));
		position = ModelService.createVec3(0, 20, 120);
		ICamera camera = ModelService.createCamera(width, height, fov, position);
		scene.setCamera(camera);

		//Add Light 1
		position = ModelService.createVec3(10, 50, -5);
		intensity = ModelService.createVec3(3000, 3000, 3000);
		light = ModelService.createLight(position, intensity, 5);
		light.setName("Light_1");
		scene.addLight(light);

		//Add Light 2
		position = ModelService.createVec3(-10, 50, -5);
		intensity = ModelService.createVec3(3000, 3000, 3000);
		light = ModelService.createLight(position, intensity, 5);
		light.setName("Light_2");
		scene.addLight(light);

		//Add Gold Sphere
		center = ModelService.createVec3(0, 0, -10);
		radius = 15;
		sphere = ModelService.creatSphere(center, radius);
		sphere.setName("Gold_Sphere");
		sphere.setMaterial(diffGoldMat);
		scene.addPrimitive(sphere);

		//Add plane Floor
		origin = ModelService.createVec3(10, -20, 0);
		normal = ModelService.createVec3(0, 1, 0);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Floor");
		plane.setMaterial(diffWhiteMat);
		scene.addPrimitive(plane);

		//Add plane Ceil
		origin = ModelService.createVec3(0, 100, 0);
		normal = ModelService.createVec3(0, -1, 0);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Ceil");
		plane.setMaterial(diffPinkMat);
		scene.addPrimitive(plane);

		//Add plane Left wall
		origin = ModelService.createVec3(-50, 0, 0);
		normal = ModelService.createVec3(1, 0, 0);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Left_Wall");
		plane.setMaterial(diffBlueMat);
		scene.addPrimitive(plane);

		//Add plane right wall
		origin = ModelService.createVec3(50, 100, 0);
		normal = ModelService.createVec3(-1, 0, 0);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Right_Wall");
		plane.setMaterial(diffYellowMat);
		scene.addPrimitive(plane);

		//Add plane Back Wall
		origin = ModelService.createVec3(0, 0, -100);
		normal = ModelService.createVec3(0, 0, 1);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Back_Wall");
		plane.setMaterial(diffCyanMat);
		scene.addPrimitive(plane);

		return scene;
	}

	private static IScene loadSimpleFresnelScene() {
		IScene scene = SceneManager.createScene();

		ISphere sphere;
		IPlane plane;
		ILight light;
		IMaterial diffWhiteMat = ModelService.createDiffuseMaterial();
		IMaterial diffPinkMat = ModelService.createDiffuseMaterial();
		diffPinkMat.setAlbedo(ModelService.createVec3(0.5f,  0, 0.5f));
		IMaterial diffYellowMat = ModelService.createDiffuseMaterial();
		diffYellowMat.setAlbedo(ModelService.createVec3(0.5f,  0.5f, 0f));
		IMaterial diffCyanMat = ModelService.createDiffuseMaterial();
		diffCyanMat.setAlbedo(ModelService.createVec3(0f,  0.5f, 0.5f));
		IMaterial fresnelMat = ModelService.createFresnelMaterial();
		IMaterial diffBlueMat = ModelService.createDiffuseMaterial();
		diffBlueMat.setAlbedo(ModelService.createVec3(0f, 0f, 0.5f));

		IVec3 origin;
		IVec3 normal;
		IVec3 center;
		IVec3 position;
		IVec3 intensity;
		float radius;

		//Set Camera
		int width = 1024;
		int height = 1024;
		float fov = (int) (60 * (Math.PI / 180.0));
		position = ModelService.createVec3(0, 20, 120);
		ICamera camera = ModelService.createCamera(width, height, fov, position);
		scene.setCamera(camera);

		//Add Light 1
		position = ModelService.createVec3(10, 50, -5);
		intensity = ModelService.createVec3(3000, 3000, 3000);
		light = ModelService.createLight(position, intensity, 5);
		light.setName("Light_1");
		scene.addLight(light);

		//Add Light 2
		position = ModelService.createVec3(-10, 50, -5);
		intensity = ModelService.createVec3(3000, 3000, 3000);
		light = ModelService.createLight(position, intensity, 5);
		light.setName("Light_2");
		scene.addLight(light);

		//Add Fresnel Sphere
		center = ModelService.createVec3(0, 0, -10);
		radius = 15;
		sphere = ModelService.creatSphere(center, radius);
		sphere.setName("Fresnel_Sphere");
		sphere.setMaterial(fresnelMat);
		scene.addPrimitive(sphere);

		//Add Fresnel Sphere
		center = ModelService.createVec3(5, 0, -30);
		radius = 15;
		sphere = ModelService.creatSphere(center, radius);
		sphere.setName("Ref_Sphere");
		sphere.setMaterial(diffWhiteMat);
		scene.addPrimitive(sphere);

		//Add plane Floor
		origin = ModelService.createVec3(10, -20, 0);
		normal = ModelService.createVec3(0, 1, 0);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Floor");
		plane.setMaterial(diffWhiteMat);
		scene.addPrimitive(plane);

		//Add plane Ceil
		origin = ModelService.createVec3(0, 100, 0);
		normal = ModelService.createVec3(0, -1, 0);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Ceil");
		plane.setMaterial(diffPinkMat);
		scene.addPrimitive(plane);

		//Add plane Left wall
		origin = ModelService.createVec3(-50, 0, 0);
		normal = ModelService.createVec3(1, 0, 0);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Left_Wall");
		plane.setMaterial(diffBlueMat);
		scene.addPrimitive(plane);

		//Add plane right wall
		origin = ModelService.createVec3(50, 100, 0);
		normal = ModelService.createVec3(-1, 0, 0);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Right_Wall");
		plane.setMaterial(diffYellowMat);
		scene.addPrimitive(plane);

		//Add plane Back Wall
		origin = ModelService.createVec3(0, 0, -100);
		normal = ModelService.createVec3(0, 0, 1);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Back_Wall");
		plane.setMaterial(diffCyanMat);
		scene.addPrimitive(plane);

		return scene;
	}

	private static IScene loadSimpleMirrorScene() {
		IScene scene = SceneManager.createScene();

		ISphere sphere;
		IPlane plane;
		ILight light;
		IMaterial diffWhiteMat = ModelService.createDiffuseMaterial();
		IMaterial specWhiteMat = ModelService.createSpecularMaterial();
		IMaterial transWhiteMat = ModelService.createTransparentMaterial();
		IMaterial diffPinkMat = ModelService.createDiffuseMaterial();
		diffPinkMat.setAlbedo(ModelService.createVec3(0.5f,  0, 0.5f));
		IMaterial diffYellowMat = ModelService.createDiffuseMaterial();
		diffYellowMat.setAlbedo(ModelService.createVec3(0.5f,  0.5f, 0f));
		IMaterial diffCyanMat = ModelService.createDiffuseMaterial();
		diffCyanMat.setAlbedo(ModelService.createVec3(0f,  0.5f, 0.5f));

		IVec3 origin;
		IVec3 normal;
		IVec3 center;
		IVec3 position;
		IVec3 intensity;
		float radius;

		//Set Camera
		int width = 1024;
		int height = 1024;
		float fov = (int) (60 * (Math.PI / 180.0));
		position = ModelService.createVec3(0, 20, 180);
		ICamera camera = ModelService.createCamera(width, height, fov, position);
		scene.setCamera(camera);

		//Add Light 1
		position = ModelService.createVec3(10, 50, -5);
		intensity = ModelService.createVec3(3000, 3000, 3000);
		light = ModelService.createLight(position, intensity, 5);
		light.setName("Light_1");
		scene.addLight(light);

		//Add Light 2
		position = ModelService.createVec3(-10, 50, -5);
		intensity = ModelService.createVec3(3000, 3000, 3000);
		light = ModelService.createLight(position, intensity, 5);
		light.setName("Light_2");
		scene.addLight(light);

		//Add Gold Sphere
		center = ModelService.createVec3(0, 0, -10);
		radius = 15;
		sphere = ModelService.creatSphere(center, radius);
		sphere.setName("Gold_Sphere");
		sphere.setMaterial(transWhiteMat);
		scene.addPrimitive(sphere);

		//Add plane Floor
		origin = ModelService.createVec3(10, -20, 0);
		normal = ModelService.createVec3(0, 1, 0);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Floor");
		plane.setMaterial(diffWhiteMat);
		scene.addPrimitive(plane);

		//Add plane Ceil
		origin = ModelService.createVec3(0, 100, 0);
		normal = ModelService.createVec3(0, -1, 0);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Ceil");
		plane.setMaterial(diffPinkMat);
		scene.addPrimitive(plane);

		//Add plane Left wall
		origin = ModelService.createVec3(-50, 0, 0);
		normal = ModelService.createVec3(1, 0, 0);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Left_Wall");
		plane.setMaterial(specWhiteMat);
		scene.addPrimitive(plane);

		//Add plane right wall
		origin = ModelService.createVec3(50, 100, 0);
		normal = ModelService.createVec3(-1, 0, 0);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Right_Wall");
		plane.setMaterial(diffYellowMat);
		scene.addPrimitive(plane);

		//Add plane Back Wall
		origin = ModelService.createVec3(0, 0, -100);
		normal = ModelService.createVec3(0, 0, 1);
		plane = ModelService.createPlane(origin, normal);
		plane.setName("Back_Wall");
		plane.setMaterial(diffCyanMat);
		scene.addPrimitive(plane);

		return scene;
	}
	//
	//	private static IScene loadGoldTempleScene () {
	//		IScene scene = SceneManager.createScene();
	//
	//		ISphere sphere;
	//		IPlane plane;
	//		ILight light;
	//		IVec3 origin;
	//		IVec3 normal;
	//		IVec3 center;
	//		IVec3 position;
	//		IVec3 intensity;
	//		float radius;
	//
	//		//Set Camera
	//		int width = 1024;
	//		int height = 1024;
	//		float fov = (int) (60 * (Math.PI / 180.0));
	//		position = ModelService.createVec3(25, 0, 150);
	//		ICamera camera = ModelService.createCamera(width, height, fov, position);
	//		scene.setCamera(camera);
	//
	//		//Add Light
	//		position = ModelService.createVec3(-45, 50, -55);
	//		intensity = ModelService.createVec3(10000, 10000, 10000);
	//		light = ModelService.createLight(position, intensity, 5f);
	//		light.setName("Light_1");
	//		scene.addLight(light);
	//
	//		//Add Light
	//		position = ModelService.createVec3(45, 50, -55);
	//		intensity = ModelService.createVec3(10000, 10000, 10000);
	//		light = ModelService.createLight(position, intensity, 5f);
	//		light.setName("Light_2");
	//		scene.addLight(light);
	//
	//		//Add Light
	//		position = ModelService.createVec3(45, 50, -25);
	//		intensity = ModelService.createVec3(10000, 10000, 10000);
	//		light = ModelService.createLight(position, intensity, 5f);
	//		light.setName("Light_3");
	//		scene.addLight(light);
	//
	//		//Add Light
	//		position = ModelService.createVec3(-45, 50, -25);
	//		intensity = ModelService.createVec3(10000, 10000, 10000);
	//		light = ModelService.createLight(position, intensity, 5f);
	//		light.setName("Light_4");
	//		scene.addLight(light);
	//
	//		//Add sphere S1
	//		center = ModelService.createVec3(0, 23, -35);
	//		radius = 30;
	//		sphere = ModelService.creatSphere(center, radius);
	//		sphere.setColor(ModelService.createVec3(0, 1, 0));
	//		sphere.setDiffVal(0.0f);
	//		sphere.setSpecVal(0.25f);
	//		sphere.setTransVal(0.75f);
	//		sphere.setName("Big_Sphere");
	//		scene.addPrimitive(sphere);
	//
	//		//Add Red Sphere
	//		center = ModelService.createVec3(20, 10, 0);
	//		radius = 15;
	//		sphere = ModelService.creatSphere(center, radius);
	//		sphere.setColor(ModelService.createVec3(1, 0, 0));
	//		sphere.setDiffVal(0.4f);
	//		sphere.setSpecVal(0.0f);
	//		sphere.setTransVal(0.6f);
	//		sphere.setRefractIndex(1.25f);
	//		sphere.setName("Red_Sphere");
	//		scene.addPrimitive(sphere);
	//
	//		//Add Green Sphere
	//		center = ModelService.createVec3(0, 45, 0);
	//		radius = 15;
	//		sphere = ModelService.creatSphere(center, radius);
	//		sphere.setColor(ModelService.createVec3(0, 1, 0));
	//		sphere.setDiffVal(0.4f);
	//		sphere.setSpecVal(0.0f);
	//		sphere.setTransVal(0.6f);
	//		sphere.setRefractIndex(1.4f);
	//		sphere.setName("Green_Sphere");
	//		scene.addPrimitive(sphere);
	//
	//		//Add Blue Sphere
	//		center = ModelService.createVec3(-20, 10, 0);
	//		radius = 15;
	//		sphere = ModelService.creatSphere(center, radius);
	//		sphere.setColor(ModelService.createVec3(0, 0, 1));
	//		sphere.setDiffVal(0.4f);
	//		sphere.setSpecVal(0.0f);
	//		sphere.setTransVal(0.6f);
	//		sphere.setRefractIndex(1.4f);
	//		sphere.setName("Blue_Sphere");
	//		scene.addPrimitive(sphere);
	//
	//		//Add Gold Sphere
	//		center = ModelService.createVec3(0, 40, -120);
	//		radius = 50;
	//		sphere = ModelService.creatSphere(center, radius);
	//		sphere.setColor(ModelService.createVec3(0.8f, 0.6f, 0.1f));
	//		sphere.setDiffVal(0.75f);
	//		sphere.setSpecVal(0.25f);
	//		sphere.setTransVal(0.0f);
	//		sphere.setRefractIndex(1.4f);
	//		sphere.setName("Gold_Sphere");
	//		scene.addPrimitive(sphere);
	//
	//		//Add plane Floor
	//		origin = ModelService.createVec3(10, -20, 0);
	//		normal = ModelService.createVec3(0, 1, 0);
	//		plane = ModelService.createPlane(origin, normal);
	//		plane.setColor(ModelService.createVec3(0.5f, 0, 0));
	//		plane.setDiffVal(0.9f);
	//		plane.setSpecVal(0.1f);
	//		plane.setTransVal(0.0f);
	//		plane.setName("Floor");
	//		scene.addPrimitive(plane);
	//
	//		//Add plane Ceil
	//		origin = ModelService.createVec3(0, 100, 0);
	//		normal = ModelService.createVec3(0, -1, 0);
	//		plane = ModelService.createPlane(origin, normal);
	//		plane.setColor(ModelService.createVec3(0.5f, 0, 0.5f));
	//		plane.setDiffVal(0.85f);
	//		plane.setSpecVal(0.15f);
	//		plane.setTransVal(0.0f);
	//		plane.setName("Ceil");
	//		scene.addPrimitive(plane);
	//
	//		//Add plane Left wall
	//		origin = ModelService.createVec3(-50, 0, 0);
	//		normal = ModelService.createVec3(1, 0, 0);
	//		plane = ModelService.createPlane(origin, normal);
	//		plane.setColor(ModelService.createVec3(0, 0, 0.5f));
	//		plane.setDiffVal(0.8f);
	//		plane.setSpecVal(0.2f);
	//		plane.setTransVal(0.0f);
	//		plane.setName("Left_Wall");
	//		scene.addPrimitive(plane);
	//
	//		//Add plane right wall
	//		origin = ModelService.createVec3(50, 100, 0);
	//		normal = ModelService.createVec3(-1, 0, 0);
	//		plane = ModelService.createPlane(origin, normal);
	//		plane.setColor(ModelService.createVec3(0.5f, 0.5f, 0));
	//		plane.setDiffVal(0.0f);
	//		plane.setSpecVal(1f);
	//		plane.setTransVal(0.0f);
	//		plane.setName("Right_Wall");
	//		scene.addPrimitive(plane);
	//
	//		//Add plane Back Wall
	//		origin = ModelService.createVec3(0, 0, -100);
	//		normal = ModelService.createVec3(0, 0, 1);
	//		plane = ModelService.createPlane(origin, normal);
	//		plane.setColor(ModelService.createVec3(0, 0.5f, 0.5f));
	//		plane.setDiffVal(0.8f);
	//		plane.setSpecVal(0.2f);
	//		plane.setTransVal(0.0f);
	//		plane.setName("Back_Wall");
	//		scene.addPrimitive(plane);
	//
	//		return scene;
	//	}
}
