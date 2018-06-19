// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   MetalTaskPaneGroupUI.java

package com.l2fprod.common.swing.plaf.metal;

import com.l2fprod.common.swing.JTaskPaneGroup;
import com.l2fprod.common.swing.plaf.basic.BasicTaskPaneGroupUI;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;

public class MetalTaskPaneGroupUI extends BasicTaskPaneGroupUI {
	class MetalPaneBorder extends com.l2fprod.common.swing.plaf.basic.BasicTaskPaneGroupUI.PaneBorder {

		protected void paintExpandedControls(JTaskPaneGroup group, Graphics g, int x, int y, int width, int height) {
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(getPaintColor(group));
			paintRectAroundControls(group, g, x, y, width, height, g.getColor(), g.getColor());
			paintChevronControls(group, g, x, y, width, height);
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		}

		protected boolean isMouseOverBorder() {
			return true;
		}

		MetalPaneBorder() {
		}
	}


	public MetalTaskPaneGroupUI() {
	}

	public static ComponentUI createUI(JComponent c) {
		return new MetalTaskPaneGroupUI();
	}

	protected void installDefaults() {
		super.installDefaults();
		group.setOpaque(false);
	}

	protected Border createPaneBorder() {
		return new MetalPaneBorder();
	}
}
