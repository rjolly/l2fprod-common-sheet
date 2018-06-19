// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JTaskPaneGroupBeanInfo.java

package com.l2fprod.common.swing;

import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.Vector;

public class JTaskPaneGroupBeanInfo extends SimpleBeanInfo {

	protected BeanDescriptor bd;
	protected Image iconMono16;
	protected Image iconColor16;
	protected Image iconMono32;
	protected Image iconColor32;

	public JTaskPaneGroupBeanInfo() throws IntrospectionException {
		bd = new BeanDescriptor(com.l2fprod.common.swing.JTaskPaneGroup.class);
		iconMono16 = loadImage("JTaskPaneGroup16-mono.gif");
		iconColor16 = loadImage("JTaskPaneGroup16.gif");
		iconMono32 = loadImage("JTaskPaneGroup32-mono.gif");
		iconColor32 = loadImage("JTaskPaneGroup32.gif");
		bd.setName("JTaskPaneGroup");
		bd.setShortDescription("JTaskPaneGroup is a container for tasks and other arbitrary components.");
		bd.setValue("isContainer", Boolean.TRUE);
		bd.setValue("containerDelegate", "getContentPane");
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
			for (Class cl = (com.l2fprod.common.swing.JTaskPaneGroup.class).getSuperclass(); !cl.equals((java.awt.Component.class).getSuperclass()); cl = cl.getSuperclass())
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
			descriptor = new PropertyDescriptor("title", com.l2fprod.common.swing.JTaskPaneGroup.class);
		}
		catch (IntrospectionException e) {
			descriptor = new PropertyDescriptor("title", com.l2fprod.common.swing.JTaskPaneGroup.class, "getTitle", null);
		}
		descriptor.setPreferred(true);
		descriptor.setBound(true);
		descriptors.add(descriptor);
		try {
			descriptor = new PropertyDescriptor("icon", com.l2fprod.common.swing.JTaskPaneGroup.class);
		}
		catch (IntrospectionException e) {
			descriptor = new PropertyDescriptor("icon", com.l2fprod.common.swing.JTaskPaneGroup.class, "getIcon", null);
		}
		descriptor.setPreferred(true);
		descriptor.setBound(true);
		descriptors.add(descriptor);
		try {
			descriptor = new PropertyDescriptor("special", com.l2fprod.common.swing.JTaskPaneGroup.class);
		}
		catch (IntrospectionException e) {
			descriptor = new PropertyDescriptor("special", com.l2fprod.common.swing.JTaskPaneGroup.class, "getSpecial", null);
		}
		descriptor.setPreferred(true);
		descriptor.setBound(true);
		descriptors.add(descriptor);
		try {
			descriptor = new PropertyDescriptor("scrollOnExpand", com.l2fprod.common.swing.JTaskPaneGroup.class);
		}
		catch (IntrospectionException e) {
			descriptor = new PropertyDescriptor("scrollOnExpand", com.l2fprod.common.swing.JTaskPaneGroup.class, "getScrollOnExpand", null);
		}
		descriptor.setPreferred(true);
		descriptor.setBound(true);
		descriptors.add(descriptor);
		try {
			descriptor = new PropertyDescriptor("expanded", com.l2fprod.common.swing.JTaskPaneGroup.class);
		}
		catch (IntrospectionException e) {
			descriptor = new PropertyDescriptor("expanded", com.l2fprod.common.swing.JTaskPaneGroup.class, "getExpanded", null);
		}
		descriptor.setPreferred(true);
		descriptor.setBound(true);
		descriptors.add(descriptor);
		try {
			descriptor = new PropertyDescriptor("animated", com.l2fprod.common.swing.JTaskPaneGroup.class);
		}
		catch (IntrospectionException e) {
			descriptor = new PropertyDescriptor("animated", com.l2fprod.common.swing.JTaskPaneGroup.class, "getAnimated", null);
		}
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
