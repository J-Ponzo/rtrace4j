package fr.gamagora.jponzo.rtrace4j.utils.impl;

import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public class Vec3 implements IVec3 {
	private float x;
	private float y;
	private float z;

	public Vec3(float x, float y, float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public float getZ() {
		return z;
	}

	@Override
	public void setZ(float z) {
		this.z = z;
	}

	@Override
	public IVec3 sum(IVec3 v) {long time = System.currentTimeMillis();
		return new Vec3(
				v.getX() + this.x, 
				v.getY() + this.y, 
				v.getZ() + this.z);
	}

	@Override
	public IVec3 sub(IVec3 v) {
		return new Vec3(
				this.x - v.getX(), 
				this.y - v.getY(), 
				this.z - v.getZ());
	}

	@Override
	public IVec3 mult(IVec3 v) {
		return new Vec3(
				this.x * v.getX(), 
				this.y * v.getY(), 
				this.z * v.getZ());
	}
	
	@Override
	public IVec3 mult(float k) {
		return new Vec3(
				this.x * k, 
				this.y * k, 
				this.z * k);
	}

	@Override
	public IVec3 div(IVec3 v) {
		//Check if this vector contains a zero component
		if (Math.abs(x) < VectorUtils.EPS
				|| Math.abs(y) < VectorUtils.EPS
				|| Math.abs(z) < VectorUtils.EPS) {
			return null;
		}
		
		return new Vec3(
				this.x / v.getX(), 
				this.y / v.getY(), 
				this.z / v.getZ());
	}
	
	@Override
	public float norm2() {
		return this.x * this.x
				+ this.y * this.y
				+ this.z * this.z;
	}
	
	@Override
	public float norm() {
		return (float) Math.sqrt(this.norm2());
	}
	
	@Override
	public IVec3 normalized() {
		//Check if this vector is (0, 0, 0)
		float norm2 = this.norm2();
		if (Math.abs(norm2) < VectorUtils.EPS / 10f) {
			return null;
		}
		
		//Do not use norm() in order to save norm2 computing (already performed for null vect testing)
		float norm = (float) Math.sqrt(norm2);
		return new Vec3(
				this.x / norm, 
				this.y / norm, 
				this.z / norm);
	}
	
	@Override
	public float dot(IVec3 v) {
		return this.x * v.getX()
				+ this.y * v.getY()
				+ this.z * v.getZ();
	}
	
	@Override
	public IVec3 cross(IVec3 v) {
		return new Vec3(
				this.y * v.getZ() - this.z * v.getY(), 
				this.z * v.getX() - this.x * v.getZ(), 
				this.x * v.getY() - this.y * v.getX());
	}
	
	@Override
	public boolean equals(IVec3 v) {
		if (Math.abs(this.x - v.getX()) > VectorUtils.EPS
				|| Math.abs(this.y - v.getY()) > VectorUtils.EPS
				|| Math.abs(this.z - v.getZ()) > VectorUtils.EPS) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean equals(IVec3 v, float epsilon) {
		if (Math.abs(this.x - v.getX()) > epsilon
				|| Math.abs(this.y - v.getY()) > epsilon
				|| Math.abs(this.z - v.getZ()) > epsilon) {
			return false;
		}
		return true;
	}
	
	@Override
	public Boolean isCollinearTo(IVec3 v) {
		IVec3 nullVect = new Vec3(0, 0, 0);
		if (this.equals(nullVect) || v.equals(nullVect)) {
			return null;
		}
		
		if (v.cross(this).equals(nullVect)) {
			return true;
		}
		return false;
	}
}
