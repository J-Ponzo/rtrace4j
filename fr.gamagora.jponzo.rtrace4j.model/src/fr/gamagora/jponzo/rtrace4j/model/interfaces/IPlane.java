package fr.gamagora.jponzo.rtrace4j.model.interfaces;

import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public interface IPlane extends IPrimitive {

	IVec3 getOrigin();

	void setOrigin(IVec3 origin);

	IVec3 getNormal();

	void setNormal(IVec3 normal);
	
}
