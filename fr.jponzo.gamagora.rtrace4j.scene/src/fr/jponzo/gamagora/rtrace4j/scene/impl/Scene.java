package fr.jponzo.gamagora.rtrace4j.scene.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import fr.gamagora.jponzo.rtrace4j.collision.CollisionSystem;
import fr.gamagora.jponzo.rtrace4j.model.ModelService;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IBoundingHierarchy;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IBoxable;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ICamera;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IInterInfo;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ILight;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IPrimitive;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IRay;
import fr.gamagora.jponzo.rtrace4j.utils.impl.VectorUtils;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;
import fr.jponzo.gamagora.rtrace4j.scene.interfaces.IScene;

public class Scene implements IScene {
	List<IPrimitive> primitives = new ArrayList<IPrimitive>();
	List<ILight> lights = new ArrayList<ILight>();
	private ICamera camera;

	public Scene() {
		super();
	}

	@Override
	public ICamera getCamera() {
		return camera;
	}

	@Override
	public void init(int maxTreeDepth) {
		CollisionSystem.clear();
		for (IPrimitive primitive : primitives) {
			CollisionSystem.addPrimitive(primitive);
		}
		CollisionSystem.computeTree(maxTreeDepth);
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
	}

		@Override
		public void removePrimitive(IPrimitive primitive) {
			if (!primitives.remove(primitive)) {
				return;
			}
		}

	@Override
	public boolean isVisibleFrom(IVec3 fromPt, IVec3 toPt) throws OperationNotSupportedException {
		IRay lightRay = ModelService.createRay(
				toPt,
				VectorUtils.computeNormalizedDirection(toPt, fromPt));
		//		IInterInfo lightIntersect = this.intersect(lightRay);
		IInterInfo lightIntersect = CollisionSystem.intersect(lightRay);

		if (lightIntersect != null) {
			Float kInter = lightRay.evaluateParameter(fromPt);
			Float kLightIntersect = lightIntersect.getT();
			if (kInter != null && kInter < kLightIntersect) {
				return true;
			}
		}

		return false;
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
}
