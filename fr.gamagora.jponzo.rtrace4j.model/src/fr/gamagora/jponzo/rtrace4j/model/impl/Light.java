package fr.gamagora.jponzo.rtrace4j.model.impl;

import fr.gamagora.jponzo.rtrace4j.model.interfaces.ILight;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public class Light implements ILight {
	private String name;
	private IVec3 position;
	private IVec3 intensity;
	private float radius;
	
	public Light(String name, IVec3 position, IVec3 intensity, float radius) {
		super();
		this.name = name;
		this.position = position;
		this.intensity = intensity;
		this.radius = radius;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
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
	public IVec3 getIntensity() {
		return intensity;
	}

	@Override
	public void setIntensity(IVec3 intensity) {
		this.intensity = intensity;
	}

	@Override
	public float getRadius() {
		return radius;
	}

	@Override
	public void setRadius(float radius) {
		this.radius = radius;
	}
}
