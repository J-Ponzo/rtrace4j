package fr.gamagora.jponzo.rtrace4j.model.interfaces;

import java.util.List;

public interface IBoundingBox extends IEntity {

	float getLeftBound();

	float getRightBound();

	float getTopBound();

	float getBottomBound();

	float getNearBound();

	float getFarBound();

	/**
	 * TODO Wrong test if intersection is not empty
	 * Test if the given boxe is inside (or crossing the border of) the
	 * this boxe
	 * @param the given bounding box is inside, false otherwise
	 * @return true if the box is inside
	 */
	boolean contains(IBoundingBox primitiveBox);

	/**
	 * Adapt the box to its content
	 * @param contents the boxable cloud to adapt to
	 */
	void updateBouningBox(List<IBoxable> contents);
}
