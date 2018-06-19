// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   WindowsTaskPaneGroupUI.java

package com.l2fprod.common.swing.plaf.windows;

import com.l2fprod.common.swing.JTaskPaneGroup;
import com.l2fprod.common.swing.plaf.basic.BasicTaskPaneGroupUI;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;

public class WindowsTaskPaneGroupUI extends BasicTaskPaneGroupUI {
	class XPPaneBorder extends com.l2fprod.common.swing.plaf.basic.BasicTaskPaneGroupUI.PaneBorder {

		protected void paintTitleBackground(JTaskPaneGroup group, Graphics g) {
			if (group.isSpecial()) {
				g.setColor(specialTitleBackground);
				g.fillRoundRect(0, 0, group.getWidth(), com.l2fprod.common.swing.plaf.basic.BasicTaskPaneGroupUI.PaneBorder * 2, com.l2fprod.common.swing.plaf.basic.BasicTaskPaneGroupUI.PaneBorder, com.l2fprod.common.swing.plaf.basic.BasicTaskPaneGroupUI.PaneBorder);
				g.fillRect(0, com.l2fprod.common.swing.plaf.basic.BasicTaskPaneGroupUI.PaneBorder, group.getWidth(), this$0 - com.l2fprod.common.swing.plaf.basic.BasicTaskPaneGroupUI.PaneBorder);
			} else {
				java.awt.Paint oldPaint = ((Graphics2D)g).getPaint();
				GradientPaint gradient = new GradientPaint(0.0F, group.getWidth() / 2, group.getComponentOrientation().isLeftToRight() ? titleBackgroundGradientStart : titleBackgroundGradientEnd, group.getWidth(), this$0, group.getComponentOrientation().isLeftToRight() ? titleBackgroundGradientEnd : titleBackgroundGradientStart);
				((Graphics2D)g).setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
				((Graphics2D)g).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				((Graphics2D)g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				((Graphics2D)g).setPaint(gradient);
				g.fillRoundRect(0, 0, group.getWidth(), com.l2fprod.common.swing.plaf.basic.BasicTaskPaneGroupUI.PaneBorder * 2, com.l2fprod.common.swing.plaf.basic.BasicTaskPaneGroupUI.PaneBorder, com.l2fprod.common.swing.plaf.basic.BasicTaskPaneGroupUI.PaneBorder);
				g.fillRect(0, com.l2fprod.common.swing.plaf.basic.BasicTaskPaneGroupUI.PaneBorder, group.getWidth(), this$0 - com.l2fprod.common.swing.plaf.basic.BasicTaskPaneGroupUI.PaneBorder);
				((Graphics2D)g).setPaint(oldPaint);
			}
		}

		protected void paintExpandedControls(JTaskPaneGroup group, Graphics g, int x, int y, int width, int height) {
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			paintOvalAroundControls(group, g, x, y, width, height);
			g.setColor(getPaintColor(group));
			paintChevronControls(group, g, x, y, width, height);
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		}

		protected boolean isMouseOverBorder() {
			return true;
		}

		XPPaneBorder() {
		}
	}


	public WindowsTaskPaneGroupUI() {
	}

	public static ComponentUI createUI(JComponent c) {
		return new WindowsTaskPaneGroupUI();
	}

	protected Border createPaneBorder() {
		return new XPPaneBorder();
	}

	public void update(Graphics g, JComponent c) {
		if (c.isOpaque()) {
			g.setColor(c.getParent().getBackground());
			g.fillRect(0, 0, c.getWidth(), c.getHeight());
			g.setColor(c.getBackground());
			g.fillRect(0, ROUND_HEIGHT, c.getWidth(), c.getHeight() - ROUND_HEIGHT);
		}
		paint(g, c);
	}













}
