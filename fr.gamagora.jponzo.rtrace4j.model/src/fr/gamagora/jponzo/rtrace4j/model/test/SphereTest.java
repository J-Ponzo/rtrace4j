package fr.gamagora.jponzo.rtrace4j.model.test;

import fr.gamagora.jponzo.rtrace4j.model.impl.Ray;
import fr.gamagora.jponzo.rtrace4j.model.impl.Sphere;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IRay;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ISphere;
import fr.gamagora.jponzo.rtrace4j.utils.impl.Vec3;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public class SphereTest {
	public static void main(String[] args) {
		System.out.println("Test Sphere");
		IRay ray;
		IVec3 origin, direction;

		ISphere sphere;
		IVec3 center;
		float radius;

		IVec3 intersect;
		float t;

		//Test from inside
		System.out.println("Test Sphere.intersect (sphere : {0,0,0}, 5; Ray : {0, 0, 0}, {0, 1, 0})");
		center = new Vec3(0, 0, 0);
		radius = 5;
		sphere = new Sphere("", center, radius);
		origin = new Vec3(0, 0, 0);
		direction = new Vec3(0, 1, 0);
		ray = new Ray(origin, direction);
		intersect = new Vec3(0, 5, 0);
		if (sphere.intersectPt(ray) != null && sphere.intersectPt(ray).equals(intersect)) {
			System.out.println("evaluatePosition OK");
		} else {
			System.out.println("evaluatePosition KO");
		}

		//Test sphere behind eye
		System.out.println("Test Sphere.intersect (sphere : {-7,0,0}, 5; Ray : {0, 0, 0}, {0, 1, 0})");
		center = new Vec3(-7, 0, 0);
		radius = 5;
		sphere = new Sphere("", center, radius);
		origin = new Vec3(0, 0, 0);
		direction = new Vec3(0, 1, 0);
		ray = new Ray(origin, direction);
		intersect = null;
		if (sphere.intersectPt(ray) == null) {
			System.out.println("evaluatePosition OK");
		} else {
			System.out.println("evaluatePosition KO");
		}

		//Test 2 solutions from left
		System.out.println("Test Sphere.intersect (sphere : {0,0,0}, 1; Ray : {-2, 0, 0}, {1, 0, 0})");
		center = new Vec3(0, 0, 0);
		radius = 1;
		sphere = new Sphere("", center, radius);
		origin = new Vec3(-2, 0, 0);
		direction = new Vec3(1, 0, 0);
		ray = new Ray(origin, direction);
		intersect = new Vec3(-1, 0, 0);;
		if (sphere.intersectPt(ray) != null && sphere.intersectPt(ray).equals(intersect)) {
			System.out.println("evaluatePosition OK");
		} else {
			System.out.println("evaluatePosition KO");
		}

		//Test 2 solutions from right
		System.out.println("Test Sphere.intersect (sphere : {0,0,0}, 1; Ray : {2, 0, 0}, {-1, 0, 0})");
		center = new Vec3(0, 0, 0);
		radius = 1;
		sphere = new Sphere("", center, radius);
		origin = new Vec3(2, 0, 0);
		direction = new Vec3(-1, 0, 0);
		ray = new Ray(origin, direction);
		intersect = new Vec3(1, 0, 0);;
		if (sphere.intersectPt(ray) != null && sphere.intersectPt(ray).equals(intersect)) {
			System.out.println("evaluatePosition OK");
		} else {
			System.out.println("evaluatePosition KO");
		}

		//Test 1 solution
		System.out.println("Test Sphere.intersect (sphere : {0,1,0}, 1; Ray : {-2, 0, 0}, {1, 0, 0})");
		center = new Vec3(0, 1, 0);
		radius = 1;
		sphere = new Sphere("", center, radius);
		origin = new Vec3(-2, 0, 0);
		direction = new Vec3(1, 0, 0);
		ray = new Ray(origin, direction);
		intersect = new Vec3(0, 0, 0);
		if (sphere.intersectPt(ray) != null && sphere.intersectPt(ray).equals(intersect)) {
			System.out.println("evaluatePosition OK");
		} else {
			System.out.println("evaluatePosition KO");
		}
	}
}
