// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   ButtonAreaLayout.java

package com.l2fprod.common.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

public final class ButtonAreaLayout
	implements LayoutManager {

	private int gap;

	public ButtonAreaLayout(int gap) {
		this.gap = gap;
	}

	public void addLayoutComponent(String s, Component component) {
	}

	public void layoutContainer(Container container) {
		Insets insets = container.getInsets();
		Component children[] = container.getComponents();
		int maxWidth = 0;
		int maxHeight = 0;
		int visibleCount = 0;
		int i = 0;
		for (int c = children.length; i < c; i++)
			if (children[i].isVisible()) {
				Dimension componentPreferredSize = children[i].getPreferredSize();
				maxWidth = Math.max(maxWidth, componentPreferredSize.width);
				maxHeight = Math.max(maxHeight, componentPreferredSize.height);
				visibleCount++;
			}

		int usedWidth = maxWidth * visibleCount + gap * (visibleCount - 1);
		visibleCount = 0;
		int i = 0;
		for (int c = children.length; i < c; i++)
			if (children[i].isVisible()) {
				children[i].setBounds((container.getWidth() - insets.right - usedWidth) + (maxWidth + gap) * visibleCount, insets.top, maxWidth, maxHeight);
				visibleCount++;
			}

	}

	public Dimension minimumLayoutSize(Container c) {
		return preferredLayoutSize(c);
	}

	public Dimension preferredLayoutSize(Container container) {
		Insets insets = container.getInsets();
		Component children[] = container.getComponents();
		int maxWidth = 0;
		int maxHeight = 0;
		int visibleCount = 0;
		int i = 0;
		for (int c = children.length; i < c; i++)
			if (children[i].isVisible()) {
				Dimension componentPreferredSize = children[i].getPreferredSize();
				maxWidth = Math.max(maxWidth, componentPreferredSize.width);
				maxHeight = Math.max(maxHeight, componentPreferredSize.height);
				visibleCount++;
			}

		int usedWidth = maxWidth * visibleCount + gap * (visibleCount - 1);
		return new Dimension(insets.left + usedWidth + insets.right, insets.top + maxHeight + insets.bottom);
	}

	public void removeLayoutComponent(Component component) {
	}
}
