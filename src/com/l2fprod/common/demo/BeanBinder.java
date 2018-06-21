// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   BeanBinder.java

package com.l2fprod.common.demo;

import com.l2fprod.common.model.DefaultBeanInfoResolver;
import com.l2fprod.common.propertysheet.Property;
import com.l2fprod.common.propertysheet.PropertySheetPanel;
import java.beans.BeanInfo;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

public class BeanBinder {

	private final Object bean;
	private final PropertySheetPanel sheet;
	private final PropertyChangeListener listener;

	public BeanBinder(Object bean, PropertySheetPanel sheet) {
		this(bean, sheet, (new DefaultBeanInfoResolver()).getBeanInfo(bean));
	}

	public BeanBinder(final Object bean, final PropertySheetPanel sheet, BeanInfo beanInfo) {
		this.bean = bean;
		this.sheet = sheet;
		sheet.setProperties(beanInfo.getPropertyDescriptors());
		sheet.readFromObject(bean);
		listener = new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				Property prop = (Property)evt.getSource();
				try {
					prop.writeToObject(bean);
				}
				catch (RuntimeException e) {
					if (e.getCause() instanceof PropertyVetoException) {
						UIManager.getLookAndFeel().provideErrorFeedback(sheet);
						prop.setValue(evt.getOldValue());
					}
				}
			}

		};
		sheet.addPropertySheetChangeListener(listener);
	}

	public void unbind() {
		sheet.removePropertyChangeListener(listener);
		sheet.setProperties(new Property[0]);
	}


}
