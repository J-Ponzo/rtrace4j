package fr.gamagora.jponzo.rtrace4j.model.interfaces;

import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public interface ILight {
	String getName();

	void setName(String name);
	
	IVec3 getPosition();

	void setPosition(IVec3 position);

	IVec3 getIntensity();

	void setIntensity(IVec3 intensity);

	float getRadius();

	void setRadius(float radius);
}
