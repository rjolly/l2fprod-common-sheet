// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   PropertySheetMain.java

package com.l2fprod.common.demo;

import com.l2fprod.common.swing.LookAndFeelTweaks;
import com.l2fprod.common.util.ResourceManager;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

// Referenced classes of package com.l2fprod.common.demo:
//			PropertySheetPage, PropertySheetPage2, PropertySheetPage3

public class PropertySheetMain extends JPanel {

	static ResourceManager RESOURCE;

	public PropertySheetMain() {
		setLayout(new BorderLayout());
		JTabbedPane tabs = new JTabbedPane();
		tabs.add("Sheet 1", new PropertySheetPage());
		tabs.add("Sheet 2", new PropertySheetPage2());
		tabs.add("Sheet 3", new PropertySheetPage3());
		add("Center", tabs);
	}

	public static void main(String args[]) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		LookAndFeelTweaks.tweak();
		JFrame frame = new JFrame("PropertySheet");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add("Center", new PropertySheetMain());
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
		RESOURCE = ResourceManager.get(com.l2fprod.common.demo.PropertySheetMain.class);
	}
}
