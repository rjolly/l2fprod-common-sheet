// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   UIUtilities.java

package com.l2fprod.common.swing;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

public class UIUtilities {

	public UIUtilities() {
	}

	public static void centerOnScreen(Window window) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = window.getSize();
		window.setLocation((screenSize.width - size.width) / 2, (screenSize.height - size.height) / 2);
	}
}
