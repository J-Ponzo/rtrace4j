package fr.gamagora.jponzo.rtrace4j.model.impl;

import fr.gamagora.jponzo.rtrace4j.model.interfaces.IEntity;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IInterInfo;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IRay;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public abstract class Entity implements IEntity {
	protected String name;
	
	public Entity(String name) {
		super();
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public IInterInfo intersect(IRay ray) {
		float t = intersectParam(ray);
		return null;
	}
	
	@Override
	public IVec3 intersectPt(IRay ray) {
		Float t = intersectParam(ray);

		//find and return intersection
		if (t == null) {
			return null;
		} else {
			return ray.evaluatePosition(t);
		}
	}
}
