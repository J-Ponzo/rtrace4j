package fr.gamagora.jponzo.rtrace4j.utils.impl;

import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public class VectorUtils {
	/**
	 * Epsilon : An infinitesimally close to zero value
	 */
	public static float EPS = 0.00001f;
	
	/**
	 * Compute the reflected ray direction given the input ray and the normal to the surface
	 * @param I the incoming ray direction
	 * @param n the normal vector of the surface
	 * @return the reflected ray direction
	 */
	public static IVec3 computeReflectedRay(IVec3 I, IVec3 n) {
		I = I.normalized();
		n = n.normalized();
		IVec3 nPrim = n.mult(I.dot(n));
		IVec3 S = nPrim.mult(-2.0f).sum(I).normalized();
		return S;
	}
	
	/**
	 * Compute the reflected ray direction given the input ray and the normal to the surface
	 * @param I the incoming ray direction
	 * @param n the normal vector of the surface
	 * @param n1 refration index of outside environment
	 * @param n2 refration index of inside environment
	 * @return the reflected ray direction
	 */
	public static IVec3 computeRefractedRay(IVec3 I, IVec3 n, float n1, float n2) {
		float ICrossn = I.cross(n).norm();
		float IDotn = I.dot(n);
		float teta = (float) Math.asin((n1 / n2) * ICrossn);
		if (Float.isNaN(teta)) {
			return I;
		}
		float alpha = (float) (ICrossn / (Math.tan(teta)) * Math.signum(IDotn) - IDotn);
		IVec3 S = I.sum(n.mult(alpha));
		
		return S.normalized();
	}
	
	/**
	 * Compute the normalized direction from the given source and destination points
	 * @param src the given source point
	 * @param dest the given destination point
	 * @return the normalized direction from source to destination
	 */
	public static IVec3 computeNormalizedDirection(IVec3 src, IVec3 dest) {
		return computeDirection(src, dest).normalized();
	}
	
	/**
	 * Compute the direction from the given source and destination points
	 * @param src the given source point
	 * @param dest the given destination point
	 * @return the direction from source to destination
	 */
	public static IVec3 computeDirection(IVec3 src, IVec3 dest) {
		return dest.sub(src);
	}
	
	/**
	 * Compute the distence between the 2 given points
	 * @param p1 first given point
	 * @param p2 second given point
	 * @return the distance between the 2 points
	 */
	public static float computeDistance(IVec3 p1, IVec3 p2) {
		return computeDirection(p1, p2).norm();
	}
}
