package fr.gamagora.jponzo.rtrace4j.model.interfaces;

import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public interface ISphere extends IBoxable {

	String getName();

	void setName(String name);
	
	IVec3 getCenter();

	void setCenter(IVec3 center);

	float getRadius();

	void setRadius(float radius);
}
