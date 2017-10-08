package fr.gamagora.jponzo.rtrace4j.model.interfaces;

import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public interface IPrimitive extends IEntity {
	IVec3 getColor();

	void setColor(IVec3 color);
	
	float getSpecVal();

	void setSpecVal(float specVal);

	float getTransVal();

	void setTransVal(float transVal);
	
	float getDiffVal();

	void setDiffVal(float diffVal);
	
	float getRefractIndex();

	void setRefractIndex(float refractIndex);
	
}
