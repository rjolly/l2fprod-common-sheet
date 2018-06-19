// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   FourLineBorder.java

package com.l2fprod.common.swing.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.Border;

public class FourLineBorder
	implements Border {

	private Color top;
	private Color left;
	private Color bottom;
	private Color right;

	public FourLineBorder(Color top, Color left, Color bottom, Color right) {
		this.top = top;
		this.left = left;
		this.bottom = bottom;
		this.right = right;
	}

	public Insets getBorderInsets(Component c) {
		return new Insets(top != null ? 1 : 0, left != null ? 1 : 0, bottom != null ? 1 : 0, right != null ? 1 : 0);
	}

	public boolean isBorderOpaque() {
		return true;
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		if (bottom != null) {
			g.setColor(bottom);
			g.drawLine(x, (y + height) - 1, (x + width) - 1, (y + height) - 1);
		}
		if (right != null) {
			g.setColor(right);
			g.drawLine((x + width) - 1, y, (x + width) - 1, (y + height) - 1);
		}
		if (top != null) {
			g.setColor(top);
			g.drawLine(x, y, (x + width) - 1, y);
		}
		if (left != null) {
			g.setColor(left);
			g.drawLine(x, y, x, (y + height) - 1);
		}
	}
}
