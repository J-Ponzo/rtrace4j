package fr.gamagora.jponzo.rtrace4j.model.test;

import fr.gamagora.jponzo.rtrace4j.utils.impl.Vec3;
import fr.gamagora.jponzo.rtrace4j.utils.impl.VectorUtils;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public class Vec3Test {
	public static void main(String[] args) {
		System.out.println("Test Vec3");
		
		IVec3 u, v, vecRes;
		float scalRes;
		
		//sum test
		System.out.println("Test Vec3.sum");
		u = new Vec3(1, -2, 3.5f);
		v = new Vec3(-7.2f, 5, -2f);
		vecRes = new Vec3(-6.2f, 3, 1.5f);
		if (u.sum(v).equals(vecRes)) {
			System.out.println("Sum OK");
		} else {
			System.out.println("Sum KO");
		}
		
		//sub test
		System.out.println("Test Vec3.sub");
		u = new Vec3(1, -2, 3.5f);
		v = new Vec3(-7.2f, 5, -2f);
		vecRes = new Vec3(8.2f, -7, 5.5f);
		if (u.sub(v).equals(vecRes)) {
			System.out.println("Sub OK");
		} else {
			System.out.println("Sub KO");
		}
		
		//mult vect test
		System.out.println("Test Vec3.mult(vec)");
		u = new Vec3(1, -2, 3.5f);
		v = new Vec3(3, 5.5f, -2f);
		vecRes = new Vec3(3, -11f, -7);
		if (u.mult(v).equals(vecRes)) {
			System.out.println("Mult Vec OK");
		} else {
			System.out.println("Mult Vec KO");
		}
		
		System.out.println("Test Vec3.mult(scal)");
		u = new Vec3(1, -2, 3.5f);
		float k = 3;
		vecRes = new Vec3(3, -6, 10.5f);
		if (u.mult(k).equals(vecRes)) {
			System.out.println("Mult Scal OK");
		} else {
			System.out.println("Mult Scal KO");
		}
		
		//div test
		System.out.println("Test Vec3.div");
		u = new Vec3(4, -2, 3.5f);
		v = new Vec3(2, 0.5f, -2f);
		vecRes = new Vec3(2, -4, -1.75f);
		if (u.div(v).equals(vecRes)) {
			System.out.println("Div OK");
		} else {
			System.out.println("Div KO");
		}
		
		//norm2 test
		System.out.println("Test Vec3.norm2");
		u = new Vec3(4, -2, 3.5f);
		scalRes = 32.25f;
		if (Math.abs(u.norm2() - scalRes) < VectorUtils.EPS) {
			System.out.println("Norm2 OK");
		} else {
			System.out.println("Norm2 KO");
		}
		
		//norm test
		System.out.println("Test Vec3.norm");
		u = new Vec3(0, -4, 3);
		scalRes = 5f;
		if (Math.abs(u.norm() - scalRes) < VectorUtils.EPS) {
			System.out.println("Norm OK");
		} else {
			System.out.println("Norm KO");
		}
		
		//normalized test
		
		
		//dot test
		System.out.println("Test Vec3.dot");
		u = new Vec3(4, -2, 3.5f);
		v = new Vec3(2, 0.5f, -2f);
		scalRes = 0;
		if (Math.abs(u.dot(v) - scalRes) < VectorUtils.EPS) {
			System.out.println("Dot OK");
		} else {
			System.out.println("Dot KO");
		}
		
		//cross test
		System.out.println("Test Vec3.cross");
		u = new Vec3(4, -2, 3.5f);
		v = new Vec3(2, 0.5f, -2f);
		vecRes = new Vec3(2.25f, 15, 6);
		if (u.cross(v).equals(vecRes)) {
			System.out.println("Cross OK");
		} else {
			System.out.println("Cross KO");
		}
	}
}
