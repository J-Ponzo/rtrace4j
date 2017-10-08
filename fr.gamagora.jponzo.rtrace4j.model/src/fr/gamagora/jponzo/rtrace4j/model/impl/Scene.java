package fr.gamagora.jponzo.rtrace4j.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import fr.gamagora.jponzo.rtrace4j.model.interfaces.IBoundingBox;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IBoundingHierarchy;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IBoxable;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ICamera;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IInterInfo;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ILight;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IPrimitive;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IRay;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IScene;
import fr.gamagora.jponzo.rtrace4j.utils.impl.VectorUtils;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public class Scene implements IScene {
	List<IPrimitive> primitives = new ArrayList<IPrimitive>();
	List<IBoxable> boxables = new ArrayList<IBoxable>();
	List<IPrimitive> nonBoxable = new ArrayList<IPrimitive>();
	
	List<ILight> lights = new ArrayList<ILight>();
	private ICamera camera;
	private IBoundingHierarchy biTree;

	public Scene() {
		super();
	}

	@Override
	public ICamera getCamera() {
		return camera;
	}

	@Override
	public void init(int maxTreeDepth) {
		biTree = new BoundingHierarchy("");
		for (IBoxable boxable : boxables) {
			biTree.addContent(boxable);
		}
		biTree.split(maxTreeDepth);
	}

	@Override
	public void setCamera(ICamera camera) {
		this.camera = camera;
	}

	@Override
	public List<IPrimitive> getPrimitives() {
		return Collections.unmodifiableList(primitives);
	}

	@Override
	public void addPrimitive(IPrimitive primitive) {
		if (primitives.contains(primitive)) {
			return;
		}
		primitives.add(primitive);
		
		if (primitive instanceof IBoxable) {
			boxables.add((IBoxable) primitive);
		} else {
			nonBoxable.add(primitive);
		}
	}
	
	@Override
	public void removePrimitive(IPrimitive primitive) {
		if (!primitives.remove(primitive)) {
			return;
		}
		
		if (primitive instanceof IBoxable) {
			boxables.remove(primitive);
		} else {
			nonBoxable.remove(primitive);
		}
	}

	@Override
	public List<ILight> getLights() {
		return lights;
	}

	@Override
	public void addLight(ILight light) {
		if (lights.contains(light)) {
			return;
		}
		lights.add(light);
	}
	
	private List<IInterInfo> intersectNonBoxables(IRay ray) throws OperationNotSupportedException {
		List<IInterInfo> intersects = new ArrayList<IInterInfo>();

		for (IPrimitive primitive : nonBoxable) {
			Float t = primitive.intersectParam(ray);
			if (t == null) continue;
			IVec3 intersect = ray.evaluatePosition(t);
			if (intersect != null) {
				intersects.add(new InterInfo(primitive, intersect, t));
			}
		}

		return intersects;
	}

	@Override
	public IInterInfo findClosestIntersect(IRay ray) throws OperationNotSupportedException {
		IInterInfo nonBoxableInter = findClosestNonBoxableIntersect(ray);
		IInterInfo boxableInter = biTree.intersect(ray);
		
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

	private IInterInfo findClosestNonBoxableIntersect(IRay ray) throws OperationNotSupportedException {
		List<IInterInfo> intersects = this.intersectNonBoxables(ray);

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
	public boolean isVisibleFrom(IVec3 fromPt, IVec3 toPt) throws OperationNotSupportedException {
		IRay lightRay = new Ray(
				toPt,
				VectorUtils.computeNormalizedDirection(toPt, fromPt));
		IInterInfo lightIntersect = this.findClosestIntersect(lightRay);

		if (lightIntersect != null) {
			Float kInter = lightRay.evaluateParameter(fromPt);
			Float kLightIntersect = lightIntersect.getT();
			if (kInter != null && kInter < kLightIntersect) {
				return true;
			}
		}

		return false;
	}
}
