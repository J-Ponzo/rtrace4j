package fr.gamagora.jponzo.rtrace4j.model.impl;

import fr.gamagora.jponzo.rtrace4j.model.ModelService;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IInterInfo;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IMaterial;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IRay;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ISampleInfo;
import fr.gamagora.jponzo.rtrace4j.utils.impl.VectorUtils;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public class FresnelMaterial extends Material implements IMaterial {
	private final float n1 = 1;			//Air refraction
	private final float n2 = 1.1f;		//Water
	
	@Override
	public ISampleInfo sample(IRay inRay, IInterInfo inter) {
		//Compute fresnel factors
		IRay rRay = computeReflectionRay(inRay, inter);
		IRay tRay = computeTransmissionRay(inRay, inter);
		
		IVec3 rDir = rRay.getDirection().normalized();
		IVec3 tDir = tRay.getDirection().normalized();
		IVec3 n = inter.getNormal().normalized();
		
		float teta1 = (float) Math.acos(n.dot(rDir)) / n.norm() * rDir.norm();
		float teta2 = (float) Math.acos(n.dot(tDir)) / n.norm() * tDir.norm();
		float r = (float) ((n1 * Math.cos(teta1) - n2 * Math.cos(teta2)) 
				/ (n1 * Math.cos(teta1) + n2 * Math.cos(teta2)));
		float t = (float) ((2f * n1 * Math.cos(teta1)) 
				/ (n1 * Math.cos(teta1) + n2 * Math.cos(teta2)));
		
		//Normalize Factors
		float d = r + t;
		r /= d;
		t /= d;
		
		//choose direction to cast
		float r1 = (float) Math.random();
		IRay outRay = null;
		float pdf;
		if (r1 < r) {
			outRay = rRay;
			pdf = r;
		} else {
			outRay = tRay;
			pdf = t;
		}

		float contribution = (float) (pdf);
		
		ISampleInfo sampleInfo = new SampleInfo(contribution, outRay);
		return sampleInfo;
	}

	@Override
	public float f(IVec3 inDir, IVec3 normal) {
		return 0;
	}

	private IRay computeReflectionRay(IRay inRay, IInterInfo inter) {
		IVec3 n = inter.getNormal();
		IVec3 displacedIntersect = inter.getPoint().sum(n.mult(VectorUtils.EPS * 1000));
		IVec3 reflectedDir = VectorUtils.computeReflectedRay(inRay.getDirection(), n);
		IRay outRay = ModelService.createRay(displacedIntersect, reflectedDir);
		
		return outRay;
	}
	
	private IRay computeTransmissionRay(IRay inRay, IInterInfo inter) {
		IVec3 displacedIntersect;
		IVec3 intersectPt = inter.getPoint();
		IVec3 n = inter.getNormal();

		//Detect in/out hits
		IVec3 refractedDir;
		if (n.dot(inRay.getDirection()) > 0) {
			refractedDir = VectorUtils.computeRefractedRay(inRay.getDirection().normalized(), n, n1, n2);
			displacedIntersect = intersectPt.sum(n.mult(VectorUtils.EPS * 1000));
		} else {
			refractedDir = VectorUtils.computeRefractedRay(inRay.getDirection().normalized(), n, n2, n1);
			displacedIntersect = intersectPt.sum(n.mult(-VectorUtils.EPS * 1000));
		}
		IRay outRay = ModelService.createRay(displacedIntersect, refractedDir);
		
		return outRay;
	}
}
