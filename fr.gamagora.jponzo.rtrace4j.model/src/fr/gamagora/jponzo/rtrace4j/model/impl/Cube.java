package fr.gamagora.jponzo.rtrace4j.model.impl;

import fr.gamagora.jponzo.rtrace4j.model.interfaces.IBoundingBox;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ICube;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IRay;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public class Cube extends Primitive implements ICube {
	private IVec3 center;
	private float xSize;
	private float ySize;
	private float zSize;
	
	private IBoundingBox bbox;

	public Cube(String name, IVec3 center, float xSize, float ySize, float zSize) {
		super(name);
		this.center = center;
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
		
		float leftBound = center.getX() - xSize / 2f;
		float rightBound = center.getX() + xSize / 2f;
		float topBound = center.getY() + ySize / 2f;
		float bottomBound = center.getY() - ySize / 2f;
		float nearBound = center.getZ() + zSize / 2f;
		float farBound = center.getZ() - zSize / 2f;
		bbox = new BoundingBox(name, leftBound, rightBound, topBound, bottomBound, nearBound, farBound);
	}

	public IVec3 getCenter() {
		return center;
	}

	public void setCenter(IVec3 center) {
		this.center = center;
	}

	public float getxSize() {
		return xSize;
	}

	public void setxSize(float xSize) {
		this.xSize = xSize;
	}

	public float getySize() {
		return ySize;
	}

	public void setySize(float ySize) {
		this.ySize = ySize;
	}

	public float getzSize() {
		return zSize;
	}

	public void setzSize(float zSize) {
		this.zSize = zSize;
	}

	@Override
	public IBoundingBox createBoundingBox() {
		return bbox;
	}

	@Override
	public Float intersectParam(IRay ray) {
		return bbox.intersectParam(ray);
	}

	@Override
	public IVec3 getNormalAt(IVec3 point) {
		IVec3 normal = null;
		if (bbox.getLeftPlane().belongToSurface(point)) {
			normal = bbox.getLeftPlane().getNormal();
		} else if (bbox.getRightPlane().belongToSurface(point)) {
			normal =  bbox.getRightPlane().getNormal();
		} else if (bbox.getTopPlane().belongToSurface(point)) {
			normal =  bbox.getTopPlane().getNormal();
		} else if (bbox.getBottomPlane().belongToSurface(point)) {
			normal =  bbox.getBottomPlane().getNormal();
		} else if (bbox.getNearPlane().belongToSurface(point)) {
			normal =  bbox.getNearPlane().getNormal();
		} else if (bbox.getFarPlane().belongToSurface(point)) {
			normal =  bbox.getFarPlane().getNormal();
		} 
		
		if (normal == null) {
			return null;
		}
		
		return normal;
	}

	@Override
	public boolean belongToSurface(IVec3 point) {
		return bbox.belongToSurface(point);
	}

}
