package fr.gamagora.jponzo.rtrace4j.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import fr.gamagora.jponzo.rtrace4j.model.interfaces.IBoundingBox;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IBoxable;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IEntity;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IInterInfo;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IPlane;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IPrimitive;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IRay;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ISphere;
import fr.gamagora.jponzo.rtrace4j.utils.impl.Vec3;
import fr.gamagora.jponzo.rtrace4j.utils.impl.VectorUtils;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public class BoundingBox extends Entity implements IBoundingBox {	
	private static final String LEFT_PLANE_NAME = "Left_Face";
	private static final String RIGHT_PLANE_NAME = "Right_Face";
	private static final String TOP_PLANE_NAME = "Top_Face";
	private static final String BOTTOM_PLANE_NAME = "Bottom_Face";
	private static final String NEAR_PLANE_NAME = "Near_Face";
	private static final String FAR_PLANE_NAME = "Far_Face";

	private float leftBound;
	private float rightBound;
	private float topBound;
	private float bottomBound;
	private float nearBound;
	private float farBound;

	private IPlane leftPlane;
	private IPlane rightPlane;
	private IPlane topPlane;
	private IPlane bottomPlane;
	private IPlane nearPlane;
	private IPlane farPlane;

	public BoundingBox(String name, float leftBound, float rightBound, float topBound, float bottomBound, float nearBound,
			float farBound) {
		super(name);
		initFromBorders(leftBound, rightBound, topBound, bottomBound, nearBound, farBound);

	}

	private void initFromBorders(float leftBound, float rightBound, float topBound, float bottomBound, float nearBound,
			float farBound) {
		this.leftBound = leftBound;
		this.rightBound = rightBound;
		this.topBound = topBound;
		this.bottomBound = bottomBound;
		this.nearBound = nearBound;
		this.farBound = farBound;


		IVec3 origin = new Vec3(leftBound, 0, 0);
		IVec3 normal = new Vec3(-1, 0, 0);
		leftPlane = new Plane(this.getName() + "_" + LEFT_PLANE_NAME, origin, normal);
		origin = new Vec3(rightBound, 0, 0);
		normal = new Vec3(1, 0, 0);
		rightPlane = new Plane(this.getName() + "_" + RIGHT_PLANE_NAME, origin, normal);
		origin = new Vec3(0, topBound, 0);
		normal = new Vec3(0, 1, 0);
		topPlane = new Plane(this.getName() + "_" + TOP_PLANE_NAME, origin, normal);
		origin = new Vec3(0, bottomBound, 0);
		normal = new Vec3(0, -1, 0);
		bottomPlane = new Plane(this.getName() + "_" + BOTTOM_PLANE_NAME, origin, normal);
		origin = new Vec3(0, 0, nearBound);
		normal = new Vec3(0, 0, 1);
		nearPlane = new Plane(this.getName() + "_" + NEAR_PLANE_NAME, origin, normal);
		origin = new Vec3(0, 0, farBound);
		normal = new Vec3(0, 0, -1);
		farPlane = new Plane(this.getName() + "_" + FAR_PLANE_NAME, origin, normal);
	}

	@Override
	public float getLeftBound() {
		return leftBound;
	}

	@Override
	public float getRightBound() {
		return rightBound;
	}

	@Override
	public float getTopBound() {
		return topBound;
	}

	@Override
	public float getBottomBound() {
		return bottomBound;
	}

	@Override
	public float getNearBound() {
		return nearBound;
	}

	@Override
	public float getFarBound() {
		return farBound;
	}

	@Override
	public Float intersectParam(IRay ray) {
		//Get intersect params
		Float leftParam = leftPlane.intersectParam(ray);
		Float rightParam = rightPlane.intersectParam(ray);
		Float topParam = topPlane.intersectParam(ray);
		Float bottomParam = bottomPlane.intersectParam(ray);
		Float nearParam = nearPlane.intersectParam(ray);
		Float farParam = farPlane.intersectParam(ray);

		//Compute intersection on each planes composing the box
		IVec3 leftInter = null;
		IVec3 rightInter = null;
		IVec3 topInter = null;
		IVec3 bottomInter = null;
		IVec3 nearInter = null;
		IVec3 farInter = null;
		if (leftParam != null) leftInter = ray.evaluatePosition(leftParam);
		if (rightParam != null) rightInter = ray.evaluatePosition(rightParam);
		if (topParam != null) topInter = ray.evaluatePosition(topParam);
		if (bottomParam != null) bottomInter = ray.evaluatePosition(bottomParam);
		if (nearParam != null) nearInter = ray.evaluatePosition(nearParam);
		if (farParam != null) farInter = ray.evaluatePosition(farParam);

		//remove intersections outside the squares composing the box
		List<IInterInfo> inters = new ArrayList<IInterInfo>();
		//LEFT/RIGHT ie X axis
		try {
			if (leftInter != null 
					&& !(leftInter.getZ() < farBound || leftInter.getZ() > nearBound
							|| leftInter.getY() > topBound || leftInter.getY() < bottomBound)) {
				IInterInfo interInfo = new InterInfo(leftPlane, leftInter, leftParam);
				inters.add(interInfo);
			}
			if (rightInter != null 
					&& !(rightInter.getZ() < farBound || rightInter.getZ() > nearBound
							|| rightInter.getY() > topBound || rightInter.getY() < bottomBound)) {
				IInterInfo interInfo = new InterInfo(rightPlane, rightInter, rightParam);
				inters.add(interInfo);
			}
			//TOP/DOWN ie Y axis
			if (topInter != null 
					&& !(topInter.getZ() < farBound || topInter.getZ() > nearBound
							|| topInter.getX() < leftBound || topInter.getX() > rightBound)) {
				IInterInfo interInfo = new InterInfo(topPlane, topInter, topParam);
				inters.add(interInfo);
			}
			if (bottomInter != null 
					&& !(bottomInter.getZ() < farBound || bottomInter.getZ() > nearBound
							|| bottomInter.getX() < leftBound || bottomInter.getX() > rightBound)) {
				IInterInfo interInfo = new InterInfo(bottomPlane, bottomInter, bottomParam);
				inters.add(interInfo);
			}
			//NEAR/FAR ie Z axis
			if (nearInter != null 
					&& !(nearInter.getY() > topBound || nearInter.getY() < bottomBound
							|| nearInter.getX() < leftBound || nearInter.getX() > rightBound)) {
				IInterInfo interInfo = new InterInfo(nearPlane, nearInter, nearParam);
				inters.add(interInfo);
			}
			if (farInter != null 
					&& !(farInter.getY() > topBound || farInter.getY() < bottomBound
							|| farInter.getX() < leftBound || farInter.getX() > rightBound)) {
				IInterInfo interInfo = new InterInfo(farPlane, farInter, farParam);
				inters.add(interInfo);
			}
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		}

		if (inters.size() == 0) {
			return null;
		}

		//Sort remaining intersections
		Collections.sort(inters, new Comparator<IInterInfo>() {

			@Override
			public int compare(IInterInfo o1, IInterInfo o2) {
				if (o1.getT() > o2.getT()) {
					return 1;
				} else if (o1.getT() < o2.getT()) {
					return -1;
				}
				return 0;
			}
		});

		return inters.get(0).getT();
	}

	@Override
	public boolean contains(IBoundingBox box) {
		if (box.getLeftBound() > this.rightBound || box.getRightBound() < this.leftBound
				|| box.getBottomBound() > this.topBound || box.getTopBound() < this.bottomBound
				|| box.getFarBound() > this.nearBound || box.getNearBound() < this.farBound) {
			return false;
		}

		return true;
	}

	@Override
	public void updateBouningBox(List<IBoxable> contents) {
		//If no sphere set point box
		if (contents.size() == 0) {
			this.initFromBorders(0, 0, 0, 0, 0, 0);
			return;
		}

		//Look for max bounds
		IBoxable boxable = contents.get(0);
		IBoundingBox boundingBox = boxable.createBoundingBox();
		float left = boundingBox.getLeftBound();
		float right = boundingBox.getRightBound();
		float top = boundingBox.getTopBound();
		float bottom = boundingBox.getBottomBound();
		float near = boundingBox.getNearBound();
		float far = boundingBox.getFarBound();
		for (int i = 1; i < contents.size(); i++) {
			boxable = contents.get(i);
			boundingBox = boxable.createBoundingBox();
			if (left > boundingBox.getLeftBound()) {
				left = boundingBox.getLeftBound();
			}
			if (right < boundingBox.getRightBound()) {
				right = boundingBox.getRightBound();
			}
			if (top < boundingBox.getTopBound()) {
				top = boundingBox.getTopBound();
			}
			if (bottom > boundingBox.getBottomBound()) {
				bottom = boundingBox.getBottomBound();
			}
			if (near < boundingBox.getNearBound()) {
				near = boundingBox.getNearBound();
			}
			if (far > boundingBox.getFarBound()) {
				far = boundingBox.getFarBound();
			}
		}

		//Set max bounds
		initFromBorders(left, right, top, bottom, near, far);
	}

	@Override
	public IVec3 getNormalAt(IVec3 point) {
		if (!belongToSurface(point)) {
			return null;
		}
		return null;
	}

	@Override
	public boolean belongToSurface(IVec3 point) {
		//Left face
		if ((leftPlane.belongToSurface(point) || rightPlane.belongToSurface(point))
				&& point.getY() < topBound && point.getY() > bottomBound
				&& point.getZ() < nearBound && point.getZ() > farBound) {
				return true;
		} else if ((topPlane.belongToSurface(point) || bottomPlane.belongToSurface(point))
				&& point.getX() < rightBound && point.getX() > leftBound
				&& point.getZ() < nearBound && point.getZ() > farBound) {
				return true;
		} else if ((nearPlane.belongToSurface(point) || farPlane.belongToSurface(point))
				&& point.getY() < rightBound && point.getY() > leftBound
				&& point.getX() < rightBound && point.getX() > leftBound) {
				return true;
		}
		
		return false;
	}
}
