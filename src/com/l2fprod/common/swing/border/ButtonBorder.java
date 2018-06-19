// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   ButtonBorder.java

package com.l2fprod.common.swing.border;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.border.AbstractBorder;

public class ButtonBorder extends AbstractBorder {

	public ButtonBorder() {
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		if (c instanceof AbstractButton) {
			AbstractButton b = (AbstractButton)c;
			ButtonModel model = b.getModel();
			boolean isPressed = model.isPressed() && model.isArmed();
			boolean isRollover = b.isRolloverEnabled() && model.isRollover();
			boolean isEnabled = b.isEnabled();
			if (!isEnabled)
				paintDisabled(b, g, x, y, width, height);
			else
			if (isPressed)
				paintPressed(b, g, x, y, width, height);
			else
			if (isRollover)
				paintRollover(b, g, x, y, width, height);
			else
				paintNormal(b, g, x, y, width, height);
		}
	}

	protected void paintNormal(AbstractButton abstractbutton, Graphics g1, int i, int j, int k, int l) {
	}

	protected void paintDisabled(AbstractButton abstractbutton, Graphics g1, int i, int j, int k, int l) {
	}

	protected void paintRollover(AbstractButton abstractbutton, Graphics g1, int i, int j, int k, int l) {
	}

	protected void paintPressed(AbstractButton abstractbutton, Graphics g1, int i, int j, int k, int l) {
	}

	public Insets getBorderInsets(Component c) {
		return getBorderInsets(c, new Insets(0, 0, 0, 0));
	}

	public Insets getBorderInsets(Component c, Insets insets) {
		return insets;
	}
}
