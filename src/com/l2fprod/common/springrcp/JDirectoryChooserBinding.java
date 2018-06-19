// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JDirectoryChooserBinding.java

package com.l2fprod.common.springrcp;

import com.l2fprod.common.swing.JDirectoryChooser;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.JComponent;
import org.springframework.binding.form.FormModel;
import org.springframework.richclient.form.binding.support.CustomBinding;

public class JDirectoryChooserBinding extends CustomBinding {

	private final JDirectoryChooser component;

	public JDirectoryChooserBinding(FormModel model, String property, JDirectoryChooser component) {
		super(model, property, java.io.File.class);
		this.component = component;
	}

	protected JComponent doBindControl() {
		component.setSelectedFile((File)getValue());
		component.addPropertyChangeListener(new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				String prop = evt.getPropertyName();
				if ("SelectedFileChangedProperty".equals(prop)) {
					File file = (File)evt.getNewValue();
					controlValueChanged(file);
				}
			}

		});
		return component;
	}

	protected void readOnlyChanged() {
		component.setEnabled(isEnabled() && !isReadOnly());
	}

	protected void enabledChanged() {
		component.setEnabled(isEnabled() && !isReadOnly());
	}

	protected void valueModelChanged(Object newValue) {
		component.setSelectedFile((File)newValue);
	}

}
