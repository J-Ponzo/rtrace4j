
package fr.gamagora.jponzo.rtrace4j.rcp.view;

import javax.annotation.PostConstruct;
import javax.naming.OperationNotSupportedException;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import fr.gamagora.jponzo.rtrace4j.dialog.LoadSceneDialog;
import fr.gamagora.jponzo.rtrace4j.dialog.RenderSceneDialog;
import fr.gamagora.jponzo.rtrace4j.loading.LoadingSystem;
import fr.gamagora.jponzo.rtrace4j.model.ModelService;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ICamera;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.ILight;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IPrimitive;
import fr.gamagora.jponzo.rtrace4j.model.interfaces.IScene;
import fr.gamagora.jponzo.rtrace4j.rendering.RenderingSystem;
import fr.gamagora.jponzo.rtrace4j.utils.interfaces.IVec3;

public class EditorPart {
	public static String ID = "fr.gamagora.jponzo.rtrace4j.rcp.part.editor";

	public EPartService partService;

	//UI vars
	DataBindingContext bindingContext = new DataBindingContext();
	private Composite parent;
	private Text textFov;
	private Text textWidth;
	private Text textHeight;
	private Text textX;
	private Text textY;
	private Text textZ;
	private Group grpCamera;
	private Group grpLights;
	private Group grpSpheres;
	private Combo typeCombo;

	private IScene boundScene;
	private Class<?> typeToCreate = null;

	/**
	 * TODO find better way to pass index to callback
	 */
	private int tmpIndex;

	public IScene getBoundScene() {
		return boundScene;
	}

	public void bindScene(IScene scene) {
		this.boundScene = scene;

		grpLights.dispose();
		grpLights = new Group(parent, SWT.NONE);
		GridLayout gl_grpLights = new GridLayout(3, true);
		gl_grpLights.horizontalSpacing = 3;
		grpLights.setLayout(gl_grpLights);
		grpLights.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpLights.setText("Lights");
		createLightsControls(grpLights);

		grpSpheres.dispose();
		grpSpheres = new Group(parent, SWT.NONE);
		GridLayout gl_grpSphere = new GridLayout(3, true);
		gl_grpSphere.horizontalSpacing = 3;
		grpSpheres.setLayout(gl_grpSphere);
		grpSpheres.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpSpheres.setText("Spheres");
		createPrimitivesControl(grpSpheres);

		parent.layout();
		initDataBindings();
	}

