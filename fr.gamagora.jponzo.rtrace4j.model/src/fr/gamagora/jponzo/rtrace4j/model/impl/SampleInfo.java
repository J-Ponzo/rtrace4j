package fr.gamagora.jponzo.rtrace4j.model.impl;

import fr.gamagora.jponzo.rtrace4j.model.interfaces.IRay;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ISampleInfo;

public class SampleInfo implements ISampleInfo {
	private float contribution;
	private IRay ray;
	
	public SampleInfo(float contribution, IRay ray) {
		super();
		this.contribution = contribution;
		this.ray = ray;
	}
	
	@Override
	public float getContribution() {
		return contribution;
	}
	
	@Override
	public IRay getRay() {
		return ray;
	}
}
