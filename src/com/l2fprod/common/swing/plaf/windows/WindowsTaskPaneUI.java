// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   WindowsTaskPaneUI.java

package com.l2fprod.common.swing.plaf.windows;

import com.l2fprod.common.swing.plaf.basic.BasicTaskPaneUI;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

public class WindowsTaskPaneUI extends BasicTaskPaneUI {

	public WindowsTaskPaneUI() {
	}

	public static ComponentUI createUI(JComponent c) {
		return new WindowsTaskPaneUI();
	}

	public void installUI(JComponent c) {
		super.installUI(c);
	}
}
