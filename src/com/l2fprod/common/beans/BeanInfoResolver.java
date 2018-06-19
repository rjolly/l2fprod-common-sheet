// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   BeanInfoResolver.java

package com.l2fprod.common.beans;

import java.beans.BeanInfo;

public interface BeanInfoResolver {

	public abstract BeanInfo getBeanInfo(Object obj);

	public abstract BeanInfo getBeanInfo(Class class1);
}
