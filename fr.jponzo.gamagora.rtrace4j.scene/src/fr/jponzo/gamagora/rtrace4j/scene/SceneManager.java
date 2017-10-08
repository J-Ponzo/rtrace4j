package fr.jponzo.gamagora.rtrace4j.scene;

import fr.jponzo.gamagora.rtrace4j.scene.impl.Scene;
import fr.jponzo.gamagora.rtrace4j.scene.interfaces.IScene;

public class SceneManager {
	private static IScene scene;
	
	public static IScene createScene() {
		return new Scene();
	}
	
	public static IScene getScene() {
		return scene;
	}

	public static void setScene(IScene scene) {
		SceneManager.scene = scene;
	}
}
