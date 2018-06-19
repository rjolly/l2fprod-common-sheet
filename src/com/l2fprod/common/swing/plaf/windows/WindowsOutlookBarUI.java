// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   WindowsOutlookBarUI.java

package com.l2fprod.common.swing.plaf.windows;

import com.l2fprod.common.swing.border.ButtonBorder;
import com.l2fprod.common.swing.border.FourLineBorder;
import com.l2fprod.common.swing.plaf.basic.BasicOutlookBarUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class WindowsOutlookBarUI extends BasicOutlookBarUI {
	public static class ThinScrollBarUI extends BasicScrollBarUI {

		public Dimension getPreferredSize(JComponent c) {
			return scrollbar.getOrientation() != 1 ? new Dimension(48, 8) : new Dimension(8, 48);
		}

		public ThinScrollBarUI() {
		}
	}

	public static class WindowsTabButtonBorder extends ButtonBorder {

		FourLineBorder normalBorder;
		FourLineBorder pressedBorder;

		protected void paintNormal(AbstractButton b, Graphics g, int x, int y, int width, int height) {
			normalBorder.paintBorder(b, g, x, y, width, height);
		}

		protected void paintDisabled(AbstractButton b, Graphics g, int x, int y, int width, int height) {
			normalBorder.paintBorder(b, g, x, y, width, height);
		}

		protected void paintRollover(AbstractButton b, Graphics g, int x, int y, int width, int height) {
			normalBorder.paintBorder(b, g, x, y, width, height);
		}

		protected void paintPressed(AbstractButton b, Graphics g, int x, int y, int width, int height) {
			pressedBorder.paintBorder(b, g, x, y, width, height);
		}

		public Insets getBorderInsets(Component c) {
			return normalBorder.getBorderInsets(c);
		}

		public WindowsTabButtonBorder(Color color1, Color color2) {
			normalBorder = new FourLineBorder(color1, color1, color2, color2);
			pressedBorder = new FourLineBorder(color2, color2, color1, color1);
		}
	}


	private Border tabButtonBorder;

	public WindowsOutlookBarUI() {
	}

	public static ComponentUI createUI(JComponent c) {
		return new WindowsOutlookBarUI();
	}

	public JScrollPane makeScrollPane(Component component) {
		JScrollPane scroll = super.makeScrollPane(component);
		scroll.setHorizontalScrollBarPolicy(31);
		scroll.getVerticalScrollBar().setUI(new ThinScrollBarUI());
		return scroll;
	}

	protected void installDefaults() {
		super.installDefaults();
		tabButtonBorder = UIManager.getBorder("OutlookBar.tabButtonBorder");
	}

	protected com.l2fprod.common.swing.plaf.basic.BasicOutlookBarUI.TabButton createTabButton() {
		com.l2fprod.common.swing.plaf.basic.BasicOutlookBarUI.TabButton button = new com.l2fprod.common.swing.plaf.basic.BasicOutlookBarUI.TabButton();
		button.setUI(new BasicButtonUI());
		button.setBorder(tabButtonBorder);
		return button;
	}
}
