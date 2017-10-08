
package fr.gamagora.jponzo.rtrace4j.rcp.view;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import fr.gamagora.jponzo.rtrace4j.model.interfaces.IScene;
import fr.gamagora.jponzo.rtrace4j.utils.impl.ReflectionUtils;

public class InspectorPart {
	private DataBindingContext m_bindingContext;
	public static String ID = "fr.gamagora.jponzo.rtrace4j.rcp.part.inspector";

	private EPartService partService;

	private Object boundObject;

	//UI Vars
	private Text textType;
	private DataBindingContext bindingContext;
	private Group grpAttr;
	private Composite parent;

	public void bindObject(Object object) {
		this.boundObject = object;

		grpAttr.dispose();
		grpAttr = new Group(parent, SWT.NONE);
		GridLayout gl_grpAttr = new GridLayout(2, false);
		gl_grpAttr.horizontalSpacing = 3;
		grpAttr.setLayout(gl_grpAttr);
		grpAttr.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		grpAttr.setText("Attrs");
		createAttrControls(grpAttr, boundObject);

		parent.layout();
		initDataBindings();
	}

	@PostConstruct
	public void createControls(Composite parent, EPartService partService) {
		this.partService = partService;
		this.parent = parent;
		GridLayout gl_parent = new GridLayout(2, false);
		gl_parent.horizontalSpacing = 2;
		parent.setLayout(gl_parent);

		Label lblType = new Label(parent, SWT.NONE);
		lblType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblType.setText("Type");

		textType = new Text(parent, SWT.BORDER);
		textType.setEditable(false);
		textType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		grpAttr = new Group(parent, SWT.NONE);
		grpAttr.setLayout(new GridLayout(2, false));
		grpAttr.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		grpAttr.setText("Attrs");
		createAttrControls(grpAttr, boundObject);

		m_bindingContext = initDataBindings();

	}

	public void createAttrControls(Composite parent, Object object) {
		if (object != null) {
			List<Field> attrList = ReflectionUtils.retieveAllFields(object.getClass());
			Field[] attributes = new Field[attrList.size()];
			attrList.toArray(attributes);

			for (int i = 0; i < attributes.length; i++) {
				if (!Modifier.isStatic(attributes[i].getModifiers())) {
					if (attributes[i].getType().isPrimitive()) {
						if (attributes[i].getType().equals(boolean.class)) {
							Button btnCheckBox = new Button(parent, SWT.CHECK);
							btnCheckBox.setText(object.getClass().getSimpleName() + "." + attributes[i].getName());
							btnCheckBox.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));

							bindCheck(object, attributes[i], btnCheckBox);
						} else {
							Label lblType = new Label(parent, SWT.NONE);
							lblType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
							lblType.setText(object.getClass().getSimpleName() + "." + attributes[i].getName());

							Text textAttr = new Text(parent, SWT.BORDER);
							bindText(object, attributes[i], textAttr);
						}
					} else {
						Object subObject;
						try {
							if (attributes[i].getType().equals(String.class)) {
								Label lblType = new Label(parent, SWT.NONE);
								lblType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
								lblType.setText(object.getClass().getSimpleName() + "." + attributes[i].getName());

								Text textAttr = new Text(parent, SWT.BORDER);
								bindText(object, attributes[i], textAttr);
							} else {
								if (attributes[i].isAccessible()) {
									subObject = attributes[i].get(object);
								} else {
									attributes[i].setAccessible(true);
									subObject = attributes[i].get(object);
									attributes[i].setAccessible(false);
								}
								Group grpSubAttr = new Group(parent, SWT.NONE);
								grpSubAttr.setLayout(new GridLayout(2, false));
								grpSubAttr.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
								grpSubAttr.setText(attributes[i].getName());
								createAttrControls(grpSubAttr, subObject);
							}
						} catch (IllegalArgumentException | IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}

	}

	private void bindCheck(Object object, Field attribute, Button btnCheckBox) {
		IObservableValue widgetValue = WidgetProperties.selection().observe(btnCheckBox);
		IObservableValue modelValue = PojoProperties.value(attribute.getName()).observe(object);
		bindingContext.bindValue(widgetValue, modelValue);
	}

	private void bindText(Object object, Field attribute, Text textAttr) {
		textAttr.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textAttr.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				EditorPart editorPart = (EditorPart) partService.findPart(EditorPart.ID).getObject();
				IScene scene = editorPart.getBoundScene();
				editorPart.bindScene(scene);
			}
		});

		IObservableValue observeTextTextTypeObserveWidget = WidgetProperties.text(SWT.Modify).observe(textAttr);
		IObservableValue simpleNameBoundObjectgetClassObserveValue = PojoProperties.value(attribute.getName()).observe(object);
		bindingContext.bindValue(observeTextTextTypeObserveWidget, simpleNameBoundObjectgetClassObserveValue, null, null);
	}

	protected DataBindingContext initDataBindings() {
		bindingContext = new DataBindingContext();
		if (boundObject != null) {
			//
			IObservableValue observeTextTextTypeObserveWidget = WidgetProperties.text(SWT.None).observe(textType);
			IObservableValue simpleNameBoundObjectgetClassObserveValue = PojoProperties.value("simpleName").observe(boundObject.getClass());
			bindingContext.bindValue(observeTextTextTypeObserveWidget, simpleNameBoundObjectgetClassObserveValue, null, null);
			//
		}
		return bindingContext;
	}
}