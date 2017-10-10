package fr.gamagora.jponzo.rtrace4j.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

import javax.naming.OperationNotSupportedException;

import fr.gamagora.jponzo.rtrace4j.model.impl.BoundingHierarchy;
import fr.gamagora.jponzo.rtrace4j.model.impl.Camera;
import fr.gamagora.jponzo.rtrace4j.model.impl.DiffuseMaterial;
import fr.gamagora.jponzo.rtrace4j.model.impl.FresnelMaterial;
import fr.gamagora.jponzo.rtrace4j.model.impl.InterInfo;
import fr.gamagora.jponzo.rtrace4j.model.impl.Light;
import fr.gamagora.jponzo.rtrace4j.model.impl.Plane;
import fr.gamagora.jponzo.rtrace4j.model.impl.Ray;
import fr.gamagora.jponzo.rtrace4j.model.impl.SpecularMaterial;
import fr.gamagora.jponzo.rtrace4j.model.impl.Sphere;
import fr.gamagora.jponzo.rtrace4j.model.impl.TransparentMaterial;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IBoundingHierarchy;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ICamera;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IInterInfo;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ILight;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IMaterial;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IPlane;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IPrimitive;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IRay;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ISphere;
import fr.gamagora.jponzo.rtrace4j.utils.impl.Vec3;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

/**
 * This class provide a factory for the model classes ensuring a fine encapsulation.
 * Also, it maintains and provide access to the current scene
 * The singleton implementation use static class & methods. It's a bad practice but for now it
 * will do the job. Later, we could use the Eclipse RCP's framework injection features to do that
 * properly
 * @author jponzo
 *
 */
public class ModelService {
	private static final String DEFAULT_SPHERE_NAME_RADICAL = "Sphere";
	private static final String DEFAULT_PLANE_NAME_RADICAL = "Plane";
	private static final String DEFAULT_LIGHT_NAME_RADICAL = "Light";
	private static final String DEFAULT_BHIERA_NAME_RADICAL = "BHiera";
	
	public static IVec3 createVec3 (float x, float y, float z) {
		return new Vec3(x, y, z);
	}
	
	public static ICamera createCamera(int width, int height, float fov, IVec3 position) {
		return new Camera(width, height, fov, position);
	}
	
	public static IRay createRay(IVec3 o, IVec3 d) {
		return new Ray(o, d);
	}
	
	public static ISphere creatSphere(IVec3 c, float r) {
		ISphere sphere = new Sphere("", c, r);
		sphere.setName(DEFAULT_SPHERE_NAME_RADICAL + "_" + System.identityHashCode(sphere));
		return sphere;
	}
	
	public static IPlane createPlane(IVec3 o, IVec3 n) {
		IPlane plane = new Plane("", o, n);
		plane.setName(DEFAULT_PLANE_NAME_RADICAL + "_" + System.identityHashCode(plane));
		return plane;
	}

	public static ILight createLight(IVec3 position, IVec3 intensity, float radius) {
		ILight light = new Light("", position, intensity, radius);
		light.setName(DEFAULT_LIGHT_NAME_RADICAL + "_" + System.identityHashCode(light));
		return light;
	}

	public static IBoundingHierarchy createBHierarchy() {
		IBoundingHierarchy boundingHierarchy = new BoundingHierarchy("");
		boundingHierarchy.setName(DEFAULT_BHIERA_NAME_RADICAL + "_" + System.identityHashCode(boundingHierarchy));
		return boundingHierarchy;
	}
	
	public static IMaterial createDiffuseMaterial() {
		return new DiffuseMaterial();
	}
	
	public static IMaterial createSpecularMaterial() {
		return new SpecularMaterial();
	}
	
	public static IMaterial createTransparentMaterial() {
		return new TransparentMaterial();
	}
	
	public static IMaterial createFresnelMaterial() {
		return new FresnelMaterial();
	}
	
	public static IInterInfo createInterInfo(IPrimitive primitive, IVec3 interPt, float t) throws OperationNotSupportedException {
		return new InterInfo(primitive, interPt, t);
	}
	
	/**
	 * Instantiate a primitive given its type. Java reflection mechanics are massively used here.
	 * Invoke it only if needed because reflection is as powerful as slow
	 * @param type the type of primitive to build
	 * @return an instance of the the given type
	 * @throws OperationNotSupportedException 
	 */
	public static IPrimitive instanciatePrimitive(Class<?> type) throws OperationNotSupportedException {
		Constructor<?> constructor = type.getConstructors()[0];
		Parameter[] dummyParams = constructor.getParameters();
		Object[] dummyValues = new Object[dummyParams.length];
		for (int i = 0; i < dummyParams.length; i++) {
			if (dummyParams[i].getType().equals(IVec3.class)) {
				dummyValues[i] = ModelService.createVec3(0, 0, 0);
			} else if (dummyParams[i].getType().equals(String.class)) {
				dummyValues[i] = type.getSimpleName();
			} else if (dummyParams[i].getType().equals(Double.class)
					|| dummyParams[i].getType().equals(double.class)) {
				dummyValues[i] = 0.0;
			} else if (dummyParams[i].getType().equals(Float.class)
					|| dummyParams[i].getType().equals(float.class)) {
				dummyValues[i] = 0f;
			}
		}
		try {
			IPrimitive primitive = (IPrimitive) constructor.newInstance(dummyValues);
			if (primitive instanceof IPlane) {
				primitive.setName(DEFAULT_PLANE_NAME_RADICAL + "_" + System.identityHashCode(primitive));
			} else if (primitive instanceof ISphere) {
				primitive.setName(DEFAULT_SPHERE_NAME_RADICAL + "_" + System.identityHashCode(primitive));
			} else {
				throw new OperationNotSupportedException();
			}
			return primitive;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
