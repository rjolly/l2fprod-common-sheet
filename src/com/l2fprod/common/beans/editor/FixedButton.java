// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   FixedButton.java

package com.l2fprod.common.beans.editor;

import com.l2fprod.common.util.OS;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

public final class FixedButton extends JButton {

	public FixedButton() {
		super("...");
		if (OS.isMacOSX() && UIManager.getLookAndFeel().isNativeLookAndFeel())
			setPreferredSize(new Dimension(16, 30));
		setMargin(new Insets(0, 0, 0, 0));
	}
}
