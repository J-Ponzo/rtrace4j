package fr.gamagora.jponzo.rtrace4j.model.interfaces;

import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public interface IEntity {
	String getName();

	void setName(String name);
	
	/**
	 * Detect any intersection with the given ray (if various exists, the most close to the origin of the ray is chosen)
	 * @param ray the given ray
	 * @return the intersection info object containing the t parameter, the normal, the primitive reference etc... if exists, null otherwise
	 */
	IInterInfo intersect(IRay ray);
	
	/**
	 * Detect any intersection with the given ray (if various exists, the most close to the origin of the ray is chosen)
	 * @param ray the given ray
	 * @return the intersection point with the ray if exists, null otherwise
	 */
	IVec3 intersectPt(IRay ray);

	/**
	 * Detect any intersection with the given ray (if various exists, the most close to the origin of the ray is chosen)
	 * @param ray the given ray
	 * @return the intersection parameter t with the ray if exists, null otherwise
	 */
	Float intersectParam(IRay ray);
	
	/**
	 * Get the normal given a point of the surface
	 * @param point the given point
	 * @return the normal to the given point if exists. null otherwise
	 */
	public IVec3 getNormalAt(IVec3 point);
	
	/**
	 * Check if the given points belong to the entity surface
	 * @param point the given point
	 * @return true if the point belong to tho surface. false otherwise
	 */
	public boolean belongToSurface(IVec3 point);
}
