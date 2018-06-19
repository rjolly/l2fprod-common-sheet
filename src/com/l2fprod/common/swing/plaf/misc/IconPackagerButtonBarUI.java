// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   IconPackagerButtonBarUI.java

package com.l2fprod.common.swing.plaf.misc;

import com.l2fprod.common.swing.JButtonBar;
import com.l2fprod.common.swing.plaf.ButtonBarButtonUI;
import com.l2fprod.common.swing.plaf.basic.BasicButtonBarUI;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;

public class IconPackagerButtonBarUI extends BasicButtonBarUI {
	static class ButtonUI extends BasicButtonUI
		implements ButtonBarButtonUI {

		private static Color selectedBackground;
		private static Color selectedBorder;
		private static Color selectedForeground;
		private static Color unselectedForeground;

		public void installUI(JComponent c) {
			super.installUI(c);
			AbstractButton button = (AbstractButton)c;
			button.setOpaque(false);
			button.setRolloverEnabled(true);
			button.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		}

		public void paint(Graphics g, JComponent c) {
			AbstractButton button = (AbstractButton)c;
			if (button.getModel().isSelected()) {
				Color oldColor = g.getColor();
				g.setColor(selectedBackground);
				g.fillRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 5, 5);
				g.setColor(selectedBorder);
				g.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 5, 5);
				g.setColor(oldColor);
			}
			if (c.getClientProperty("html") != null) {
				ButtonModel model = button.getModel();
				if (model.isEnabled()) {
					if (model.isSelected())
						button.setForeground(selectedForeground);
					else
						button.setForeground(unselectedForeground);
				} else {
					button.setForeground(unselectedForeground.darker());
				}
			}
			super.paint(g, c);
		}

		protected void paintText(Graphics g, AbstractButton b, Rectangle textRect, String text) {
			ButtonModel model = b.getModel();
			FontMetrics fm = g.getFontMetrics();
			int mnemonicIndex = b.getDisplayedMnemonicIndex();
			Color oldColor = g.getColor();
			if (model.isEnabled()) {
				if (model.isSelected())
					g.setColor(selectedForeground);
				else
					g.setColor(unselectedForeground);
			} else {
				g.setColor(unselectedForeground.darker());
			}
			BasicGraphicsUtils.drawStringUnderlineCharAt(g, text, mnemonicIndex, textRect.x + getTextShiftOffset(), textRect.y + fm.getAscent() + getTextShiftOffset());
			g.setColor(oldColor);
		}

		static  {
			selectedBackground = Color.white;
			selectedBorder = Color.black;
			selectedForeground = Color.black;
			unselectedForeground = Color.white;
		}

		ButtonUI() {
		}
	}


	public IconPackagerButtonBarUI() {
	}

	public static ComponentUI createUI(JComponent c) {
		return new IconPackagerButtonBarUI();
	}

	protected void installDefaults() {
		javax.swing.border.Border b = bar.getBorder();
		if (b == null || (b instanceof UIResource))
			bar.setBorder(new BorderUIResource(new CompoundBorder(BorderFactory.createEtchedBorder(), BorderFactory.createEmptyBorder(2, 2, 2, 2))));
		if (bar.getBackground() == null || (bar.getBackground() instanceof UIResource)) {
			bar.setBackground(new ColorUIResource(128, 128, 128));
			bar.setOpaque(true);
		}
	}

	public void installButtonBarUI(AbstractButton button) {
		button.setUI(new ButtonUI());
		button.setHorizontalTextPosition(0);
		button.setVerticalTextPosition(3);
	}
}
