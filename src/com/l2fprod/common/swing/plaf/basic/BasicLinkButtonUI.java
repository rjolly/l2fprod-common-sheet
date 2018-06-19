// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   BasicLinkButtonUI.java

package com.l2fprod.common.swing.plaf.basic;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.text.View;

public class BasicLinkButtonUI extends BasicButtonUI {
	static class HandCursor extends MouseAdapter {

		public void mouseEntered(MouseEvent e) {
			e.getComponent().setCursor(Cursor.getPredefinedCursor(12));
		}

		public void mouseExited(MouseEvent e) {
			e.getComponent().setCursor(Cursor.getDefaultCursor());
		}

		HandCursor() {
		}
	}


	private static Rectangle viewRect = new Rectangle();
	private static Rectangle textRect = new Rectangle();
	private static Rectangle iconRect = new Rectangle();
	private static MouseListener handCursorListener = new HandCursor();
	protected int dashedRectGapX;
	protected int dashedRectGapY;
	protected int dashedRectGapWidth;
	protected int dashedRectGapHeight;
	private Color focusColor;

	public BasicLinkButtonUI() {
	}

	public static ComponentUI createUI(JComponent c) {
		return new BasicLinkButtonUI();
	}

	protected void installDefaults(AbstractButton b) {
		super.installDefaults(b);
		b.setOpaque(false);
		b.setBorderPainted(false);
		b.setRolloverEnabled(true);
		dashedRectGapX = UIManager.getInt("ButtonUI.dashedRectGapX");
		dashedRectGapY = UIManager.getInt("ButtonUI.dashedRectGapY");
		dashedRectGapWidth = UIManager.getInt("ButtonUI.dashedRectGapWidth");
		dashedRectGapHeight = UIManager.getInt("ButtonUI.dashedRectGapHeight");
		focusColor = UIManager.getColor("ButtonUI.focus");
		b.setHorizontalAlignment(2);
	}

	protected void installListeners(AbstractButton b) {
		super.installListeners(b);
		b.addMouseListener(handCursorListener);
	}

	protected void uninstallListeners(AbstractButton b) {
		super.uninstallListeners(b);
		b.removeMouseListener(handCursorListener);
	}

	protected Color getFocusColor() {
		return focusColor;
	}

	public void paint(Graphics g, JComponent c) {
		AbstractButton b = (AbstractButton)c;
		ButtonModel model = b.getModel();
		java.awt.FontMetrics fm = g.getFontMetrics();
		Insets i = c.getInsets();
		viewRect.x = i.left;
		viewRect.y = i.top;
		viewRect.width = b.getWidth() - (i.right + viewRect.x);
		viewRect.height = b.getHeight() - (i.bottom + viewRect.y);
		textRect.x = textRect.y = textRect.width = textRect.height = 0;
		iconRect.x = iconRect.y = iconRect.width = iconRect.height = 0;
		java.awt.Font f = c.getFont();
		g.setFont(f);
		String text = SwingUtilities.layoutCompoundLabel(c, fm, b.getText(), b.getIcon(), b.getVerticalAlignment(), b.getHorizontalAlignment(), b.getVerticalTextPosition(), b.getHorizontalTextPosition(), viewRect, iconRect, textRect, b.getText() != null ? b.getIconTextGap() : 0);
		clearTextShiftOffset();
		if (model.isArmed() && model.isPressed())
			paintButtonPressed(g, b);
		if (b.getIcon() != null)
			paintIcon(g, c, iconRect);
		java.awt.Composite oldComposite = ((Graphics2D)g).getComposite();
		if (model.isRollover())
			((Graphics2D)g).setComposite(AlphaComposite.getInstance(3, 0.5F));
		if (text != null && !text.equals("")) {
			View v = (View)c.getClientProperty("html");
			if (v != null) {
				textRect.x += getTextShiftOffset();
				textRect.y += getTextShiftOffset();
				v.paint(g, textRect);
				textRect.x -= getTextShiftOffset();
				textRect.y -= getTextShiftOffset();
			} else {
				paintText(g, b, textRect, text);
			}
		}
		if (b.isFocusPainted() && b.hasFocus())
			paintFocus(g, b, viewRect, textRect, iconRect);
		((Graphics2D)g).setComposite(oldComposite);
	}

	protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {
		if (b.getParent() instanceof JToolBar) {
			return;
		} else {
			int width = b.getWidth();
			int height = b.getHeight();
			g.setColor(getFocusColor());
			BasicGraphicsUtils.drawDashedRect(g, dashedRectGapX, dashedRectGapY, width - dashedRectGapWidth, height - dashedRectGapHeight);
			return;
		}
	}

	protected void paintButtonPressed(Graphics g, AbstractButton b) {
		setTextShiftOffset();
	}

}
