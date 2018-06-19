// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   ObjectTableModel.java

package com.l2fprod.common.swing;

import javax.swing.table.TableModel;

public interface ObjectTableModel
	extends TableModel {

	public abstract Object getObject(int i);
}
