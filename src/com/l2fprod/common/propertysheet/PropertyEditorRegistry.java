// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   PropertyEditorRegistry.java

package com.l2fprod.common.propertysheet;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package com.l2fprod.common.propertysheet:
//			PropertyDescriptorAdapter, PropertyEditorFactory, Property

public class PropertyEditorRegistry
	implements PropertyEditorFactory {

	private Map typeToEditor;
	private Map propertyToEditor;

	public PropertyEditorRegistry() {
		typeToEditor = new HashMap();
		propertyToEditor = new HashMap();
		registerDefaults();
	}

	public PropertyEditor createPropertyEditor(Property property) {
		return getEditor(property);
	}

	public synchronized PropertyEditor getEditor(Property property) {
		PropertyEditor editor = null;
		if (property instanceof PropertyDescriptorAdapter) {
			PropertyDescriptor descriptor = ((PropertyDescriptorAdapter)property).getDescriptor();
			if (descriptor != null) {
				Class clz = descriptor.getPropertyEditorClass();
				if (clz != null)
					editor = loadPropertyEditor(clz);
			}
		}
		if (editor == null) {
			Object value = propertyToEditor.get(property);
			if (value instanceof PropertyEditor)
				editor = (PropertyEditor)value;
			else
			if (value instanceof Class)
				editor = loadPropertyEditor((Class)value);
			else
				editor = getEditor(property.getType());
		}
		if (editor == null && (property instanceof PropertyDescriptorAdapter)) {
			PropertyDescriptor descriptor = ((PropertyDescriptorAdapter)property).getDescriptor();
			Class clz = descriptor.getPropertyType();
			editor = PropertyEditorManager.findEditor(clz);
		}
		return editor;
	}

	private PropertyEditor loadPropertyEditor(Class clz) {
		PropertyEditor editor = null;
		try {
			editor = (PropertyEditor)clz.newInstance();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return editor;
	}

	public synchronized PropertyEditor getEditor(Class type) {
		PropertyEditor editor = null;
		Object value = typeToEditor.get(type);
		if (value instanceof PropertyEditor)
			editor = (PropertyEditor)value;
		else
		if (value instanceof Class)
			try {
				editor = (PropertyEditor)((Class)value).newInstance();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		return editor;
	}

	public synchronized void registerEditor(Class type, Class editorClass) {
		typeToEditor.put(type, editorClass);
	}

	public synchronized void registerEditor(Class type, PropertyEditor editor) {
		typeToEditor.put(type, editor);
	}

	public synchronized void unregisterEditor(Class type) {
		typeToEditor.remove(type);
	}

	public synchronized void registerEditor(Property property, Class editorClass) {
		propertyToEditor.put(property, editorClass);
	}

	public synchronized void registerEditor(Property property, PropertyEditor editor) {
		propertyToEditor.put(property, editor);
	}

	public synchronized void unregisterEditor(Property property) {
		propertyToEditor.remove(property);
	}

	public void registerDefaults() {
		typeToEditor.clear();
		propertyToEditor.clear();
		registerEditor(java.lang.String.class, com.l2fprod.common.beans.editor.StringPropertyEditor.class);
		registerEditor(Double.TYPE, com.l2fprod.common.beans.editor.DoublePropertyEditor.class);
		registerEditor(java.lang.Double.class, com.l2fprod.common.beans.editor.DoublePropertyEditor.class);
		registerEditor(Float.TYPE, com.l2fprod.common.beans.editor.FloatPropertyEditor.class);
		registerEditor(java.lang.Float.class, com.l2fprod.common.beans.editor.FloatPropertyEditor.class);
		registerEditor(Integer.TYPE, com.l2fprod.common.beans.editor.IntegerPropertyEditor.class);
		registerEditor(java.lang.Integer.class, com.l2fprod.common.beans.editor.IntegerPropertyEditor.class);
		registerEditor(Long.TYPE, com.l2fprod.common.beans.editor.LongPropertyEditor.class);
		registerEditor(java.lang.Long.class, com.l2fprod.common.beans.editor.LongPropertyEditor.class);
		registerEditor(Short.TYPE, com.l2fprod.common.beans.editor.ShortPropertyEditor.class);
		registerEditor(java.lang.Short.class, com.l2fprod.common.beans.editor.ShortPropertyEditor.class);
		registerEditor(Boolean.TYPE, com.l2fprod.common.beans.editor.BooleanAsCheckBoxPropertyEditor.class);
		registerEditor(java.lang.Boolean.class, com.l2fprod.common.beans.editor.BooleanAsCheckBoxPropertyEditor.class);
		registerEditor(java.io.File.class, com.l2fprod.common.beans.editor.FilePropertyEditor.class);
		registerEditor(java.awt.Color.class, com.l2fprod.common.beans.editor.ColorPropertyEditor.class);
		registerEditor(java.awt.Dimension.class, com.l2fprod.common.beans.editor.DimensionPropertyEditor.class);
		registerEditor(java.awt.Insets.class, com.l2fprod.common.beans.editor.InsetsPropertyEditor.class);
		try {
			Class fontEditor = Class.forName("com.l2fprod.common.beans.editor.FontPropertyEditor");
			registerEditor(java.awt.Font.class, fontEditor);
		}
		catch (Exception e) { }
		registerEditor(java.awt.Rectangle.class, com.l2fprod.common.beans.editor.RectanglePropertyEditor.class);
		boolean foundDateEditor = false;
		try {
			Class.forName("com.toedter.calendar.JDateChooser");
			registerEditor(java.util.Date.class, Class.forName("com.l2fprod.common.beans.editor.JCalendarDatePropertyEditor"));
			foundDateEditor = true;
		}
		catch (ClassNotFoundException e) { }
		if (!foundDateEditor)
			try {
				Class.forName("net.sf.nachocalendar.components.DateField");
				registerEditor(java.util.Date.class, Class.forName("com.l2fprod.common.beans.editor.NachoCalendarDatePropertyEditor"));
				foundDateEditor = true;
			}
			catch (ClassNotFoundException e) { }
	}
}
