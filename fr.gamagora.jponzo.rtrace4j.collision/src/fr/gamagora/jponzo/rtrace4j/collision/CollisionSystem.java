package fr.gamagora.jponzo.rtrace4j.collision;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import fr.gamagora.jponzo.rtrace4j.model.ModelService;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IBoundingHierarchy;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IBoxable;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IInterInfo;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IPrimitive;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IRay;
import fr.gamagora.jponzo.rtrace4j.utils.impl.VectorUtils;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public class CollisionSystem {
	private static List<IBoxable> boxables = new ArrayList<IBoxable>();
	private static List<IPrimitive> nonBoxable = new ArrayList<IPrimitive>();
	private static IBoundingHierarchy biTree;

	public static List<IInterInfo> intersectRayPrimitives(IRay ray, List<? extends IPrimitive> primitives) throws OperationNotSupportedException {
		List<IInterInfo> intersects = new ArrayList<IInterInfo>();

		for (IPrimitive primitive : primitives) {
			Float t = primitive.intersectParam(ray);
			if (t == null) continue;
			IVec3 intersect = ray.evaluatePosition(t);
			if (intersect != null) {
				intersects.add(ModelService.createInterInfo(primitive, intersect, t));
			}
		}

		return intersects;
	}

	public static IInterInfo intersectRayBHierarchy(IRay ray, IBoundingHierarchy boundingHierarchy) throws OperationNotSupportedException {
		IInterInfo boxableInter = boundingHierarchy.intersect(ray);
		return boxableInter;
	}

	public static IInterInfo intersectRayPrimitivesClosest(IRay ray, List<? extends IPrimitive> primitives) throws OperationNotSupportedException {
		List<IInterInfo> intersects = intersectRayPrimitives(ray, primitives);

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

	public static void addPrimitive(IPrimitive primitive) {		
		if (primitive instanceof IBoxable) {
			boxables.add((IBoxable) primitive);
		} else {
			nonBoxable.add(primitive);
		}
	}

	public static void clear() {
		boxables.clear();
		nonBoxable.clear();
		biTree = null;
	}

	public static IInterInfo intersect(IRay ray) throws OperationNotSupportedException {
		IInterInfo nonBoxableInter = CollisionSystem.intersectRayPrimitivesClosest(ray, nonBoxable);
		IInterInfo boxableInter = CollisionSystem.intersectRayBHierarchy(ray, biTree);

		if (boxableInter == null && nonBoxableInter == null) {
			return null;
		} else if (boxableInter == null) {
			return nonBoxableInter;

		} else if (nonBoxableInter == null) {
			return boxableInter;
		} else if (boxableInter.getT() < nonBoxableInter.getT()) {
			return boxableInter;
		} else {
			return nonBoxableInter;
		}
	}

	public static void computeTree(int maxTreeDepth) {
		biTree = ModelService.createBHierarchy();
		for (IBoxable boxable : boxables) {
			biTree.addContent(boxable);
		}
		biTree.split(maxTreeDepth);
	}
}
