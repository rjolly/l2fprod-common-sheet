// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   BasicTaskPaneUI.java

package com.l2fprod.common.swing.plaf.basic;

import com.l2fprod.common.swing.JTaskPane;
import com.l2fprod.common.swing.PercentLayout;
import com.l2fprod.common.swing.plaf.TaskPaneUI;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;

public class BasicTaskPaneUI extends TaskPaneUI {

	protected JTaskPane taskPane;
	protected boolean useGradient;
	protected Color gradientStart;
	protected Color gradientEnd;

	public BasicTaskPaneUI() {
	}

	public static ComponentUI createUI(JComponent c) {
		return new BasicTaskPaneUI();
	}

	public void installUI(JComponent c) {
		super.installUI(c);
		taskPane = (JTaskPane)c;
		taskPane.setLayout(new PercentLayout(1, 14));
		taskPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		taskPane.setOpaque(true);
		if (taskPane.getBackground() == null || (taskPane.getBackground() instanceof ColorUIResource))
			taskPane.setBackground(UIManager.getColor("TaskPane.background"));
		useGradient = UIManager.getBoolean("TaskPane.useGradient");
		if (useGradient) {
			gradientStart = UIManager.getColor("TaskPane.backgroundGradientStart");
			gradientEnd = UIManager.getColor("TaskPane.backgroundGradientEnd");
		}
	}

	public void paint(Graphics g, JComponent c) {
		Graphics2D g2d = (Graphics2D)g;
		if (useGradient) {
			java.awt.Paint old = g2d.getPaint();
			GradientPaint gradient = new GradientPaint(0.0F, 0.0F, gradientStart, 0.0F, c.getHeight(), gradientEnd);
			g2d.setPaint(gradient);
			g.fillRect(0, 0, c.getWidth(), c.getHeight());
			g2d.setPaint(old);
		}
	}
}
