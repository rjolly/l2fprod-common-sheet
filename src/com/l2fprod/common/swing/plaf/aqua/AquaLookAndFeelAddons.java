// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   AquaLookAndFeelAddons.java

package com.l2fprod.common.swing.plaf.aqua;

import com.l2fprod.common.swing.plaf.basic.BasicLookAndFeelAddons;

public class AquaLookAndFeelAddons extends BasicLookAndFeelAddons {

	public AquaLookAndFeelAddons() {
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
			"TaskPaneGroupUI", "com.l2fprod.common.swing.plaf.misc.GlossyTaskPaneGroupUI"
		};
		return defaults;
	}
}
