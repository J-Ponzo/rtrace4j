package fr.gamagora.jponzo.rtrace4j.model.interfaces;

public interface IBoundingHierarchy extends IEntity {
	/**
	 * Add the given boxable to the BH content
	 * @param boxable the given boxable
	 */
	void addContent(IBoxable boxable);

	/**
	 * Remove the given boxable from the BH content
	 * @param boxable the given boxable
	 */
	void removeContent(IBoxable boxable);
	
	/**
	 * Recursively split the BH along its longest axis
	 * @param maxRec the number of recursive calls
	 */
	void split(int maxRec);
}
