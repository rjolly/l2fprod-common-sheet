// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   FontChooserModel.java

package com.l2fprod.common.swing;


public interface FontChooserModel {

	public abstract String[] getFontFamilies(String s);

	public abstract int[] getDefaultSizes();

	public abstract String[] getCharSets();

	public abstract String getPreviewMessage(String s);
}
