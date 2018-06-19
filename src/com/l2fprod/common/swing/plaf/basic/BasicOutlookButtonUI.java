// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   BasicOutlookButtonUI.java

package com.l2fprod.common.swing.plaf.basic;

import com.l2fprod.common.swing.border.ButtonBorder;
import com.l2fprod.common.swing.border.FourLineBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;

public class BasicOutlookButtonUI extends BasicButtonUI {
	public static class OutlookButtonBorder extends ButtonBorder {

		FourLineBorder rolloverBorder;
		FourLineBorder pressedBorder;

		protected void paintRollover(AbstractButton b, Graphics g, int x, int y, int width, int height) {
			rolloverBorder.paintBorder(b, g, x, y, width, height);
		}

		protected void paintPressed(AbstractButton b, Graphics g, int x, int y, int width, int height) {
			pressedBorder.paintBorder(b, g, x, y, width, height);
		}

		public Insets getBorderInsets(Component c) {
			return rolloverBorder.getBorderInsets(c);
		}

		public OutlookButtonBorder(Color color1, Color color2) {
			rolloverBorder = new FourLineBorder(color1, color1, color2, color2);
			pressedBorder = new FourLineBorder(color2, color2, color1, color1);
		}
	}


	public BasicOutlookButtonUI() {
	}

	public static ComponentUI createUI(JComponent c) {
		return new BasicOutlookButtonUI();
	}

	protected void installDefaults(AbstractButton b) {
		super.installDefaults(b);
		b.setRolloverEnabled(true);
		b.setOpaque(false);
		b.setHorizontalTextPosition(0);
		b.setVerticalTextPosition(3);
		LookAndFeel.installBorder(b, "OutlookButton.border");
	}

	protected void paintButtonPressed(Graphics g, AbstractButton b) {
		setTextShiftOffset();
	}
}
