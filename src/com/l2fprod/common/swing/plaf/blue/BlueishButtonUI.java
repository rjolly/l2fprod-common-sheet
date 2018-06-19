// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   BlueishButtonUI.java

package com.l2fprod.common.swing.plaf.blue;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

public class BlueishButtonUI extends BasicButtonUI {

	private static Color blueishBackgroundOver = new Color(224, 232, 246);
	private static Color blueishBorderOver = new Color(152, 180, 226);
	private static Color blueishBackgroundSelected = new Color(193, 210, 238);
	private static Color blueishBorderSelected = new Color(49, 106, 197);

	public BlueishButtonUI() {
	}

	public void installUI(JComponent c) {
		super.installUI(c);
		AbstractButton button = (AbstractButton)c;
		button.setRolloverEnabled(true);
		button.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
	}

	public void paint(Graphics g, JComponent c) {
		AbstractButton button = (AbstractButton)c;
		if (button.getModel().isRollover() || button.getModel().isArmed() || button.getModel().isSelected()) {
			Color oldColor = g.getColor();
			if (button.getModel().isSelected())
				g.setColor(blueishBackgroundSelected);
			else
				g.setColor(blueishBackgroundOver);
			g.fillRect(0, 0, c.getWidth() - 1, c.getHeight() - 1);
			if (button.getModel().isSelected())
				g.setColor(blueishBorderSelected);
			else
				g.setColor(blueishBorderOver);
			g.drawRect(0, 0, c.getWidth() - 1, c.getHeight() - 1);
			g.setColor(oldColor);
		}
		super.paint(g, c);
	}

}
