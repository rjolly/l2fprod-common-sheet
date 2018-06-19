// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   PropertyRendererFactory.java

package com.l2fprod.common.propertysheet;

import javax.swing.table.TableCellRenderer;

// Referenced classes of package com.l2fprod.common.propertysheet:
//			Property

public interface PropertyRendererFactory {

	public abstract TableCellRenderer createTableCellRenderer(Property property);

	public abstract TableCellRenderer createTableCellRenderer(Class class1);
}
