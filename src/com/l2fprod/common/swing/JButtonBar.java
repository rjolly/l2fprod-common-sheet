// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JButtonBar.java

package com.l2fprod.common.swing;

import com.l2fprod.common.swing.plaf.ButtonBarButtonUI;
import com.l2fprod.common.swing.plaf.ButtonBarUI;
import com.l2fprod.common.swing.plaf.JButtonBarAddon;
import com.l2fprod.common.swing.plaf.LookAndFeelAddons;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.plaf.ComponentUI;

public class JButtonBar extends JComponent
	implements SwingConstants {

	public static final String UI_CLASS_ID = "ButtonBarUI";
	public static final String ORIENTATION_CHANGED_KEY = "orientation";
	private int orientation;
	private static PropertyChangeListener uiUpdater = new PropertyChangeListener() {

		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getSource() instanceof AbstractButton) {
				AbstractButton button = (AbstractButton)evt.getSource();
				if ((button.getParent() instanceof JButtonBar) && !(button.getUI() instanceof ButtonBarButtonUI))
					((ButtonBarUI)((PropertyChangeEvent) ((JButtonBar)button.getParent())).getSource).installButtonBarUI(button);
			}
		}

	};
	private static ContainerListener buttonTracker = new ContainerAdapter() {

		public void componentAdded(ContainerEvent e) {
			JButtonBar container = (JButtonBar)e.getContainer();
			if (e.getChild() instanceof AbstractButton) {
				((ButtonBarUI)((ContainerEvent) (container)).getContainer).installButtonBarUI((AbstractButton)e.getChild());
				((AbstractButton)e.getChild()).addPropertyChangeListener("UI", JButtonBar.uiUpdater);
			}
		}

		public void componentRemoved(ContainerEvent e) {
			if (e.getChild() instanceof AbstractButton)
				((AbstractButton)e.getChild()).removePropertyChangeListener("UI", JButtonBar.uiUpdater);
		}

	};

	public JButtonBar() {
		this(0);
	}

	public JButtonBar(int orientation) {
		this.orientation = orientation;
		updateUI();
		addContainerListener(buttonTracker);
	}

	public void updateUI() {
		setUI((ButtonBarUI)LookAndFeelAddons.getUI(this, com.l2fprod.common.swing.plaf.ButtonBarUI.class));
	}

	public ButtonBarUI getUI() {
		return (ButtonBarUI)ui;
	}

	public void setUI(ButtonBarUI ui) {
		super.setUI(ui);
		java.awt.Component components[] = getComponents();
		int i = 0;
		for (int c = components.length; i < c; i++)
			if (components[i] instanceof AbstractButton)
				ui.installButtonBarUI((AbstractButton)components[i]);

	}

	public String getUIClassID() {
		return "ButtonBarUI";
	}

	public void setOrientation(int orientation) {
		if (orientation != 0 && orientation != 1)
			throw new IllegalArgumentException("orientation must be one of: VERTICAL, HORIZONTAL");
		if (this.orientation != orientation) {
			int oldOrientation = this.orientation;
			this.orientation = orientation;
			firePropertyChange("orientation", oldOrientation, this.orientation);
		}
	}

	public int getOrientation() {
		return orientation;
	}

	static  {
		LookAndFeelAddons.contribute(new JButtonBarAddon());
	}



}
