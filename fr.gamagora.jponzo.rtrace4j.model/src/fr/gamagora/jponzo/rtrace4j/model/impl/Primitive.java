package fr.gamagora.jponzo.rtrace4j.model.impl;

import fr.gamagora.jponzo.rtrace4j.model.interfaces.IMaterial;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IPrimitive;
import fr.gamagora.jponzo.rtrace4j.utils.impl.Vec3;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public abstract class Primitive extends Entity implements IPrimitive {	
	protected IMaterial material;
	
	public Primitive(String name) {
		super(name);
	}

	@Override
	public IMaterial getMaterial() {
		return this.material;
	}
	
	@Override
	public void setMaterial(IMaterial material) {
		this.material = material;
	}
}
