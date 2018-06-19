// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   TaskPaneMain.java

package com.l2fprod.common.demo;

import com.l2fprod.common.swing.JTaskPane;
import com.l2fprod.common.swing.JTaskPaneGroup;
import com.l2fprod.common.swing.LookAndFeelTweaks;
import com.l2fprod.common.swing.plaf.LookAndFeelAddons;
import com.l2fprod.common.util.ResourceManager;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

public class TaskPaneMain extends JPanel {
	static class DemoPanel extends JTaskPane {

		Action makeAction(String title, String tooltiptext, String iconPath) {
			Action action = new AbstractAction(title) {

				public void actionPerformed(ActionEvent actionevent) {
				}

			};
			action.putValue("SmallIcon", new ImageIcon((TaskPaneMain.class$com$l2fprod$common$demo$TaskPaneMain != null ? TaskPaneMain.class$com$l2fprod$common$demo$TaskPaneMain : (TaskPaneMain.class$com$l2fprod$common$demo$TaskPaneMain = TaskPaneMain._mthclass$("com.l2fprod.common.demo.TaskPaneMain"))).getResource(iconPath)));
			action.putValue("ShortDescription", tooltiptext);
			return action;
		}

		public DemoPanel() {
			JTaskPane taskPane = new JTaskPane();
			JTaskPaneGroup systemGroup = new JTaskPaneGroup();
			systemGroup.setTitle(TaskPaneMain.RESOURCE.getString("Main.tasks.systemGroup"));
			systemGroup.setToolTipText(TaskPaneMain.RESOURCE.getString("Main.tasks.systemGroup.tooltip"));
			systemGroup.setSpecial(true);
			systemGroup.setIcon(new ImageIcon((TaskPaneMain.class$com$l2fprod$common$demo$TaskPaneMain != null ? TaskPaneMain.class$com$l2fprod$common$demo$TaskPaneMain : (TaskPaneMain.class$com$l2fprod$common$demo$TaskPaneMain = TaskPaneMain._mthclass$("com.l2fprod.common.demo.TaskPaneMain"))).getResource("icons/tasks-email.png")));
			systemGroup.add(makeAction(TaskPaneMain.RESOURCE.getString("Main.tasks.email"), "", "icons/tasks-email.png"));
			systemGroup.add(makeAction(TaskPaneMain.RESOURCE.getString("Main.tasks.delete"), "", "icons/tasks-recycle.png"));
			taskPane.add(systemGroup);
			JTaskPaneGroup officeGroup = new JTaskPaneGroup();
			officeGroup.setTitle(TaskPaneMain.RESOURCE.getString("Main.tasks.office"));
			officeGroup.add(makeAction(TaskPaneMain.RESOURCE.getString("Main.tasks.word"), "", "icons/tasks-writedoc.png"));
			officeGroup.setExpanded(false);
			officeGroup.setScrollOnExpand(true);
			taskPane.add(officeGroup);
			JTaskPaneGroup seeAlsoGroup = new JTaskPaneGroup();
			seeAlsoGroup.setTitle(TaskPaneMain.RESOURCE.getString("Main.tasks.seealso"));
			seeAlsoGroup.add(makeAction("The Internet", TaskPaneMain.RESOURCE.getString("Main.tasks.internet.tooltip"), "icons/tasks-internet.png"));
			seeAlsoGroup.add(makeAction(TaskPaneMain.RESOURCE.getString("Main.tasks.help"), TaskPaneMain.RESOURCE.getString("Main.tasks.help.tooltip"), "icons/tasks-question.png"));
			taskPane.add(seeAlsoGroup);
			JTaskPaneGroup detailsGroup = new JTaskPaneGroup();
			detailsGroup.setTitle(TaskPaneMain.RESOURCE.getString("Main.tasks.details"));
			detailsGroup.setScrollOnExpand(true);
			JEditorPane detailsText = new JEditorPane("text/html", "<html>");
			LookAndFeelTweaks.makeMultilineLabel(detailsText);
			LookAndFeelTweaks.htmlize(detailsText);
			detailsText.setText(TaskPaneMain.RESOURCE.getString("Main.tasks.details.message"));
			detailsGroup.add(detailsText);
			taskPane.add(detailsGroup);
			JScrollPane scroll = new JScrollPane(taskPane);
			scroll.setBorder(null);
			setLayout(new BorderLayout());
			add("Center", scroll);
			setBorder(null);
		}
	}


	static ResourceManager RESOURCE;

	public TaskPaneMain() throws Exception {
		setLayout(new BorderLayout());
		JTabbedPane tabs = new JTabbedPane();
		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		LookAndFeelAddons.setAddon(com.l2fprod.common.swing.plaf.metal.MetalLookAndFeelAddons.class);
		DemoPanel demo = new DemoPanel();
		tabs.addTab("Metal L&F", demo);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		UIManager.put("win.xpstyle.name", "luna");
		LookAndFeelAddons.setAddon(com.l2fprod.common.swing.plaf.windows.WindowsLookAndFeelAddons.class);
		demo = new DemoPanel();
		tabs.addTab("Windows L&F (Luna)", demo);
		UIManager.put("win.xpstyle.name", "homestead");
		LookAndFeelAddons.setAddon(com.l2fprod.common.swing.plaf.windows.WindowsLookAndFeelAddons.class);
		demo = new DemoPanel();
		tabs.addTab("Windows L&F (Homestead)", demo);
		UIManager.put("win.xpstyle.name", "metallic");
		LookAndFeelAddons.setAddon(com.l2fprod.common.swing.plaf.windows.WindowsLookAndFeelAddons.class);
		demo = new DemoPanel();
		tabs.addTab("Windows L&F (Metallic)", demo);
		UIManager.put("win.xpstyle.name", null);
		LookAndFeelAddons.setAddon(com.l2fprod.common.swing.plaf.windows.WindowsClassicLookAndFeelAddons.class);
		demo = new DemoPanel();
		tabs.addTab("Windows L&F (Classic)", demo);
		LookAndFeelAddons.setAddon(com.l2fprod.common.swing.plaf.aqua.AquaLookAndFeelAddons.class);
		demo = new DemoPanel();
		tabs.addTab("Glossy", demo);
		try {
			Class.forName("com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
			UIManager.setLookAndFeel("com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
			LookAndFeelAddons.setAddon(LookAndFeelAddons.getBestMatchAddonClassName());
			demo = new DemoPanel();
			tabs.addTab("JGoodies Plastic3D", demo);
		}
		catch (Exception e) { }
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		add("Center", tabs);
	}

	public static void main(String args[]) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		JFrame frame = new JFrame("JTaskPane / JTaskPaneGroup");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add("Center", new TaskPaneMain());
		frame.setDefaultCloseOperation(3);
		frame.pack();
		frame.setLocation(100, 100);
		frame.setVisible(true);
	}

	static  {
		RESOURCE = ResourceManager.get(com.l2fprod.common.demo.TaskPaneMain.class);
	}
}
