package fr.gamagora.jponzo.rtrace4j.model.interfaces;

public interface IBoxable extends IPrimitive {
	
	/**
	 * Create the minimal bounding box containing the primitive
	 * @return the minimal bounding box
	 */
	IBoundingBox createBoundingBox();
	
}
