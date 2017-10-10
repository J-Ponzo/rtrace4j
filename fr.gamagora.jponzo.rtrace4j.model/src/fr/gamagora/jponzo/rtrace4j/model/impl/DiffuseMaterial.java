package fr.gamagora.jponzo.rtrace4j.model.impl;

import fr.gamagora.jponzo.rtrace4j.model.ModelService;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IInterInfo;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IMaterial;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IRay;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ISampleInfo;
import fr.gamagora.jponzo.rtrace4j.utils.impl.MathUtils;
import fr.gamagora.jponzo.rtrace4j.utils.impl.VectorUtils;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public class DiffuseMaterial extends Material implements IMaterial {

	@Override
	public ISampleInfo sample(IRay inRay, IInterInfo inter) {
		IVec3 interPt = inter.getPoint();
		float r1 = (float) Math.random();
		float r2 = (float) Math.random();
		IVec3 randPt = MathUtils.randomizePointOnHemisphere(
				interPt,
				inter.getNormal(), 
				r1 , r2);
		IVec3 dir = VectorUtils.computeDirection(interPt, randPt).normalized();
		IRay outRay = ModelService.createRay(inter.getPoint(), dir);
		
		float pdf = (float) (Math.sqrt(r2) / Math.PI);
		float contribution = (float) (f(dir, inter.getNormal()) / Math.PI) / pdf;
		
		ISampleInfo sampleInfo = new SampleInfo(contribution, outRay);
		return sampleInfo;
	}

	@Override
	public float f(IVec3 inDir, IVec3 normal) {
		return inDir.dot(normal);
	}	
}
