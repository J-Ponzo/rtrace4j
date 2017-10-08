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

public class LoadSceneDialog extends Dialog {
	private Text textMaxTreeDepth;

	public LoadSceneDialog(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(2, false));
		
		Label lblMaxTreeDepth = new Label(container, SWT.NONE);
		lblMaxTreeDepth.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMaxTreeDepth.setText("Max Tree Depth");
		
		textMaxTreeDepth = new Text(container, SWT.BORDER);
		textMaxTreeDepth.setText("" + LoadingSystem.maxTreeDepth);
		textMaxTreeDepth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		return container;
	}
	
	@Override
	protected void okPressed() {
		LoadingSystem.maxTreeDepth = Integer.parseInt(textMaxTreeDepth.getText());
		super.okPressed();
	}
}
