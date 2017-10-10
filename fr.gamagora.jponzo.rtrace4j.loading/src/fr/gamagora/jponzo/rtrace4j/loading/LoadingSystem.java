package fr.gamagora.jponzo.rtrace4j.loading;

import java.util.Random;

import fr.gamagora.jponzo.rtrace4j.model.ModelService;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ICamera;
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
//				loadManuScene();
//				loadRandomSphereScene();
//				loadGoldTempleScene();
//				loadSimpleScene();
				loadSimpleMirrorScene();
		return scene;
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
