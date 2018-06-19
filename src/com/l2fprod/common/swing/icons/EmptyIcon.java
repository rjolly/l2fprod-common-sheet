// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   EmptyIcon.java

package com.l2fprod.common.swing.icons;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

public final class EmptyIcon
	implements Icon {

	private int width;
	private int height;

	public EmptyIcon() {
		this(0, 0);
	}

	public EmptyIcon(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getIconHeight() {
		return height;
	}

	public int getIconWidth() {
		return width;
	}

	public void paintIcon(Component component, Graphics g1, int i, int j) {
	}
}
