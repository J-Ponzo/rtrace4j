package fr.gamagora.jponzo.rtrace4j.model.impl;

import fr.gamagora.jponzo.rtrace4j.model.interfaces.IPlane;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IRay;
import fr.gamagora.jponzo.rtrace4j.utils.impl.VectorUtils;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public class Plane extends Primitive implements IPlane {
	private IVec3 origin;
	private IVec3 normal;

	public Plane(String name, IVec3 origin, IVec3 normal) {
		super(name);
		this.origin = origin;
		this.normal = normal;
	}

	@Override
	public IVec3 getOrigin() {
		return origin;
	}

	@Override
	public void setOrigin(IVec3 origin) {
		this.origin = origin;
	}

	@Override
	public IVec3 getNormal() {
		return normal;
	}

	@Override
	public void setNormal(IVec3 normal) {
		this.normal = normal;
	}

	@Override
	public Float intersectParam(IRay ray) {
		//Line parameters
		float A = ray.getOrigin().getX();
		float B = ray.getOrigin().getY();
		float C = ray.getOrigin().getZ();
		float alpha = ray.getNormalizedDirection().getX();
		float beta = ray.getNormalizedDirection().getY();
		float gamma = ray.getNormalizedDirection().getZ();

		//Plane parameters
		float a = this.normal.getX();
		float b = this.normal.getY();
		float c = this.normal.getZ();
		float d = -(a * this.origin.getX() + b * origin.getY() + c * origin.getZ());

		//Find Solution
		if (Math.abs(ray.getDirection().dot(this.normal)) < VectorUtils.EPS ) {		//Case : ray cast is parallel the plane
			if (Math.abs(a * A + b * B + c * C + d) < VectorUtils.EPS) {				//Case : the plane contains the ray
				return 0f;
			} else {																	//Case : the ray is outside the plane
				return null;
			}
		} else {																	//Case : General
			float t = -(a * A + b * B + c * C + d) / (a * alpha + b * beta + c * gamma);
			if (t > 0) {
				return t;
			}
		}
		return null;
	}

	@Override
	public IVec3 getNormalAt(IVec3 point) {
		if (!belongToSurface(point)) {
			return null;
		}
		return this.normal;
	}

	@Override
	public boolean belongToSurface(IVec3 point) {
		float A = point.getX();
		float B = point.getY();
		float C = point.getZ();
		float a = this.normal.getX();
		float b = this.normal.getY();
		float c = this.normal.getZ();
		float d = -(a * this.origin.getX() + b * origin.getY() + c * origin.getZ());
		if (Math.abs(a * A + b * B + c * C + d) < VectorUtils.EPS) {
			return true;
		}	
		return false;
	}
}
