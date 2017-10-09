package fr.gamagora.jponzo.rtrace4j.utils.impl;

import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public class MathUtils {
	/**
	 * Normalize the given x C R+ using y = log(x + 1)
	 * @param the given x val
	 * @return the normalized intensity
	 */
	public static float logNormalize(float x) {
		return (float) Math.log((x + 1));
	}

	/**
	 * Compute a random point on the sphere border given the sphere params and 2 random number r1 & r2
	 * @param cx the x of the sphere center
	 * @param cy the y of the sphere center
	 * @param cz the z of the sphere center
	 * @param r the radius
	 * @param r1 the first random number
	 * @param r2 the second random number
	 * @return a randomized point on the sphere border
	 */
	public static IVec3 randomizePointOnSphere(float cx, float cy, float cz, float r, float r1, float r2) {
		float x = (float) (cx + 2f * r * Math.cos(2f * Math.PI * r1) * Math.sqrt(r2 * (1f - r2)));
		float y = (float) (cy + 2f * r * Math.sin(2f * Math.PI * r1) * Math.sqrt(r2 * (1f - r2)));
		float z = cz + r * (1f - 2f * r2);
		return new Vec3(x, y, z);
	}

	/**
	 * Find random point on an hemisphere given its normal and origin as well as 2 random points
	 * @param origin origin of the hemisphere
	 * @param normal normal of the hemisphere
	 * @param r1 first random number
	 * @param r2 second random number
	 * @return The randomized point on the hemisphere
	 */
	public static IVec3 randomizePointOnHemisphere(IVec3 origin, IVec3 normal, float r1, float r2) {
		IVec3 randPt = randomizeCosPIPointOnUnitHemisphere(r1, r2);
		IVec3[] basis = getBasisFromAxis(normal);
		IVec3 rotatedPt = switchBasis(randPt, basis[0], basis[1], basis[2]);
		IVec3 finalPt = rotatedPt.sum(origin);
		return finalPt;
	}

	/**
	 * Find uniform random point on a unit hemisphere
	 * @param origin origin of the hemisphere
	 * @param normal normal of the hemisphere
	 * @param r1 first random number
	 * @param r2 second random number
	 * @return The uniform randomized point on the unit hemisphere
	 */
	public static IVec3 randomizeUniformPointOnUnitHemisphere(float r1, float r2) {
		float x = (float) (Math.cos(2f * Math.PI * r1) * Math.sqrt(1f - r2 * r2));
		float y = (float) (Math.sin(2f * Math.PI * r1) * Math.sqrt(1f - r2 * r2));
		float z = r2;
		return new Vec3(x, y, z);
	}

	/**
	 * Find random point on a unit hemisphere using cos pdf
	 * @param origin origin of the hemisphere
	 * @param normal normal of the hemisphere
	 * @param r1 first random number
	 * @param r2 second random number
	 * @return The cos randomized point on the unit hemisphere
	 */
	public static IVec3 randomizeCosPIPointOnUnitHemisphere(float r1, float r2) {
		float x = (float) (Math.cos(2f * Math.PI * r1) * Math.sqrt(1f - r2));
		float y = (float) (Math.sin(2f * Math.PI * r1) * Math.sqrt(1f - r2));
		float z = (float) Math.sqrt(r2);
		return new Vec3(x, y, z);
	}

	/**
	 * Generate random basis given one fixed axis
	 */
	public static IVec3[] getBasisFromAxis(IVec3 axis) {
		float sign = Math.signum(axis.getZ());
		if (sign == 0) {
			sign = 1;
		}
		float a = -1f / (sign + axis.getZ());
		float b = axis.getX() * axis.getY() * a;
		

		IVec3[] basis = new IVec3[3];
		basis[0] = axis.normalized();
		basis[1] = new Vec3(1f + sign * axis.getX() * axis.getX() * a, sign * b, -sign * axis.getX());
		basis[2] = new Vec3(b, sign + axis.getY() * axis.getY() * a, -axis.getY());

		return basis;
	}

	/**
	 * TODO create basis object and use it instead of {i, j, k} vectors
	 * Perform a basis switch of the given v vector to {i, j, k}
	 * @param v the vector to switch
	 * @param i the i unit vector of the target basis
	 * @param j the j unit vector of the target basis
	 * @param k the k unit vector of the target basis
	 * @return an new vector instance representing v expressed on the target basis
	 */
	public static IVec3 switchBasis(IVec3 v, IVec3 i, IVec3 j, IVec3 k) {
		float a = v.getX();
		float b = v.getY();
		float c = v.getZ();
		IVec3 aI = i.mult(a);
		IVec3 bJ = j.mult(b);
		IVec3 cK = k.mult(c);
		return aI.sum(bJ).sum(cK);
	}
}
