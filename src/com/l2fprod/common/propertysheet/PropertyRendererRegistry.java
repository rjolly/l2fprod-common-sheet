// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   PropertyRendererRegistry.java

package com.l2fprod.common.propertysheet;

import com.l2fprod.common.beans.ExtendedPropertyDescriptor;
import com.l2fprod.common.swing.renderer.BooleanCellRenderer;
import com.l2fprod.common.swing.renderer.ColorCellRenderer;
import com.l2fprod.common.swing.renderer.DateRenderer;
import com.l2fprod.common.swing.renderer.DefaultCellRenderer;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.TableCellRenderer;

// Referenced classes of package com.l2fprod.common.propertysheet:
//			PropertyDescriptorAdapter, PropertyRendererFactory, Property

public class PropertyRendererRegistry
	implements PropertyRendererFactory {

	private Map typeToRenderer;
	private Map propertyToRenderer;

	public PropertyRendererRegistry() {
		typeToRenderer = new HashMap();
		propertyToRenderer = new HashMap();
		registerDefaults();
	}

	public TableCellRenderer createTableCellRenderer(Property property) {
		return getRenderer(property);
	}

	public TableCellRenderer createTableCellRenderer(Class type) {
		return getRenderer(type);
	}

	public synchronized TableCellRenderer getRenderer(Property property) {
		TableCellRenderer renderer;
		java.beans.PropertyDescriptor descriptor;
		renderer = null;
		if (property instanceof PropertyDescriptorAdapter) {
			descriptor = ((PropertyDescriptorAdapter)property).getDescriptor();
			if (descriptor instanceof ExtendedPropertyDescriptor && ((ExtendedPropertyDescriptor)descriptor).getPropertyTableRendererClass() != null) {
				try {
					return (TableCellRenderer)((ExtendedPropertyDescriptor)descriptor).getPropertyTableRendererClass().newInstance();
				} catch (final Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		Object value = propertyToRenderer.get(property);
		if (value instanceof TableCellRenderer)
			renderer = (TableCellRenderer)value;
		else
		if (value instanceof Class)
			try {
				renderer = (TableCellRenderer)((Class)value).newInstance();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		else
			renderer = getRenderer(property.getType());
		return renderer;
	}

	public synchronized TableCellRenderer getRenderer(Class type) {
		TableCellRenderer renderer = null;
		Object value = typeToRenderer.get(type);
		if (value instanceof TableCellRenderer)
			renderer = (TableCellRenderer)value;
		else
		if (value instanceof Class)
			try {
				renderer = (TableCellRenderer)((Class)value).newInstance();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		return renderer;
	}

	public synchronized void registerRenderer(Class type, Class rendererClass) {
		typeToRenderer.put(type, rendererClass);
	}

	public synchronized void registerRenderer(Class type, TableCellRenderer renderer) {
		typeToRenderer.put(type, renderer);
	}

	public synchronized void unregisterRenderer(Class type) {
		typeToRenderer.remove(type);
	}

	public synchronized void registerRenderer(Property property, Class rendererClass) {
		propertyToRenderer.put(property, rendererClass);
	}

	public synchronized void registerRenderer(Property property, TableCellRenderer renderer) {
		propertyToRenderer.put(property, renderer);
	}

	public synchronized void unregisterRenderer(Property property) {
		propertyToRenderer.remove(property);
	}

	public void registerDefaults() {
		typeToRenderer.clear();
		propertyToRenderer.clear();
		DefaultCellRenderer renderer = new DefaultCellRenderer();
		renderer.setShowOddAndEvenRows(false);
		ColorCellRenderer colorRenderer = new ColorCellRenderer();
		colorRenderer.setShowOddAndEvenRows(false);
		BooleanCellRenderer booleanRenderer = new BooleanCellRenderer();
		DateRenderer dateRenderer = new DateRenderer();
		dateRenderer.setShowOddAndEvenRows(false);
		registerRenderer(java.lang.Object.class, renderer);
		registerRenderer(java.awt.Color.class, colorRenderer);
		registerRenderer(Boolean.TYPE, booleanRenderer);
		registerRenderer(java.lang.Boolean.class, booleanRenderer);
		registerRenderer(Byte.TYPE, renderer);
		registerRenderer(java.lang.Byte.class, renderer);
		registerRenderer(Character.TYPE, renderer);
		registerRenderer(java.lang.Character.class, renderer);
		registerRenderer(Double.TYPE, renderer);
		registerRenderer(java.lang.Double.class, renderer);
		registerRenderer(Float.TYPE, renderer);
		registerRenderer(java.lang.Float.class, renderer);
		registerRenderer(Integer.TYPE, renderer);
		registerRenderer(java.lang.Integer.class, renderer);
		registerRenderer(Long.TYPE, renderer);
		registerRenderer(java.lang.Long.class, renderer);
		registerRenderer(Short.TYPE, renderer);
		registerRenderer(java.lang.Short.class, renderer);
		registerRenderer(java.util.Date.class, dateRenderer);
	}
}
