package fr.gamagora.jponzo.rtrace4j.model.impl;

import fr.gamagora.jponzo.rtrace4j.model.interfaces.IBoundingBox;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IRay;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ISphere;
import fr.gamagora.jponzo.rtrace4j.utils.impl.Vec3;
import fr.gamagora.jponzo.rtrace4j.utils.impl.VectorUtils;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public class Sphere extends Primitive implements ISphere {
	private IVec3 center;
	private float radius;

	public Sphere(String name, IVec3 center, float radius) {
		super(name);
		this.center = center;
		this.radius = radius;
	}

	@Override
	public IVec3 getCenter() {
		return center;
	}

	@Override
	public void setCenter(IVec3 center) {
		this.center = center;
	}

	@Override
	public float getRadius() {
		return radius;
	}

	@Override
	public void setRadius(float radius) {
		this.radius = radius;
	}

	@Override
	public Float intersectParam(IRay ray) {
		IVec3 U = ray.getDirection().normalized();
		IVec3 C = ray.getOrigin();
		IVec3 O = this.center;
		float R = this.radius;

		//Compute Delta
		IVec3 CmO = C.sub(O);
		float a = 1;
		float b = 2 * U.dot(CmO);
		float c = (float) (Math.pow(CmO.norm(), 2) - Math.pow(R, 2));
		float delta = b * b - 4 * a * c;

		//find the solution
		float t = 0;
		if (delta > 0) {
			float sqrtDelta = (float) Math.sqrt(delta);
			float t1 = (- b + sqrtDelta) / (2 * a);
			float t2 = (- b - sqrtDelta) / (2 * a);

			if ((t1 < 0 || Math.abs(t1) < VectorUtils.EPS) && (t2 < 0 || Math.abs(t1) < VectorUtils.EPS)) {
				return null;
			} else if (t1 < 0) {
				t = t2;
			} else if (t2 < 0) {
				t = t1;
			} else if (t1 < t2) {
				t = t1;
			} else if (t2 < t1) {
				t = t2;
			}
		} else if (Math.abs(delta) < VectorUtils.EPS) {
			t = -b / (2 * a);
			if (t < 0) {
				return null;
			}
		} else {
			return null;
		}

		//find and return intersection
		return t;
	}

	@Override
	public IVec3 getNormalAt(IVec3 point) {
		if (!belongToSurface(point)) {
			return null;
		}
		IVec3 n = VectorUtils.computeDirection(center, point).normalized();
		return n;
	}
	
	@Override
	public IBoundingBox createBoundingBox() {
		return new BoundingBox(this.getName() + "_Box", 
				this.center.getX() - this.radius, this.center.getX() + this.radius, 
				this.center.getY() + this.radius, this.center.getY() - this.radius, 
				this.center.getZ() + this.radius, this.center.getZ() - this.radius);
	}

	@Override
	public boolean belongToSurface(IVec3 point) {
		IVec3 dir = VectorUtils.computeDirection(center, point);
		if (Math.abs(dir.norm() - radius) < VectorUtils.EPS) {
			return true;
		}
		return false;
	}
}
