package fr.gamagora.jponzo.rtrace4j.model.interfaces;

import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public interface IRay {

	IVec3 getOrigin();

	void setOrigin(IVec3 origine);

	IVec3 getDirection();

	void setDirection(IVec3 direction);
	
	/**
	 * compute the position at the given t parameter
	 * @param t the given parameter
	 * @return the t parametred position along the ray
	 */
	IVec3 evaluatePosition(float t);

	/**
	 * Invert function of IVec3 evaluatePosition(float t). Try to find the t parameter given a position.
	 * If the given position is not on the ray path, null is return
	 * @param position the given position
	 * @return the t parameter of the given position if exists, null otherwise
	 */
	Float evaluateParameter(IVec3 position);

	/**
	 * Compute the normalized direction vector (not computed each times, cache optimization is implemented)
	 * @return the normalized vector 
	 */
	IVec3 getNormalizedDirection();
}
