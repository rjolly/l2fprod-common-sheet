// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   BasicButtonBarUI.java

package com.l2fprod.common.swing.plaf.basic;

import com.l2fprod.common.swing.JButtonBar;
import com.l2fprod.common.swing.PercentLayout;
import com.l2fprod.common.swing.plaf.ButtonBarUI;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

public class BasicButtonBarUI extends ButtonBarUI {
	private class ChangeListener
		implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals("orientation")) {
				updateLayout();
				bar.revalidate();
				bar.repaint();
			}
		}

		private ChangeListener() {
		}

	}


	protected JButtonBar bar;
	protected PropertyChangeListener propertyListener;

	public BasicButtonBarUI() {
	}

	public static ComponentUI createUI(JComponent c) {
		return new BasicButtonBarUI();
	}

	public void installUI(JComponent c) {
		super.installUI(c);
		bar = (JButtonBar)c;
		installDefaults();
		installListeners();
		updateLayout();
	}

	public void uninstallUI(JComponent c) {
		uninstallDefaults();
		uninstallListeners();
		super.uninstallUI(c);
	}

	protected void installDefaults() {
	}

	protected void uninstallDefaults() {
	}

	protected void installListeners() {
		propertyListener = createPropertyChangeListener();
		bar.addPropertyChangeListener(propertyListener);
	}

	protected void uninstallListeners() {
		bar.removePropertyChangeListener(propertyListener);
	}

	protected PropertyChangeListener createPropertyChangeListener() {
		return new ChangeListener();
	}

	protected void updateLayout() {
		if (bar.getOrientation() == 0)
			bar.setLayout(new PercentLayout(0, 2));
		else
			bar.setLayout(new PercentLayout(1, 2));
	}

	public Dimension getPreferredSize(JComponent c) {
		JButtonBar b = (JButtonBar)c;
		Dimension preferred;
		if (b.getLayout() == null)
			preferred = new Dimension(100, 100);
		else
			preferred = b.getLayout().preferredLayoutSize(c);
		if (b.getOrientation() == 0)
			return new Dimension(preferred.width, 53);
		else
			return new Dimension(74, preferred.height);
	}
}
