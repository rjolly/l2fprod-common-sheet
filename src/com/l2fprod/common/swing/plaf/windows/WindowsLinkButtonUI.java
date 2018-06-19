// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   WindowsLinkButtonUI.java

package com.l2fprod.common.swing.plaf.windows;

import com.l2fprod.common.swing.plaf.basic.BasicLinkButtonUI;
import java.awt.Graphics;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

public class WindowsLinkButtonUI extends BasicLinkButtonUI {

	private static WindowsLinkButtonUI buttonUI = new WindowsLinkButtonUI();

	public WindowsLinkButtonUI() {
	}

	public static ComponentUI createUI(JComponent c) {
		return buttonUI;
	}

	protected void paintButtonPressed(Graphics g, AbstractButton b) {
		setTextShiftOffset();
	}

}
