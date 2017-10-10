package fr.gamagora.jponzo.rtrace4j.model.interfaces;

import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public interface IMaterial {
	/**
	 * Sample the output ray/contribution given the input ray and intersection infos
	 * @param inRay the give input ray
	 * @param inter the intput intersection info
	 * @return the sampled ray / contribution
	 */
	ISampleInfo sample(IRay inRay, IInterInfo inter);

	/**
	 * Implement the material characteristic behavior. Basically determines
	 * the attenuation factor of light in output given the input direction and the normal to the surface
	 * @param inDir the given input light direction
	 * @param normal the given normal to the surface
	 * @return the amount the attenuation factor
	 */
	float f(IVec3 inDir, IVec3 normal);
	
	IVec3 getAlbedo();

	void setAlbedo(IVec3 albedo);
}
