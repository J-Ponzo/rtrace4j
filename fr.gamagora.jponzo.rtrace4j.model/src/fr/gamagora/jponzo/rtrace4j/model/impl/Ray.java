package fr.gamagora.jponzo.rtrace4j.model.impl;

import fr.gamagora.jponzo.rtrace4j.model.interfaces.IRay;
import fr.gamagora.jponzo.rtrace4j.utils.impl.Vec3;
import fr.gamagora.jponzo.rtrace4j.utils.impl.VectorUtils;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public class Ray implements IRay {
	private IVec3 origin;
	private IVec3 direction;
	
	//Vars for normal caching purpose
	private boolean isDirty = true;				//On direction access, the normal direction might be altered. This flag is set accordingly
	private IVec3 cachedNormalizedDirection = new Vec3(0, 0, 0);

	public Ray(IVec3 origine, IVec3 direction) {
		super();
		this.origin = origine;
		this.direction = direction;
		unpdateCachedValue();
	}

	private void unpdateCachedValue() {
		this.cachedNormalizedDirection = this.direction.normalized();
		this.isDirty = false;
	}

	@Override
	public IVec3 getOrigin() {
		return origin;
	}

	@Override
	public void setOrigin(IVec3 origine) {
		this.origin = origine;
	}

	@Override
	public IVec3 getDirection() {
		this.isDirty = false;
		return direction;
	}

	@Override
	public void setDirection(IVec3 direction) {
		this.isDirty = false;
		this.direction = direction;
	}

	@Override
	public IVec3 evaluatePosition(float t) {
		return origin.sum(this.getNormalizedDirection().mult(t));
	}

	@Override
	public Float evaluateParameter(IVec3 position) {
		IVec3 OP = VectorUtils.computeDirection(origin, position);
		IVec3 cross = OP.cross(direction);
		if (cross.equals(new Vec3(0, 0, 0))) {
			float t = OP.norm();
			IVec3 proj = this.evaluatePosition(t);
			if (proj.equals(position, VectorUtils.EPS * 1000)) {
				return t;
			} else {
				return -t;
			}
		}
		
		return null;
	}
	
	@Override
	public IVec3 getNormalizedDirection() {
		if (this.isDirty) {
			unpdateCachedValue();
		}
		return cachedNormalizedDirection;
	}
}
