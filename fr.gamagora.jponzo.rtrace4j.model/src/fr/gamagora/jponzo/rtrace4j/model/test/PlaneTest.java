package fr.gamagora.jponzo.rtrace4j.model.test;

import fr.gamagora.jponzo.rtrace4j.model.impl.Plane;
import fr.gamagora.jponzo.rtrace4j.model.impl.Ray;
import fr.gamagora.jponzo.rtrace4j.model.impl.Sphere;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IPlane;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IRay;
import fr.gamagora.jponzo.rtrace4j.utils.impl.Vec3;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public class PlaneTest {
	public static void main(String[] args) {
		System.out.println("Test Plane");
		IRay ray;
		IVec3 origin, direction;

		IPlane plane;
		IVec3 center;
		IVec3 normal;

		IVec3 intersect;

		//Test Y = 0
		System.out.println("Test Plane.intersect (plane : {0,0,0}, {0, 1, 0}; Ray : {0, 5, 0}, {0, -1, 0})");
		center = new Vec3(0, 0, 0);
		normal = new Vec3(0, 1, 0);
		plane = new Plane("", center, normal);
		origin = new Vec3(0, 5, 0);
		direction = new Vec3(0, -1, 0);
		ray = new Ray(origin, direction);
		intersect = new Vec3(0, 0, 0);
		if (plane.intersectPt(ray) != null && plane.intersectPt(ray).equals(intersect)) {
			System.out.println("evaluatePosition OK");
		} else {
			System.out.println("evaluatePosition KO");
		}

		//Test Y = -2
		System.out.println("Test Plane.intersect (plane : {0,-2,0}, {0, 1, 0}; Ray : {0, 5, 0}, {0, -1, 0})");
		center = new Vec3(0, -2, 0);
		normal = new Vec3(0, 1, 0);
		plane = new Plane("", center, normal);
		origin = new Vec3(0, 5, 0);
		direction = new Vec3(0, -1, 0);
		ray = new Ray(origin, direction);
		intersect = new Vec3(0, -2, 0);
		if (plane.intersectPt(ray) != null && plane.intersectPt(ray).equals(intersect)) {
			System.out.println("evaluatePosition OK");
		} else {
			System.out.println("evaluatePosition KO");
		}

		//Test Y = +2
		System.out.println("Test Plane.intersect (plane : {0,2,0}, {0, 1, 0}; Ray : {0, 5, 0}, {0, -1, 0})");
		center = new Vec3(0, 2, 0);
		normal = new Vec3(0, 1, 0);
		plane = new Plane("", center, normal);
		origin = new Vec3(0, 5, 0);
		direction = new Vec3(0, -1, 0);
		ray = new Ray(origin, direction);
		intersect = new Vec3(0, 2, 0);
		if (plane.intersectPt(ray) != null && plane.intersectPt(ray).equals(intersect)) {
			System.out.println("evaluatePosition OK");
		} else {
			System.out.println("evaluatePosition KO");
		}

		//Test from behind
		System.out.println("Test Plane.intersect (plane : {0,0,0}, {0, 1, 0}; Ray : {0, -1, 0}, {0, -1, 0})");
		center = new Vec3(0, 0, 0);
		normal = new Vec3(0, 1, 0);
		plane = new Plane("", center, normal);
		origin = new Vec3(0, -1, 0);
		direction = new Vec3(0, -1, 0);
		ray = new Ray(origin, direction);
		intersect = new Vec3(0, 0, 0);
		if (plane.intersectPt(ray) == null) {
			System.out.println("evaluatePosition OK");
		} else {
			System.out.println("evaluatePosition KO");
		}

		//Test Digonal Y = 0
		System.out.println("Test Plane.intersect (plane : {0,0,0}, {0, 1, 0}; Ray : {0, 1, 0}, {1, -1, 0})");
		center = new Vec3(0, 0, 0);
		normal = new Vec3(0, 1, 0);
		plane = new Plane("", center, normal);
		origin = new Vec3(0, 1, 0);
		direction = new Vec3(1, -1, 0);
		direction = direction.normalized();
		ray = new Ray(origin, direction);
		intersect = new Vec3(1, 0, 0);
		if (plane.intersectPt(ray) != null && plane.intersectPt(ray).equals(intersect)) {
			System.out.println("evaluatePosition OK");
		} else {
			System.out.println("evaluatePosition KO");
		}

		//Test Digonal Y = 2
		System.out.println("Test Plane.intersect (plane : {0,2,0}, {0, 1, 0}; Ray : {0, 3, 0}, {1, -1, 0})");
		center = new Vec3(0, 2, 0);
		normal = new Vec3(0, 1, 0);
		plane = new Plane("", center, normal);
		origin = new Vec3(0, 3, 0);
		direction = new Vec3(1, -1, 0);
		direction = direction.normalized();
		ray = new Ray(origin, direction);
		intersect = new Vec3(1, 2, 0);
		if (plane.intersectPt(ray) != null && plane.intersectPt(ray).equals(intersect)) {
			System.out.println("evaluatePosition OK");
		} else {
			System.out.println("evaluatePosition KO");
		}

		//Test Digonal Y = -2
		System.out.println("Test Plane.intersect (plane : {0,-2,0}, {0, 1, 0}; Ray : {0, -1, 0}, {1, -1, 0})");
		center = new Vec3(0, -2, 0);
		normal = new Vec3(0, 1, 0);
		plane = new Plane("", center, normal);
		origin = new Vec3(0, -1, 0);
		direction = new Vec3(1, -1, 0);
		direction = direction.normalized();
		ray = new Ray(origin, direction);
		intersect = new Vec3(1, -2, 0);
		if (plane.intersectPt(ray) != null && plane.intersectPt(ray).equals(intersect)) {
			System.out.println("evaluatePosition OK");
		} else {
			System.out.println("evaluatePosition KO");
		}
	}
}
