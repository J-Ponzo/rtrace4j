
package fr.gamagora.jponzo.rtrace4j.rcp.view;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import fr.gamagora.jponzo.rtrace4j.model.ModelService;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ICamera;
import fr.gamagora.jponzo.rtrace4j.utils.impl.IOUtils;
import fr.jponzo.gamagora.rtrace4j.scene.SceneManager;
import fr.jponzo.gamagora.rtrace4j.scene.interfaces.IScene;

public class ViewerPart {
	public static String ID = "fr.gamagora.jponzo.rtrace4j.rcp.part.viewer";
	
	private Canvas canvas;

	/**
	 * TODO remove hard coded values on setBounds
	 * 
	 * @param parent the parent composite provided by the framework
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		canvas = new Canvas(parent ,SWT.FILL);
		canvas.setBounds(0, 0, 1024, 1024);
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				IScene scene = SceneManager.getScene();
				if (scene == null) return;
				ICamera camera = SceneManager.getScene().getCamera();
				if (camera == null) return;
				
				displayImageOnCanvas(e);
			}
		});
	}
	
	/**
	 * Sweep the image of the camera in order to draw each pixels on the canvas
	 * @param parent
	 * @param e
	 */
	private void displayImageOnCanvas(PaintEvent e) {
		// Create the image to fill the canvas
        Image image = new Image(Display.getCurrent(), canvas.getBounds());

        // Set up the offscreen gc
        GC gcImage = new GC(image);
        ICamera camera = SceneManager.getScene().getCamera();
        for (int i = 0; i < camera.getHeight(); i++) {
			for (int j = 0; j < camera.getWidth(); j++) {
				gcImage.setForeground(new Color(Display.getCurrent(), 
						(int) IOUtils.clamp(0, 255, camera.getImgTable()[camera.getHeight() - i - 1][j][0]), 
						(int) IOUtils.clamp(0, 255, camera.getImgTable()[camera.getHeight() - i - 1][j][1]), 
						(int) IOUtils.clamp(0, 255, camera.getImgTable()[camera.getHeight() - i - 1][j][2])));
				gcImage.drawPoint(j, camera.getHeight() - 1 - i);
			}
		}
        
        // Draw the offscreen buffer to the screen
        e.gc.drawImage(image, 0, 0);

        // Clean up
        image.dispose();
        gcImage.dispose();
	}
	
	public void refresh() {
		canvas.redraw();
	}
}