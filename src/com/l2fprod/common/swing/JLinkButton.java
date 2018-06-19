// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JLinkButton.java

package com.l2fprod.common.swing;

import com.l2fprod.common.swing.plaf.JLinkButtonAddon;
import com.l2fprod.common.swing.plaf.LookAndFeelAddons;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

public class JLinkButton extends JButton {

	public static final String UI_CLASS_ID = "LinkButtonUI";

	public JLinkButton() {
	}

	public JLinkButton(String text) {
		super(text);
	}

	public JLinkButton(String text, Icon icon) {
		super(text, icon);
	}

	public JLinkButton(Action a) {
		super(a);
	}

	public JLinkButton(Icon icon) {
		super(icon);
	}

	public String getUIClassID() {
		return "LinkButtonUI";
	}

	static  {
		LookAndFeelAddons.contribute(new JLinkButtonAddon());
	}
}
