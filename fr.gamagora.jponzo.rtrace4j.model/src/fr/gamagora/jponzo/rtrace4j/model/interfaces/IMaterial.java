package fr.gamagora.jponzo.rtrace4j.model.interfaces;

import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public interface IMaterial {
	ISampleInfo sample(IRay inRay, IInterInfo inter);

	IVec3 getAlbedo();

	void setAlbedo(IVec3 albedo);
}
