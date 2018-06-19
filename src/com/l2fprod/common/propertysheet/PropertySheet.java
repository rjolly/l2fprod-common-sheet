// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   PropertySheet.java

package com.l2fprod.common.propertysheet;

import java.util.Iterator;

// Referenced classes of package com.l2fprod.common.propertysheet:
//			Property

public interface PropertySheet {

	public static final int VIEW_AS_FLAT_LIST = 0;
	public static final int VIEW_AS_CATEGORIES = 1;

	public abstract void setProperties(Property aproperty[]);

	public abstract Property[] getProperties();

	public abstract void addProperty(Property property);

	public abstract void addProperty(int i, Property property);

	public abstract void removeProperty(Property property);

	public abstract int getPropertyCount();

	public abstract Iterator propertyIterator();
}
