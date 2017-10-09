package fr.gamagora.jponzo.rtrace4j.model.impl;

import fr.gamagora.jponzo.rtrace4j.model.interfaces.IMaterial;
import fr.gamagora.jponzo.rtrace4j.utils.impl.Vec3;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public abstract class Material implements IMaterial {
	protected IVec3 albedo = new Vec3(1, 1, 1);

	@Override
	public IVec3 getAlbedo() {
		return albedo;
	}

	@Override
	public void setAlbedo(IVec3 albedo) {
		this.albedo = albedo;
	}
}
