// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   MetalLookAndFeelAddons.java

package com.l2fprod.common.swing.plaf.metal;

import com.l2fprod.common.swing.plaf.basic.BasicLookAndFeelAddons;

public class MetalLookAndFeelAddons extends BasicLookAndFeelAddons {

	public MetalLookAndFeelAddons() {
	}

	public void initialize() {
		super.initialize();
		loadDefaults(getDefaults());
	}

	public void uninitialize() {
		super.uninitialize();
		unloadDefaults(getDefaults());
	}

	private Object[] getDefaults() {
		Object defaults[] = {
			"DirectoryChooserUI", "com.l2fprod.common.swing.plaf.windows.WindowsDirectoryChooserUI"
		};
		return defaults;
	}
}
