// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   Property.java

package com.l2fprod.common.propertysheet;

import java.beans.PropertyChangeListener;
import java.io.Serializable;

public interface Property
	extends Serializable, Cloneable {

	public abstract String getName();

	public abstract String getDisplayName();

	public abstract String getShortDescription();

	public abstract Class getType();

	public abstract Object getValue();

	public abstract void setValue(Object obj);

	public abstract boolean isEditable();

	public abstract String getCategory();

	public abstract void readFromObject(Object obj);

	public abstract void writeToObject(Object obj);

	public abstract void addPropertyChangeListener(PropertyChangeListener propertychangelistener);

	public abstract void removePropertyChangeListener(PropertyChangeListener propertychangelistener);

	public abstract Object clone();

	public abstract Property getParentProperty();

	public abstract Property[] getSubProperties();
}
