package fr.gamagora.jponzo.rtrace4j.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import fr.gamagora.jponzo.rtrace4j.loading.LoadingSystem;
import fr.gamagora.jponzo.rtrace4j.rendering.RenderingSystem;

public class RenderSceneDialog extends Dialog {
	private Text textNbThreads;
	private Text textShadowSampling;
	private Text textMaxLightConsidered;
	private Text textIndiretLightSampling;
	private Text textRayPerPixel;
	private Text textMaxBounces;

	public RenderSceneDialog(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(2, false));
		
		Label lblNbThreads = new Label(container, SWT.NONE);
		lblNbThreads.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNbThreads.setText("Nb. Threads");
		
		textNbThreads = new Text(container, SWT.BORDER);
		textNbThreads.setText("" + RenderingSystem.nbThreads);
		textNbThreads.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblShadowSampling = new Label(container, SWT.NONE);
		lblShadowSampling.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblShadowSampling.setText("Shadow Sampling");
		
		textShadowSampling = new Text(container, SWT.BORDER);
		textShadowSampling.setText("" + RenderingSystem.smoothShadowSampling);
		textShadowSampling.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblMaxLightConsidered = new Label(container, SWT.NONE);
		lblMaxLightConsidered.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMaxLightConsidered.setText("Direct Light Sampling");
		
		textMaxLightConsidered = new Text(container, SWT.BORDER);
		textMaxLightConsidered.setText("" + RenderingSystem.maxLightConsidered);
		textMaxLightConsidered.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblIndiretLightSampling = new Label(container, SWT.NONE);
		lblIndiretLightSampling.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblIndiretLightSampling.setText("Indirect Light Sampling");
		
		textIndiretLightSampling = new Text(container, SWT.BORDER);
		textIndiretLightSampling.setText("" + RenderingSystem.indirectLightSampling);
		textIndiretLightSampling.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblRayPerPixel = new Label(container, SWT.NONE);
		lblRayPerPixel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblRayPerPixel.setText("Nb. Rays Per Pixel");
		
		textRayPerPixel = new Text(container, SWT.BORDER);
		textRayPerPixel.setText("" + RenderingSystem.rayPerPixel);
		textRayPerPixel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblMaxBounces = new Label(container, SWT.NONE);
		lblMaxBounces.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMaxBounces.setText("Max. Ray Bounces");
		
		textMaxBounces = new Text(container, SWT.BORDER);
		textMaxBounces.setText("" + RenderingSystem.maxRayBounces);
		textMaxBounces.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		return container;
	}
	
	@Override
	protected void okPressed() {
		RenderingSystem.nbThreads = Integer.parseInt(textNbThreads.getText());
		RenderingSystem.smoothShadowSampling = Integer.parseInt(textShadowSampling.getText());
		RenderingSystem.maxLightConsidered = Integer.parseInt(textMaxLightConsidered.getText());
		RenderingSystem.indirectLightSampling = Integer.parseInt(textIndiretLightSampling.getText());
		RenderingSystem.rayPerPixel = Integer.parseInt(textRayPerPixel.getText());
		RenderingSystem.maxRayBounces = Integer.parseInt(textMaxBounces.getText());
		super.okPressed();
	}
}
