package fr.gamagora.jponzo.rtrace4j.utils.impl;

import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

/**
 * Centralization of debug helpers
 * @author JPonzo
 *
 */
public class DebugUtils {
	private static boolean DEBUG = false;
	private static IVec3 BLACK_COLOR = new Vec3(0, 0, 0);
	private static IVec3 WHITE_COLOR = new Vec3(1, 1, 1);
	private static IVec3 RED_COLOR = new Vec3(1, 0, 0);
	private static IVec3 GREEN_COLOR = new Vec3(0, 1, 0);
	private static IVec3 BLUE_COLOR = new Vec3(0, 0, 1);
	
	/**
	 * @return the shadow debug color instead of black on debug mode
	 */
	public static IVec3 getShadowColor() {
		if (DEBUG) {
			return BLUE_COLOR;
		}
		return BLACK_COLOR;
	}
	
	/**
	 * @return the clear debug color instead of black on debug mode
	 */
	public static IVec3 getClearColor() {
		if (DEBUG) {
			return GREEN_COLOR;
		}
		return BLACK_COLOR;
	}
	
	/**
	 * @return a debug color instead of black on debug mode when max ray bouncing
	 * is reached and nothing relevant was found
	 */
	public static IVec3 getMaxRecursiveStagesColor() {
		if (DEBUG) {
			return RED_COLOR;
		}
		return BLACK_COLOR;
	}
}
