// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   TaskPaneGroupUI.java

package com.l2fprod.common.swing.plaf;

import java.awt.Component;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.plaf.PanelUI;

public class TaskPaneGroupUI extends PanelUI {

	public TaskPaneGroupUI() {
	}

	public Component createAction(Action action) {
		return new JButton(action);
	}
}
