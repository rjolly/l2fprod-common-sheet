// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   BaseBeanInfo.java

package com.l2fprod.common.beans;

import com.l2fprod.common.util.ResourceManager;
import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.MissingResourceException;

// Referenced classes of package com.l2fprod.common.beans:
//			DefaultBeanDescriptor, ExtendedPropertyDescriptor

public class BaseBeanInfo extends SimpleBeanInfo {

	private Class type;
	private BeanDescriptor beanDescriptor;
	private List properties;

	public BaseBeanInfo(Class type) {
		properties = new ArrayList(0);
		this.type = type;
	}

	public final Class getType() {
		return type;
	}

	public ResourceManager getResources() {
		return ResourceManager.get(getType());
	}

	public BeanDescriptor getBeanDescriptor() {
		if (beanDescriptor == null)
			beanDescriptor = new DefaultBeanDescriptor(this);
		return beanDescriptor;
	}

	public PropertyDescriptor[] getPropertyDescriptors() {
		return (PropertyDescriptor[])properties.toArray(new PropertyDescriptor[0]);
	}

	public int getPropertyDescriptorCount() {
		return properties.size();
	}

	public PropertyDescriptor getPropertyDescriptor(int index) {
		return (PropertyDescriptor)properties.get(index);
	}

	protected PropertyDescriptor addPropertyDescriptor(PropertyDescriptor property) {
		properties.add(property);
		return property;
	}

	public ExtendedPropertyDescriptor addProperty(String propertyName) {
		ExtendedPropertyDescriptor descriptor;
		MissingResourceException e;
		if (propertyName == null || propertyName.trim().length() == 0)
			throw new IntrospectionException("bad property name");
		descriptor = ExtendedPropertyDescriptor.newPropertyDescriptor(propertyName, getType());
		try {
			descriptor.setDisplayName(getResources().getString(propertyName));
		}
		// Misplaced declaration of an exception variable
		catch (MissingResourceException e) { }
		try {
			descriptor.setShortDescription(getResources().getString(propertyName + ".shortDescription"));
		}
		// Misplaced declaration of an exception variable
		catch (MissingResourceException e) { }
		addPropertyDescriptor(descriptor);
		return descriptor;
		e;
		throw new RuntimeException(e);
	}

	public PropertyDescriptor removeProperty(String propertyName) {
		if (propertyName == null)
			throw new IllegalArgumentException("Property name can not be null");
		for (Iterator iter = properties.iterator(); iter.hasNext();) {
			PropertyDescriptor property = (PropertyDescriptor)iter.next();
			if (propertyName.equals(property.getName())) {
				iter.remove();
				return property;
			}
		}

		return null;
	}

	public java.awt.Image getIcon(int kind) {
		return null;
	}

	public String getText(Object value) {
		return value.toString();
	}

	public String getDescription(Object value) {
		return getText(value);
	}

	public String getToolTipText(Object value) {
		return getText(value);
	}
}
