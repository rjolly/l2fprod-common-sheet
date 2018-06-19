// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   TipModel.java

package com.l2fprod.common.swing;


public interface TipModel {
	public static interface Tip {

		public abstract String getTipName();

		public abstract Object getTip();
	}


	public abstract int getTipCount();

	public abstract Tip getTipAt(int i);
}