	@PostConstruct
	public void createControls(Composite parent, EPartService partService) {	
		this.partService = partService;
		this.parent = parent;
		parent.setLayout(new GridLayout(1, false));

		Button loadBtn = new Button(parent, SWT.PUSH);
		loadBtn.setText("Load");
		loadBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				load();
			}
		});
		loadBtn.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 3, 1));

		Button renderBtn = new Button(parent, SWT.PUSH);
		renderBtn.setText("Render");
		renderBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				render();
			}
		});
		renderBtn.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 3, 1));

		grpCamera = new Group(parent, SWT.NONE);
		grpCamera.setLayout(new GridLayout(6, false));
		grpCamera.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpCamera.setText("Camera");
		createCameraControls(grpCamera);

		grpLights = new Group(parent, SWT.NONE);
		GridLayout gl_grpLights = new GridLayout(3, true);
		gl_grpLights.horizontalSpacing = 3;
		grpLights.setLayout(gl_grpLights);
		grpLights.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpLights.setText("Lights");
		createLightsControls(grpLights);

		grpSpheres = new Group(parent, SWT.NONE);
		grpSpheres.setLayout(new GridLayout(3, true));
		grpSpheres.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpSpheres.setText("Spheres");
		createPrimitivesControl(grpSpheres);

		bindingContext = initDataBindings();
	}

	private void createPrimitivesControl(Composite grpSpheres) {
		if (boundScene != null) {
			for (tmpIndex = 0; tmpIndex < boundScene.getPrimitives().size(); tmpIndex++) {
				Label lblSphere = new Label(grpSpheres, SWT.NONE);
				lblSphere.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
				lblSphere.setText(boundScene.getPrimitives().get(tmpIndex).getName());

				Button btnEdit = new Button(grpSpheres, SWT.NONE);
				btnEdit.addSelectionListener(new SelectionAdapter() {
					private int index = tmpIndex;

					@Override
					public void widgetSelected(SelectionEvent e) {
						IPrimitive primitive = boundScene.getPrimitives().get(index);
						InspectorPart inspectorPart = (InspectorPart) partService.findPart(InspectorPart.ID).getObject();
						inspectorPart.bindObject(primitive);

						//TODO no need to do all of that
						bindScene(boundScene);
					}
				});
				btnEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
				btnEdit.setText("Edit");

				Button btnRemove = new Button(grpSpheres, SWT.NONE);
				btnRemove.addSelectionListener(new SelectionAdapter() {
					private int index = tmpIndex;

					@Override
					public void widgetSelected(SelectionEvent e) {
						IPrimitive primitive = boundScene.getPrimitives().get(index);
						boundScene.removePrimitive(primitive);

						//TODO no need to do all of that
						bindScene(boundScene);
					}
				});
				btnRemove.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
				btnRemove.setText("Remove");
			}
		}

		typeCombo = new Combo(grpSpheres, SWT.READ_ONLY);
		typeCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		Class<?>[] primitiveTypes = getAllPrimitiveTypes();
		String items[] = new String[primitiveTypes.length];
		for (int i = 0; i < primitiveTypes.length; i++) {
			items[i] = primitiveTypes[i].getSimpleName();
		}
		typeCombo.setItems(items);
		typeCombo.select(0);
		typeToCreate = getAllPrimitiveTypes()[0];
		typeCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Class<?> type = primitiveTypes[typeCombo.getSelectionIndex()];
				typeToCreate = type;
			}
		});

		Button btnAdd = new Button(grpSpheres, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				try {
					IPrimitive primitive = ModelService.instanciatePrimitive(typeToCreate);
					boundScene.addPrimitive(primitive);
				} catch (OperationNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				//TODO no need to do all of that
				bindScene(boundScene);
			}
		});
		btnAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnAdd.setText("Add");
	}

	private void createLightsControls(Composite grpLights) {
		if (boundScene != null) {
			for (tmpIndex = 0; tmpIndex < boundScene.getLights().size(); tmpIndex++) {
				Label lblLight = new Label(grpLights, SWT.NONE);
				lblLight.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
				lblLight.setText(boundScene.getLights().get(tmpIndex).getName());

				Button btnEdit = new Button(grpLights, SWT.NONE);
				btnEdit.addSelectionListener(new SelectionAdapter() {
					private int index = tmpIndex;

					@Override
					public void widgetSelected(SelectionEvent e) {
						ILight light = boundScene.getLights().get(index);
						InspectorPart inspectorPart = (InspectorPart) partService.findPart(InspectorPart.ID).getObject();
						inspectorPart.bindObject(light);

						//TODO no need to do all of that
						bindScene(boundScene);
					}
				});
				btnEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
				btnEdit.setText("Edit");

				Button btnRemove = new Button(grpLights, SWT.NONE);
				btnRemove.addSelectionListener(new SelectionAdapter() {
					private int index = tmpIndex;

					@Override
					public void widgetSelected(SelectionEvent e) {
						boundScene.getLights().remove(index);

						//TODO no need to do all of that
						bindScene(boundScene);
					}
				});
				btnRemove.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
				btnRemove.setText("Remove");
			}
		}

		Button btnAdd = new Button(grpLights, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				IVec3 position = ModelService.createVec3(0, 0, 0);
				IVec3 intensity = ModelService.createVec3(0, 0, 0);
				ILight light = ModelService.createLight(position, intensity, 0);
				boundScene.addLight(light);

				//TODO no need to do all of that
				bindScene(boundScene);
			}
		});
		btnAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		btnAdd.setText("Add");
		new Label(grpLights, SWT.NONE);
		new Label(grpLights, SWT.NONE);
		new Label(grpLights, SWT.NONE);
	}

	private void createCameraControls(Composite grpCamera) {
		Label lblFov = new Label(grpCamera, SWT.NONE);
		lblFov.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFov.setText("fov");

		textFov = new Text(grpCamera, SWT.BORDER);
		textFov.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblWidth = new Label(grpCamera, SWT.NONE);
		lblWidth.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblWidth.setText("width");

		textWidth = new Text(grpCamera, SWT.BORDER);
		textWidth.setEditable(false);
		textWidth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblHeight = new Label(grpCamera, SWT.NONE);
		lblHeight.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblHeight.setText("height");

		textHeight = new Text(grpCamera, SWT.BORDER);
		textHeight.setEditable(false);
		textHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblX = new Label(grpCamera, SWT.NONE);
		lblX.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblX.setText("X");

		textX = new Text(grpCamera, SWT.BORDER);
		textX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblY = new Label(grpCamera, SWT.NONE);
		lblY.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblY.setText("Y");

		textY = new Text(grpCamera, SWT.BORDER);
		textY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblZ = new Label(grpCamera, SWT.NONE);
		lblZ.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblZ.setText("Z");

		textZ = new Text(grpCamera, SWT.BORDER);
		textZ.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}

	/**
	 * Trigger the rendering step
	 */
	private void render() {
		RenderSceneDialog dialog = new RenderSceneDialog(Display.getCurrent().getActiveShell());
		if (dialog.open() == Dialog.OK) {
			ICamera camera = boundScene.getCamera();
			int[][][] imgTable = camera.getImgTable();
			for (int i = 0; i < camera.getHeight(); i++) {
				for (int j = 0; j < camera.getHeight(); j++) {
					imgTable[i][j][0] = 0;
					imgTable[i][j][1] = 0;
					imgTable[i][j][2] = 0;
				}
			}
			RenderingSystem.parallLaunchRays();
		}
		//Refresh Canvas
		ViewerPart viewerPart = (ViewerPart) partService.findPart(ViewerPart.ID).getObject();
		viewerPart.refresh();
	}

	/**
	 * Trigger the load step (stub scene is loaded)
	 */
	private void load() {
		LoadSceneDialog dialog = new LoadSceneDialog(Display.getCurrent().getActiveShell());
		if (dialog.open() == Dialog.OK) {
			IScene loadedScene = LoadingSystem.loadStubScene();
			loadedScene.init(LoadingSystem.maxTreeDepth);
			ModelService.setScene(loadedScene);
			bindScene(ModelService.getScene());
		}
	}

	private Class<?>[] getAllPrimitiveTypes() {
		Class<?>[] primitiveTypes = new Class<?>[2];
		primitiveTypes[0] = ModelService.creatSphere(null, 0).getClass();
		primitiveTypes[1] = ModelService.createPlane(null, null).getClass();
		return primitiveTypes;
	}

	protected DataBindingContext initDataBindings() {
		bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextTextFovObserveWidget = WidgetProperties.text(SWT.Modify).observe(textFov);
		IObservableValue camerafovBoundSceneObserveValue = PojoProperties.value("camera.fov").observe(boundScene);
		bindingContext.bindValue(observeTextTextFovObserveWidget, camerafovBoundSceneObserveValue, null, null);
		//
		IObservableValue observeTextTextWidthObserveWidget = WidgetProperties.text(SWT.Modify).observe(textWidth);
		IObservableValue camerawidthBoundSceneObserveValue = PojoProperties.value("camera.width").observe(boundScene);
		bindingContext.bindValue(observeTextTextWidthObserveWidget, camerawidthBoundSceneObserveValue, null, null);
		//
		IObservableValue observeTextTextHeightObserveWidget = WidgetProperties.text(SWT.Modify).observe(textHeight);
		IObservableValue cameraheightBoundSceneObserveValue = PojoProperties.value("camera.height").observe(boundScene);
		bindingContext.bindValue(observeTextTextHeightObserveWidget, cameraheightBoundSceneObserveValue, null, null);
		//
		IObservableValue observeTextTextXObserveWidget = WidgetProperties.text(SWT.Modify).observe(textX);
		IObservableValue camerapositionxBoundSceneObserveValue = PojoProperties.value("camera.position.x").observe(boundScene);
		bindingContext.bindValue(observeTextTextXObserveWidget, camerapositionxBoundSceneObserveValue, null, null);
		//
		IObservableValue observeTextTextYObserveWidget = WidgetProperties.text(SWT.Modify).observe(textY);
		IObservableValue camerapositionyBoundSceneObserveValue = PojoProperties.value("camera.position.y").observe(boundScene);
		bindingContext.bindValue(observeTextTextYObserveWidget, camerapositionyBoundSceneObserveValue, null, null);
		//
		IObservableValue observeTextTextZObserveWidget = WidgetProperties.text(SWT.Modify).observe(textZ);
		IObservableValue camerapositionzBoundSceneObserveValue = PojoProperties.value("camera.position.z").observe(boundScene);
		bindingContext.bindValue(observeTextTextZObserveWidget, camerapositionzBoundSceneObserveValue, null, null);
		//
		return bindingContext;
	}
}