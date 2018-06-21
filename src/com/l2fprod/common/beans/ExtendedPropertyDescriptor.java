// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   ExtendedPropertyDescriptor.java

package com.l2fprod.common.beans;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Comparator;

// Referenced classes of package com.l2fprod.common.beans:
//			BeanUtils

public class ExtendedPropertyDescriptor extends PropertyDescriptor {

	private Class tableCellRendererClass;
	private String category;
	public static final Comparator BY_CATEGORY_COMPARATOR = new Comparator() {

		public int compare(Object o1, Object o2) {
			PropertyDescriptor desc1 = (PropertyDescriptor)o1;
			PropertyDescriptor desc2 = (PropertyDescriptor)o2;
			if (desc1 == null && desc2 == null)
				return 0;
			if (desc1 != null && desc2 == null)
				return 1;
			if (desc1 == null && desc2 != null)
				return -1;
			if ((desc1 instanceof ExtendedPropertyDescriptor) && !(desc2 instanceof ExtendedPropertyDescriptor))
				return -1;
			if (!(desc1 instanceof ExtendedPropertyDescriptor) && (desc2 instanceof ExtendedPropertyDescriptor))
				return 1;
			if (!(desc1 instanceof ExtendedPropertyDescriptor) && !(desc2 instanceof ExtendedPropertyDescriptor))
				return String.CASE_INSENSITIVE_ORDER.compare(desc1.getDisplayName(), desc2.getDisplayName());
			int category = String.CASE_INSENSITIVE_ORDER.compare(((ExtendedPropertyDescriptor)desc1).getCategory() != null ? (((ExtendedPropertyDescriptor)desc1).getCategory()) : "", ((ExtendedPropertyDescriptor)desc2).getCategory() != null ? (((ExtendedPropertyDescriptor)desc2).getCategory()) : "");
			if (category == 0)
				return String.CASE_INSENSITIVE_ORDER.compare(desc1.getDisplayName(), desc2.getDisplayName());
			else
				return category;
		}

	};

	public ExtendedPropertyDescriptor(String propertyName, Class beanClass) throws IntrospectionException {
		super(propertyName, beanClass);
		tableCellRendererClass = null;
		category = "";
	}

	public ExtendedPropertyDescriptor(String propertyName, Method getter, Method setter) throws IntrospectionException {
		super(propertyName, getter, setter);
		tableCellRendererClass = null;
		category = "";
	}

	public ExtendedPropertyDescriptor(String propertyName, Class beanClass, String getterName, String setterName) throws IntrospectionException {
		super(propertyName, beanClass, getterName, setterName);
		tableCellRendererClass = null;
		category = "";
	}

	public ExtendedPropertyDescriptor setCategory(String category) {
		this.category = category;
		return this;
	}

	public String getCategory() {
		return category;
	}

	public ExtendedPropertyDescriptor setReadOnly() {
		try {
			setWriteMethod(null);
		}
		catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return this;
	}

	public void setPropertyTableRendererClass(Class tableCellRendererClass) {
		this.tableCellRendererClass = tableCellRendererClass;
	}

	public Class getPropertyTableRendererClass() {
		return tableCellRendererClass;
	}

	public static ExtendedPropertyDescriptor newPropertyDescriptor(String propertyName, Class beanClass) throws IntrospectionException {
		Method readMethod = BeanUtils.getReadMethod(beanClass, propertyName);
		Method writeMethod = null;
		if (readMethod == null) {
			throw new IntrospectionException("No getter for property " + propertyName + " in class " + beanClass.getName());
		} else {
			writeMethod = BeanUtils.getWriteMethod(beanClass, propertyName, readMethod.getReturnType());
			return new ExtendedPropertyDescriptor(propertyName, readMethod, writeMethod);
		}
	}

}
