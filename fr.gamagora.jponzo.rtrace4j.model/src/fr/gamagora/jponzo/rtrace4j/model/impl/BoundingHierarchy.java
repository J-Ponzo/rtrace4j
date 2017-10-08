package fr.gamagora.jponzo.rtrace4j.model.impl;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import fr.gamagora.jponzo.rtrace4j.model.interfaces.IBoundingBox;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IBoundingHierarchy;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IBoxable;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IInterInfo;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IRay;
import fr.gamagora.jponzo.rtrace4j.utils.impl.VectorUtils;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public class BoundingHierarchy extends Entity implements IBoundingHierarchy {
	private IBoundingBox boundingBox;
	private IBoundingHierarchy leftBH = null;
	private IBoundingHierarchy rightBH = null;
	private List<IBoxable> contents = new ArrayList<IBoxable>();

	public BoundingHierarchy(String name) {
		super(name);
		boundingBox = new BoundingBox("", 0, 0, 0, 0, 0, 0);
	}

	@Override
	public Float intersectParam(IRay ray) {
		return boundingBox.intersectParam(ray);
	}

	@Override
	public IInterInfo intersect(IRay ray) {
		//Recherche dans les sous-scenes
		if (leftBH != null && rightBH != null) {
			Float leftDist = leftBH.intersectParam(ray);
			Float rightDist = rightBH.intersectParam(ray);
			if (leftDist == null && rightDist == null) {
				return null;
			} else if (leftDist == null) {
				return rightBH.intersect(ray);
			} else if (rightDist == null) {
				return leftBH.intersect(ray);
			} else if (leftDist < rightDist) {
				IInterInfo interInfo = leftBH.intersect(ray);
				if (interInfo == null) {
					interInfo = rightBH.intersect(ray);
				}
				return interInfo;
			} else {
				IInterInfo interInfo = rightBH.intersect(ray);
				if (interInfo == null) {
					interInfo = leftBH.intersect(ray);
				}
				return interInfo;
			}
		}

		List<IInterInfo> intersects = new ArrayList<IInterInfo>();

		for (IBoxable boxable : contents) {
			Float t = boxable.intersectParam(ray);
			if (t == null) continue;
			IVec3 intersect = ray.evaluatePosition(t);
			if (intersect != null) {
				try {
					intersects.add(new InterInfo(boxable, intersect, t));
				} catch (OperationNotSupportedException e) {
					e.printStackTrace();
				}
			}
		}

		//Look for the closest
		IInterInfo closestIntersect = null;
		IVec3 origin = ray.getOrigin();
		float curDist;
		float bestDist = -1;							// We can use -1 as +Infinite representation as long as a distance is > 0
		// (Sentinel values are very bad practice but Float objects instead of primitive
		// types are very bad for garbage collecting, therefore performances. As long as
		// this method is invoked multiple times for each bounces of each ray, we afford
		// this disposition for the great of the good)

		for (IInterInfo intersect : intersects) {
			curDist = VectorUtils.computeDistance(intersect.getPoint(), origin);
			if (bestDist == -1 || bestDist > curDist) {
				closestIntersect = intersect;
				bestDist = curDist;
			}
		}
		return closestIntersect;
	}

	@Override
	public IVec3 getNormalAt(IVec3 point) {
		return boundingBox.getNormalAt(point);
	}

	@Override
	public void addContent(IBoxable boxable) {
		contents.add(boxable);
	}

	@Override
	public void removeContent(IBoxable boxable) {
		contents.remove(boxable);
	}

	@Override
	public void split(int maxRec) {
		boundingBox.updateBouningBox(contents);

		if (maxRec == 0) {
			return;
		}

		float xSize = boundingBox.getRightBound() - boundingBox.getLeftBound();
		float ySize = boundingBox.getTopBound() - boundingBox.getBottomBound();
		float zSize = boundingBox.getNearBound() - boundingBox.getFarBound();

		if (zSize > ySize && zSize > xSize) {
			splitAlongZ(zSize);
		} else if (ySize > xSize && ySize > zSize) {
			splitAlongY(ySize);
		} else {
			splitAlongX(xSize);
		}

		if (((BoundingHierarchy)leftBH).contents.size() == 0) {
			leftBH = null;
		} else {
			leftBH.split(maxRec - 1);
		}
		if (((BoundingHierarchy)rightBH).contents.size() == 0) {
			rightBH = null;
		} else {
			rightBH.split(maxRec - 1);
		}
	}

	private void splitAlongX(float size) {
		//Create childs BH
		leftBH = new BoundingHierarchy("");
		((BoundingHierarchy)leftBH).boundingBox = new BoundingBox("", 
				boundingBox.getLeftBound(), boundingBox.getLeftBound() + size / 2f, 
				boundingBox.getTopBound(), boundingBox.getBottomBound(), 
				boundingBox.getNearBound(), boundingBox.getFarBound());
		rightBH = new BoundingHierarchy("");
		((BoundingHierarchy)rightBH).boundingBox = new BoundingBox("", 
				boundingBox.getRightBound() - size / 2, boundingBox.getRightBound(), 
				boundingBox.getTopBound(), boundingBox.getBottomBound(), 
				boundingBox.getNearBound(), boundingBox.getFarBound());

		//Assign primitives
		assignBoxablesToSubScenes();
	}

	private void splitAlongY(float size) {
		//Create childs BH
		leftBH = new BoundingHierarchy("");
		((BoundingHierarchy)leftBH).boundingBox = new BoundingBox("", 
				boundingBox.getLeftBound(), boundingBox.getRightBound(), 
				boundingBox.getTopBound(), boundingBox.getTopBound() - size / 2f, 
				boundingBox.getNearBound(), boundingBox.getFarBound());
		rightBH = new BoundingHierarchy("");
		((BoundingHierarchy)rightBH).boundingBox = new BoundingBox("", 
				boundingBox.getLeftBound(), boundingBox.getRightBound(), 
				boundingBox.getBottomBound() + size / 2f, boundingBox.getBottomBound(), 
				boundingBox.getNearBound(), boundingBox.getFarBound());

		//Assign primitives
		assignBoxablesToSubScenes();
	}

	private void splitAlongZ(float size) {
		//Create childs BH
		leftBH = new BoundingHierarchy("");
		((BoundingHierarchy)leftBH).boundingBox = new BoundingBox("", 
				boundingBox.getLeftBound(), boundingBox.getRightBound(), 
				boundingBox.getTopBound(), boundingBox.getBottomBound(), 
				boundingBox.getNearBound(), boundingBox.getNearBound() - size / 2f);
		rightBH = new BoundingHierarchy("");
		((BoundingHierarchy)rightBH).boundingBox = new BoundingBox("", 
				boundingBox.getLeftBound(), boundingBox.getRightBound(), 
				boundingBox.getTopBound(), boundingBox.getBottomBound(), 
				boundingBox.getFarBound() + size / 2f, boundingBox.getFarBound());

		//Assign primitives
		assignBoxablesToSubScenes();
	}

	private void assignBoxablesToSubScenes() {
		//Init left scene
		for (IBoxable boxable : contents) {
			IBoundingBox primitiveBox = boxable.createBoundingBox();
			if (primitiveBox != null && ((BoundingHierarchy)leftBH).boundingBox.contains(primitiveBox)) {
				leftBH.addContent(boxable);
			}
		}

		//Init left scene
		for (IBoxable boxable : contents) {
			IBoundingBox primitiveBox = boxable.createBoundingBox();
			if (primitiveBox != null && ((BoundingHierarchy)rightBH).boundingBox.contains(primitiveBox)) {
				rightBH.addContent(boxable);
			}
		}
	}

	@Override
	public boolean belongToSurface(IVec3 point) {
		return boundingBox.belongToSurface(point);
	}
}
