package fr.gamagora.jponzo.rtrace4j.model.impl;

import javax.naming.OperationNotSupportedException;

import fr.gamagora.jponzo.rtrace4j.model.interfaces.ICube;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IInterInfo;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IPlane;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IPrimitive;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ISphere;
import fr.gamagora.jponzo.rtrace4j.utils.impl.VectorUtils;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public class InterInfo implements IInterInfo {
	private IPrimitive primitive;
	private IVec3 point;
	private IVec3 normal;
	private float t;

	public InterInfo(IPrimitive primitive, IVec3 intersectPt, float t) throws OperationNotSupportedException {
		super();
		this.primitive = primitive;
		this.t = t;
		this.point = intersectPt;
		if (primitive instanceof ISphere) {
			ISphere sphere = (ISphere) primitive;
			this.normal = VectorUtils.computeNormalizedDirection(sphere.getCenter(), intersectPt);
		} else if (primitive instanceof IPlane) {
			this.normal = ((IPlane) primitive).getNormal();
		} else if (primitive instanceof ICube) {
			this.normal = ((ICube) primitive).getNormalAt(intersectPt);
		} else {
			throw new OperationNotSupportedException();
		}
	}

	@Override
	public IVec3 getNormal() {
		return normal;
	}

	@Override
	public float getT() {
		return t;
	}

	@Override
	public IPrimitive getPrimitive() {
		return primitive;
	}

	@Override
	public IVec3 getPoint() {
		return point;
	}


}
