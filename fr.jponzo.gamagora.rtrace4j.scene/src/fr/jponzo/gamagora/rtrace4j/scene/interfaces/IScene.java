package fr.jponzo.gamagora.rtrace4j.scene.interfaces;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import fr.gamagora.jponzo.rtrace4j.model.interfaces.ICamera;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IInterInfo;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ILight;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IPrimitive;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IRay;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public interface IScene {

	List<IPrimitive> getPrimitives();
	
	List<ILight> getLights();

	ICamera getCamera();
	
	void setCamera(ICamera camera);
	
	/**
	 * Add the given primitive to the scene. (If the primitive reference already exists, do nothing, no duplicate values allowed)
	 * @param primitive the given primitive 
	 */
	void addPrimitive(IPrimitive primitive);
	
	/**
	 * Remove the given primitive from the scene. (The given primitive must be the instance to remove)
	 * @param primitive the given primitive 
	 */
	void removePrimitive(IPrimitive primitive);
	
	/**
	 * Add the given light to the scene. (If the light reference already exists, do nothing, no duplicate values allowed)
	 * @param light the given light 
	 */
	void addLight(ILight light);

	/**
	 * Check if the fromPt is visible from the toPt in this scene
	 * @param fromPt the from point
	 * @param toPt the to point
	 * @return true if visible, false otherwise
	 * @throws OperationNotSupportedException
	 */
	boolean isVisibleFrom(IVec3 fromPt, IVec3 toPt) throws OperationNotSupportedException;

	/**
	 * Perform some pre-treatments in order to optimize the scene
	 * @param maxTreeDepth the max depth of the bounding tree
	 */
	void init(int maxTreeDepth);
}
