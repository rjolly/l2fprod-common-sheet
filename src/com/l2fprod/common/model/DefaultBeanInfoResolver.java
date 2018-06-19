// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   DefaultBeanInfoResolver.java

package com.l2fprod.common.model;

import com.l2fprod.common.beans.BeanInfoResolver;
import java.beans.BeanInfo;

public class DefaultBeanInfoResolver
	implements BeanInfoResolver {

	public DefaultBeanInfoResolver() {
	}

	public BeanInfo getBeanInfo(Object object) {
		if (object == null)
			return null;
		else
			return getBeanInfo(object.getClass());
	}

	public BeanInfo getBeanInfo(Class clazz) {
		String classname;
		if (clazz == null)
			return null;
		classname = clazz.getName();
		int index = classname.indexOf(".impl.basic");
		if (index == -1 || !classname.endsWith("Basic"))
			break MISSING_BLOCK_LABEL_90;
		classname = classname.substring(0, index) + classname.substring(index + ".impl.basic".length(), classname.lastIndexOf("Basic"));
		return getBeanInfo(Class.forName(classname));
		ClassNotFoundException e;
		e;
		e.printStackTrace();
		return null;
		BeanInfo beanInfo = (BeanInfo)Class.forName(classname + "BeanInfo").newInstance();
		return beanInfo;
		beanInfo;
		beanInfo.printStackTrace();
		return null;
	}
}
