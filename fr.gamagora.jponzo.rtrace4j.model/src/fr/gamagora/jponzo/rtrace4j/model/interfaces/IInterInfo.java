package fr.gamagora.jponzo.rtrace4j.model.interfaces;

import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public interface IInterInfo {

	IPrimitive getPrimitive();

	IVec3 getPoint();

	IVec3 getNormal();

	float getT();	
}
