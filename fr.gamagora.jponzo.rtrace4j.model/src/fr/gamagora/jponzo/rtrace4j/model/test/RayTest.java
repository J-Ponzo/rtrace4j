package fr.gamagora.jponzo.rtrace4j.model.test;

import fr.gamagora.jponzo.rtrace4j.model.impl.Ray;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IRay;
import fr.gamagora.jponzo.rtrace4j.utils.impl.Vec3;
import fr.gamagora.jponzo.rtrace4j.utils.impl.VectorUtils;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public class RayTest {
	public static void main(String[] args) {
		System.out.println("Test Ray");
		IRay r;
		IVec3 o, d, p;
		IVec3 vecRes;
		float t;
		float scalRes;

		//Test evaluatePosition
		System.out.println("Test Ray.evaluatePosition (o = {0, 0, 0}, d = {1, 0, 0}, t = 0)");
		o = new Vec3(0, 0, 0);
		d = new Vec3(1, 0, 0);
		r = new Ray(o, d);
		t = 0;
		vecRes = new Vec3(0, 0, 0);
		if (r.evaluatePosition(t).equals(vecRes)) {
			System.out.println("evaluatePosition OK");
		} else {
			System.out.println("evaluatePosition KO");
		}

		//Test evaluatePosition
		System.out.println("Test Ray.evaluatePosition (o = {0, 0, 0}, d = {1, 0, 0}, t = 13.2)");
		o = new Vec3(0, 0, 0);
		d = new Vec3(1, 0, 0);
		r = new Ray(o, d);
		t = 13.2f;
		vecRes = new Vec3(13.2f, 0, 0);
		if (r.evaluatePosition(t).equals(vecRes)) {
			System.out.println("evaluatePosition OK");
		} else {
			System.out.println("evaluatePosition KO");
		}

		//Test evaluatePosition
		System.out.println("Test Ray.evaluatePosition (o = {0, 0, 0}, d = {1, 0, 0}, t = -13.2)");
		o = new Vec3(0, 0, 0);
		d = new Vec3(1, 0, 0);
		r = new Ray(o, d);
		t = -13.2f;
		vecRes = new Vec3(-13.2f, 0, 0);
		if (r.evaluatePosition(t).equals(vecRes)) {
			System.out.println("evaluatePosition OK");
		} else {
			System.out.println("evaluatePosition KO");
		}

		//Test evaluatePosition
		System.out.println("Test Ray.evaluatePosition (o = {-3, 2, 1}, d = {1.5, -1, -0.5}, t = 2)");
		o = new Vec3(-3, 2, 1);
		d = new Vec3(3, -2, -1);
		r = new Ray(o, d);
		t = o.norm();
		vecRes = new Vec3(0, 0, 0);
		if (r.evaluatePosition(t).equals(vecRes)) {
			System.out.println("evaluatePosition OK");
		} else {
			System.out.println("evaluatePosition KO");
		}

		//Test evaluateParameter
		System.out.println("Test Ray.evaluateParameter (o = {-3, 2, 1}, d = {1.5, -1, -0.5}, p = (0, 0, 0)");
		o = new Vec3(0, 0, 0);
		d = new Vec3(1, 0, 0);
		r = new Ray(o, d);
		p = new Vec3(0, 0, 0);
		scalRes = 0;
		Float testRes = r.evaluateParameter(p);
		if (testRes != null && Math.abs(r.evaluateParameter(p) - scalRes) < VectorUtils.EPS) {
			System.out.println("evaluateParameter OK");
		} else {
			System.out.println("evaluateParameter KO");
		}

		//Test evaluateParameter
		System.out.println("Test Ray.evaluateParameter (o = {-3, 2, 1}, d = {1.5, -1, -0.5}, p = (0, 0, 0)");
		o = new Vec3(0, 0, 0);
		d = new Vec3(1, 0, 0);
		r = new Ray(o, d);
		p = new Vec3(13.2f, 0, 0);
		scalRes = 13.2f;
		testRes = r.evaluateParameter(p);
		if (testRes != null && Math.abs(r.evaluateParameter(p) - scalRes) < VectorUtils.EPS) {
			System.out.println("evaluateParameter OK");
		} else {
			System.out.println("evaluateParameter KO");
		}
		
		System.out.println("Test Ray.evaluateParameter (o = {-3, 2, 1}, d = {1.5, -1, -0.5}, p = (0, 0, 0)");
		o = new Vec3(0, 0, 0);
		d = new Vec3(1, 0, 0);
		r = new Ray(o, d);
		p = new Vec3(-13.2f, 0, 0);
		scalRes = -13.2f;
		testRes = r.evaluateParameter(p);
		if (testRes != null && Math.abs(r.evaluateParameter(p) - scalRes) < VectorUtils.EPS) {
			System.out.println("evaluateParameter OK");
		} else {
			System.out.println("evaluateParameter KO");
		}

		//Test evaluateParameter
		System.out.println("Test Ray.evaluateParameter (o = {-3, 2, 1}, d = {1.5, -1, -0.5}, p = (0, 0, 0)");
		o = new Vec3(-3, 2, 1);
		d = new Vec3(3, -2, -1);
		r = new Ray(o, d);
		p = new Vec3(0, 0, 0);
		scalRes = o.norm();
		testRes = r.evaluateParameter(p);
		if (testRes != null && Math.abs(r.evaluateParameter(p) - scalRes) < VectorUtils.EPS) {
			System.out.println("evaluateParameter OK");
		} else {
			System.out.println("evaluateParameter KO");
		}
	}
}
