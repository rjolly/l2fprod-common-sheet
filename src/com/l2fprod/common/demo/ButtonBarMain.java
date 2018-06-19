// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   ButtonBarMain.java

package com.l2fprod.common.demo;

import com.l2fprod.common.swing.JButtonBar;
import com.l2fprod.common.swing.plaf.blue.BlueishButtonBarUI;
import com.l2fprod.common.swing.plaf.misc.IconPackagerButtonBarUI;
import com.l2fprod.common.util.ResourceManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.UIManager;

public class ButtonBarMain extends JPanel {
	static class ButtonBarPanel extends JPanel {

		private Component currentComponent;

		private JPanel makePanel(String title) {
			JPanel panel = new JPanel(new BorderLayout());
			JLabel top = new JLabel(title);
			top.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
			top.setFont(top.getFont().deriveFont(1));
			top.setOpaque(true);
			top.setBackground(panel.getBackground().brighter());
			panel.add("North", top);
			panel.setPreferredSize(new Dimension(400, 300));
			panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
			return panel;
		}

		private void show(Component component) {
			if (currentComponent != null)
				remove(currentComponent);
			add("Center", currentComponent = component);
			revalidate();
			repaint();
		}

		private void addButton(final String title, String iconUrl, Component component, JButtonBar bar, ButtonGroup group) {
			javax.swing.Action action = new AbstractAction(new ImageIcon((ButtonBarMain.class$com$l2fprod$common$demo$ButtonBarMain != null ? ButtonBarMain.class$com$l2fprod$common$demo$ButtonBarMain : (ButtonBarMain.class$com$l2fprod$common$demo$ButtonBarMain = ButtonBarMain._mthclass$("com.l2fprod.common.demo.ButtonBarMain"))).getResource(iconUrl)), component) {

				public void actionPerformed(ActionEvent e) {
					show(component);
				}

			};
			JToggleButton button = new JToggleButton(action);
			bar.add(button);
			group.add(button);
			if (group.getSelection() == null) {
				button.setSelected(true);
				show(component);
			}
		}


		public ButtonBarPanel(JButtonBar toolbar) {
			setLayout(new BorderLayout());
			add("West", toolbar);
			ButtonGroup group = new ButtonGroup();
			addButton(ButtonBarMain.RESOURCE.getString("Main.welcome"), "icons/welcome32x32.png", makePanel(ButtonBarMain.RESOURCE.getString("Main.welcome")), toolbar, group);
			addButton(ButtonBarMain.RESOURCE.getString("Main.settings"), "icons/propertysheet32x32.png", makePanel(ButtonBarMain.RESOURCE.getString("Main.settings")), toolbar, group);
			addButton(ButtonBarMain.RESOURCE.getString("Main.sounds"), "icons/fonts32x32.png", makePanel(ButtonBarMain.RESOURCE.getString("Main.sounds")), toolbar, group);
			addButton(ButtonBarMain.RESOURCE.getString("Main.stats"), "icons/folder32x32.png", makePanel(ButtonBarMain.RESOURCE.getString("Main.stats")), toolbar, group);
		}
	}


	static ResourceManager RESOURCE;

	public ButtonBarMain() {
		setLayout(new BorderLayout());
		JTabbedPane tabs = new JTabbedPane();
		add("Center", tabs);
		JButtonBar toolbar = new JButtonBar(1);
		toolbar.setUI(new BlueishButtonBarUI());
		tabs.addTab("Mozilla L&F", new ButtonBarPanel(toolbar));
		toolbar = new JButtonBar(1);
		toolbar.setUI(new IconPackagerButtonBarUI());
		tabs.addTab("Icon Packager L&F", new ButtonBarPanel(toolbar));
	}

	public static void main(String args[]) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		JFrame frame = new JFrame("ButtonBar");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add("Center", new ButtonBarMain());
		frame.setDefaultCloseOperation(3);
		frame.pack();
		frame.setLocation(100, 100);
		frame.setVisible(true);
	}

	static Class _mthclass$(String x0) {
		return Class.forName(x0);
		ClassNotFoundException x1;
		x1;
		throw new NoClassDefFoundError(x1.getMessage());
	}

	static  {
		RESOURCE = ResourceManager.get(com.l2fprod.common.demo.ButtonBarMain.class);
	}
}
