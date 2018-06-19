// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JFontChooserBeanInfo.java

package com.l2fprod.common.swing;

import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.Vector;

public class JFontChooserBeanInfo extends SimpleBeanInfo {

	protected BeanDescriptor bd;
	protected Image iconMono16;
	protected Image iconColor16;
	protected Image iconMono32;
	protected Image iconColor32;

	public JFontChooserBeanInfo() throws IntrospectionException {
		bd = new BeanDescriptor(com.l2fprod.common.swing.JFontChooser.class);
		iconMono16 = loadImage("JFontChooser16-mono.gif");
		iconColor16 = loadImage("JFontChooser16.gif");
		iconMono32 = loadImage("JFontChooser32-mono.gif");
		iconColor32 = loadImage("JFontChooser32.gif");
		bd.setName("JFontChooser");
		bd.setShortDescription("A component that supports selecting a Font.");
		bd.setValue("isContainer", Boolean.FALSE);
		BeanInfo info = Introspector.getBeanInfo(getBeanDescriptor().getBeanClass().getSuperclass());
		String order = info.getBeanDescriptor().getValue("propertyorder") != null ? (String)info.getBeanDescriptor().getValue("propertyorder") : "";
		PropertyDescriptor pd[] = getPropertyDescriptors();
		for (int i = 0; i != pd.length; i++)
			if (order.indexOf(pd[i].getName()) == -1)
				order = order + (order.length() != 0 ? ":" : "") + pd[i].getName();

		getBeanDescriptor().setValue("propertyorder", order);
	}

	public BeanInfo[] getAdditionalBeanInfo() {
		Vector bi = new Vector();
		BeanInfo biarr[] = null;
		try {
			for (Class cl = (com.l2fprod.common.swing.JFontChooser.class).getSuperclass(); !cl.equals((javax.swing.JComponent.class).getSuperclass()); cl = cl.getSuperclass())
				bi.addElement(Introspector.getBeanInfo(cl));

			biarr = new BeanInfo[bi.size()];
			bi.copyInto(biarr);
		}
		catch (Exception e) { }
		return biarr;
	}

	public BeanDescriptor getBeanDescriptor() {
		return bd;
	}

	public int getDefaultPropertyIndex() {
		String defName = "";
		if (defName.equals(""))
			return -1;
		PropertyDescriptor pd[] = getPropertyDescriptors();
		for (int i = 0; i < pd.length; i++)
			if (pd[i].getName().equals(defName))
				return i;

		return -1;
	}

	public Image getIcon(int type) {
		if (type == 1)
			return iconColor16;
		if (type == 3)
			return iconMono16;
		if (type == 2)
			return iconColor32;
		if (type == 4)
			return iconMono32;
		else
			return null;
	}

	public PropertyDescriptor[] getPropertyDescriptors() {
		Vector descriptors;
		descriptors = new Vector();
		PropertyDescriptor descriptor = null;
		try {
			descriptor = new PropertyDescriptor("selectedFont", com.l2fprod.common.swing.JFontChooser.class);
		}
		catch (IntrospectionException e) {
			descriptor = new PropertyDescriptor("selectedFont", com.l2fprod.common.swing.JFontChooser.class, "getSelectedFont", null);
		}
		descriptor.setShortDescription("The current font the chooser is to display");
		descriptor.setPreferred(true);
		descriptor.setBound(true);
		descriptors.add(descriptor);
		return (PropertyDescriptor[])descriptors.toArray(new PropertyDescriptor[descriptors.size()]);
		Exception e;
		e;
		e.printStackTrace();
		return null;
	}
}