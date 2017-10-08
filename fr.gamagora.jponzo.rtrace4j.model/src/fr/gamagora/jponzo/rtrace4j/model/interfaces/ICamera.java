package fr.gamagora.jponzo.rtrace4j.model.interfaces;

import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public interface ICamera {

	float getFov();

	void setFov(float fov);

	IVec3 getPosition();

	void setPosition(IVec3 position);

	int getWidth();

	int getHeight();

	int getDepth();
	
	int[][][] getImgTable();
}
