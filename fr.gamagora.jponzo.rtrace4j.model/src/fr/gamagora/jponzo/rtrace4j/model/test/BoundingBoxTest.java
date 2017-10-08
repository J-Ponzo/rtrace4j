package fr.gamagora.jponzo.rtrace4j.model.test;

import fr.gamagora.jponzo.rtrace4j.model.impl.BoundingBox;
import fr.gamagora.jponzo.rtrace4j.model.impl.Ray;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IBoundingBox;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IRay;
import fr.gamagora.jponzo.rtrace4j.utils.impl.Vec3;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public class BoundingBoxTest {
	public static void main(String[] args) {
		System.out.println("Test Bounding Box");
		IRay ray;
		IVec3 origin, direction;

		IBoundingBox box;
		float left;
		float right;
		float top;
		float bottom;
		float near;
		float far;

		IVec3 intersect;

		//Test Hit on top
		System.out.println("Test Bounding.intersect (Box : {-1, 1, 1, -1, 1, -1}; Ray : {0, 5, 0}, {0, -1, 0})");
		left = -1;
		right = 1;
		top = 1;
		bottom = -1;
		near = 1;
		far = -1;
		box = new BoundingBox("", left, right, top, bottom, near, far);

		origin = new Vec3(0, 5, 0);
		direction = new Vec3(0, -1, 0);
		ray = new Ray(origin, direction);

		intersect = new Vec3(0, 1, 0);
		if (box.intersectPt(ray) != null && box.intersectPt(ray).equals(intersect)) {
			System.out.println("evaluatePosition OK");
		} else {
			System.out.println("evaluatePosition KO");
		}

		//Test Hit on bottom
		System.out.println("Test Bounding.intersect (Box : {-1, 1, 1, -1, 1, -1}; Ray : {0, -5, 0}, {0, 1, 0})");
		left = -1;
		right = 1;
		top = 1;
		bottom = -1;
		near = 1;
		far = -1;
		box = new BoundingBox("", left, right, top, bottom, near, far);

		origin = new Vec3(0, -5, 0);
		direction = new Vec3(0, 1, 0);
		ray = new Ray(origin, direction);

		intersect = new Vec3(0, -1, 0);
		if (box.intersectPt(ray) != null && box.intersectPt(ray).equals(intersect)) {
			System.out.println("evaluatePosition OK");
		} else {
			System.out.println("evaluatePosition KO");
		}

		//Test Hit on left
		System.out.println("Test Bounding.intersect (Box : {-1, 1, 1, -1, 1, -1}; Ray : {-5, 0, 0}, {1, 0, 0})");
		left = -1;
		right = 1;
		top = 1;
		bottom = -1;
		near = 1;
		far = -1;
		box = new BoundingBox("", left, right, top, bottom, near, far);

		origin = new Vec3(-5, 0, 0);
		direction = new Vec3(1, 0, 0);
		ray = new Ray(origin, direction);

		intersect = new Vec3(-1, 0, 0);
		if (box.intersectPt(ray) != null && box.intersectPt(ray).equals(intersect)) {
			System.out.println("evaluatePosition OK");
		} else {
			System.out.println("evaluatePosition KO");
		}

		//Test Hit on right
		System.out.println("Test Bounding.intersect (Box : {-1, 1, 1, -1, 1, -1}; Ray : {5, 0, 0}, {-1, 0, 0})");
		left = -1;
		right = 1;
		top = 1;
		bottom = -1;
		near = 1;
		far = -1;
		box = new BoundingBox("", left, right, top, bottom, near, far);

		origin = new Vec3(5, 0, 0);
		direction = new Vec3(-1, 0, 0);
		ray = new Ray(origin, direction);

		intersect = new Vec3(1, 0, 0);
		if (box.intersectPt(ray) != null && box.intersectPt(ray).equals(intersect)) {
			System.out.println("evaluatePosition OK");
		} else {
			System.out.println("evaluatePosition KO");
		}

		//Test Hit on near
		System.out.println("Test Bounding.intersect (Box : {-1, 1, 1, -1, 1, -1}; Ray : {0, 0, 5}, {0, 0, -1})");
		left = -1;
		right = 1;
		top = 1;
		bottom = -1;
		near = 1;
		far = -1;
		box = new BoundingBox("", left, right, top, bottom, near, far);

		origin = new Vec3(0, 0, 5);
		direction = new Vec3(0, 0, -1);
		ray = new Ray(origin, direction);

		intersect = new Vec3(0, 0, 1);
		if (box.intersectPt(ray) != null && box.intersectPt(ray).equals(intersect)) {
			System.out.println("evaluatePosition OK");
		} else {
			System.out.println("evaluatePosition KO");
		}

		//Test Hit on far
		System.out.println("Test Bounding.intersect (Box : {-1, 1, 1, -1, 1, -1}; Ray : {0, 0, -5}, {0, 0, 1})");
		left = -1;
		right = 1;
		top = 1;
		bottom = -1;
		near = 1;
		far = -1;
		box = new BoundingBox("", left, right, top, bottom, near, far);

		origin = new Vec3(0, 0, -5);
		direction = new Vec3(0, 0, 1);
		ray = new Ray(origin, direction);

		intersect = new Vec3(0, 0, -1);
		if (box.intersectPt(ray) != null && box.intersectPt(ray).equals(intersect)) {
			System.out.println("evaluatePosition OK");
		} else {
			System.out.println("evaluatePosition KO");
		}
	}
}
