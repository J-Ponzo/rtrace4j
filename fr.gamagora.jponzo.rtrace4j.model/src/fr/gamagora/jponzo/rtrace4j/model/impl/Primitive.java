package fr.gamagora.jponzo.rtrace4j.model.impl;

import fr.gamagora.jponzo.rtrace4j.model.interfaces.IPrimitive;
import fr.gamagora.jponzo.rtrace4j.utils.impl.Vec3;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public abstract class Primitive extends Entity implements IPrimitive {	
	protected IVec3 color = new Vec3(255, 255, 255);
	protected float specVal = 0;
	protected float transVal = 0;
	protected float diffVal = 1f;
	protected float refractIndex = 1.1f;
	
	public Primitive(String name) {
		super(name);
	}

	@Override
	public IVec3 getColor() {
		return color;
	}

	@Override
	public void setColor(IVec3 color) {
		this.color = color;
	}

	@Override
	public float getSpecVal() {
		return specVal;
	}

	@Override
	public void setSpecVal(float specVal) {
		this.specVal = specVal;
	}

	@Override
	public float getTransVal() {
		return transVal;
	}

	@Override
	public void setTransVal(float transVal) {
		this.transVal = transVal;
	}

	@Override
	public float getDiffVal() {
		return diffVal;
	}

	@Override
	public void setDiffVal(float diffVal) {
		this.diffVal = diffVal;
	}

	@Override
	public float getRefractIndex() {
		return refractIndex;
	}

	@Override
	public void setRefractIndex(float refractIndex) {
		this.refractIndex = refractIndex;
	}
}
