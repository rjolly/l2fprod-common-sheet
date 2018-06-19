// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   AbstractPropertyEditor.java

package com.l2fprod.common.beans.editor;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyEditor;

public class AbstractPropertyEditor
	implements PropertyEditor {

	protected Component editor;
	private PropertyChangeSupport listeners;

	public AbstractPropertyEditor() {
		listeners = new PropertyChangeSupport(this);
	}

	public boolean isPaintable() {
		return false;
	}

	public boolean supportsCustomEditor() {
		return false;
	}

	public Component getCustomEditor() {
		return editor;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}

	protected void firePropertyChange(Object oldValue, Object newValue) {
		listeners.firePropertyChange("value", oldValue, newValue);
	}

	public Object getValue() {
		return null;
	}

	public void setValue(Object obj) {
	}

	public String getAsText() {
		return null;
	}

	public String getJavaInitializationString() {
		return null;
	}

	public String[] getTags() {
		return null;
	}

	public void setAsText(String s) throws IllegalArgumentException {
	}

	public void paintValue(Graphics g, Rectangle rectangle) {
	}
}
