package fr.gamagora.jponzo.rtrace4j.model.impl;

import fr.gamagora.jponzo.rtrace4j.model.ModelService;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IInterInfo;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IMaterial;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IRay;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ISampleInfo;
import fr.gamagora.jponzo.rtrace4j.utils.impl.VectorUtils;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public class SpecularMaterial extends Material implements IMaterial {

	@Override
	public ISampleInfo sample(IRay inRay, IInterInfo inter) {
		IVec3 n = inter.getNormal();
		IVec3 displacedIntersect = inter.getPoint().sum(n.mult(VectorUtils.EPS * 1000));
		IVec3 reflectedDir = VectorUtils.computeReflectedRay(inRay.getDirection(), n);
		IRay outRay = ModelService.createRay(displacedIntersect, reflectedDir);
		
		ISampleInfo sampleInfo = new SampleInfo(0.7f, outRay);
		return sampleInfo;
	}

	@Override
	public float f(IVec3 inDir, IVec3 normal) {
		return 0f;
	}
}
