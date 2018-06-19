// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   OutlookBarMain.java

package com.l2fprod.common.demo;

import com.l2fprod.common.swing.JOutlookBar;
import com.l2fprod.common.swing.PercentLayout;
import com.l2fprod.common.swing.plaf.LookAndFeelAddons;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.plaf.ButtonUI;

public class OutlookBarMain extends JPanel {

	public OutlookBarMain() throws Exception {
		setLayout(new BorderLayout());
		JTabbedPane tabs = new JTabbedPane();
		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		LookAndFeelAddons.setAddon(com.l2fprod.common.swing.plaf.metal.MetalLookAndFeelAddons.class);
		tabs.addTab("Metal L&F", makeOutlookPanel(0));
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		LookAndFeelAddons.setAddon(com.l2fprod.common.swing.plaf.windows.WindowsLookAndFeelAddons.class);
		tabs.addTab("Windows L&F", makeOutlookPanel(2));
		add("Center", tabs);
	}

	JPanel makeOutlookPanel(int alignment) {
		JOutlookBar outlook = new JOutlookBar();
		outlook.setTabPlacement(2);
		addTab(outlook, "Folders");
		addTab(outlook, "Backup");
		JTree tree = new JTree();
		outlook.addTab("A JTree", outlook.makeScrollPane(tree));
		outlook.addTab("Disabled", new JButton());
		outlook.setEnabledAt(3, false);
		outlook.setAllTabsAlignment(alignment);
		JPanel panel = new JPanel(new PercentLayout(0, 3));
		panel.add(outlook, "100");
		return panel;
	}

	void addTab(JOutlookBar tabs, String title) {
		JPanel panel = new JPanel();
		panel.setLayout(new PercentLayout(1, 0));
		panel.setOpaque(false);
		String buttons[] = {
			"Inbox", "icons/outlook-inbox.gif", "Outbox", "icons/outlook-outbox.gif", "Drafts", "icons/outlook-inbox.gif", "Templates", "icons/outlook-inbox.gif", "Deleted Items", "icons/outlook-trash.gif"
		};
		int i = 0;
		for (int c = buttons.length; i < c; i += 2) {
			JButton button = new JButton(buttons[i]);
			try {
				button.setUI((ButtonUI)Class.forName((String)UIManager.get("OutlookButtonUI")).newInstance());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			button.setIcon(new ImageIcon((com.l2fprod.common.demo.OutlookBarMain.class).getResource(buttons[i + 1])));
			panel.add(button);
		}

		javax.swing.JScrollPane scroll = tabs.makeScrollPane(panel);
		tabs.addTab("", scroll);
		int index = tabs.indexOfComponent(scroll);
		tabs.setTitleAt(index, title);
		tabs.setToolTipTextAt(index, title + " Tooltip");
	}

	public static void main(String args[]) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		JFrame frame = new JFrame("JOutlookBar");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add("Center", new OutlookBarMain());
		frame.setDefaultCloseOperation(3);
		frame.pack();
		frame.setLocation(100, 100);
		frame.setVisible(true);
	}
}
