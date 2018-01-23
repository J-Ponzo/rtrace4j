package fr.gamagora.jponzo.rtrace4j.model.impl;

import fr.gamagora.jponzo.rtrace4j.model.ModelService;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IInterInfo;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IMaterial;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IRay;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ISampleInfo;
import fr.gamagora.jponzo.rtrace4j.utils.impl.VectorUtils;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public class TransparentMaterial extends Material implements IMaterial {

	@Override
	public ISampleInfo sample(IRay inRay, IInterInfo inter) {
		float n1 = 1.3f;			//Air refraction
		float n2 = 1f;		//Water

		IVec3 displacedIntersect;
		IVec3 intersectPt = inter.getPoint();
		IVec3 n = inter.getNormal();

		//Detect in/out hits
		IVec3 refractedDir;
		if (n.dot(inRay.getDirection()) < 0) {
			refractedDir = VectorUtils.computeRefractedRay(inRay.getDirection().normalized(), n, n1, n2);
			displacedIntersect = intersectPt.sum(n.mult(-VectorUtils.EPS * 1000));
		} else {
			refractedDir = VectorUtils.computeRefractedRay(inRay.getDirection().normalized(), n, n2, n1);
			displacedIntersect = intersectPt.sum(n.mult(VectorUtils.EPS * 1000));
		}
		IRay outRay = ModelService.createRay(displacedIntersect, refractedDir);
		
		ISampleInfo sampleInfo = new SampleInfo(0.9f, outRay);
		return sampleInfo;
	}

	@Override
	public float f(IVec3 inDir, IVec3 normal) {
		return 0f;
	}

}
