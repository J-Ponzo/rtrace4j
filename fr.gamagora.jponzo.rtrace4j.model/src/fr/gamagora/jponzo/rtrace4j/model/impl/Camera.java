package fr.gamagora.jponzo.rtrace4j.model.impl;

import fr.gamagora.jponzo.rtrace4j.model.interfaces.ICamera;
import fr.gamagora.jponzo.rtrace4j.utils.impl.Vec3;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public class Camera implements ICamera {
	private int width;
	private int height;
	private int depth;
	private float fov;
	private IVec3 position;
	
	private int[][][] imgTable;
	
	public Camera(int width, int height, float fov, IVec3 position) {
		super();
		this.width = width;
		this.height = height;
		this.fov = fov;
		this.position = position;
		this.imgTable= new int[width][height][3];
	}

	@Override
	public float getFov() {
		return fov;
	}

	@Override
	public void setFov(float fov) {
		this.fov = fov;
	}

	@Override
	public IVec3 getPosition() {
		return position;
	}

	@Override
	public void setPosition(IVec3 position) {
		this.position = position;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}
	
	@Override
	public int getDepth() {
		return depth;
	}
	
	@Override
	public int[][][] getImgTable() {
		return imgTable;
	}
}
