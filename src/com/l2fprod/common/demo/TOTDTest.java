// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   TOTDTest.java

package com.l2fprod.common.demo;

import com.l2fprod.common.swing.JTipOfTheDay;
import com.l2fprod.common.swing.TipModel;
import com.l2fprod.common.swing.plaf.LookAndFeelAddons;
import com.l2fprod.common.swing.tips.DefaultTip;
import com.l2fprod.common.swing.tips.DefaultTipModel;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;

public class TOTDTest extends JPanel {

	static Class class$com$l2fprod$common$swing$plaf$basic$BasicTipOfTheDayUI; /* synthetic field */

	public TOTDTest() {
		setLayout(new GridLayout(2, 1));
		try {
			add(makeAction("Windows Look And Feel", UIManager.getSystemLookAndFeelClassName(), (com.l2fprod.common.swing.plaf.windows.WindowsLookAndFeelAddons.class).getName()));
			add(makeAction("Other Look And Feel", UIManager.getCrossPlatformLookAndFeelClassName(), (com.l2fprod.common.swing.plaf.basic.BasicLookAndFeelAddons.class).getName()));
		}
		catch (Exception e) { }
	}

	public static void main(String args[]) throws Exception {
		LookAndFeelAddons.setTrackingLookAndFeelChanges(false);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		JFrame frame = new JFrame("Tip of the Day Testbed");
		frame.getContentPane().add("Center", new TOTDTest());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(3);
		frame.setVisible(true);
	}

	static JButton makeAction(String title, final String lnf, final String addon) throws Exception {
		final com.l2fprod.common.swing.JTipOfTheDay.ShowOnStartupChoice fake = new com.l2fprod.common.swing.JTipOfTheDay.ShowOnStartupChoice() {

			private boolean value;

			public boolean isShowingOnStartup() {
				return value;
			}

			public void setShowingOnStartup(boolean showOnStartup) {
				value = showOnStartup;
			}

			 {
				value = true;
			}
		};
		ActionListener action = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					UIManager.setLookAndFeel(lnf);
					LookAndFeelAddons.setAddon(addon);
				}
				catch (Exception ex) { }
				if (!fake.isShowingOnStartup() && 0 == JOptionPane.showConfirmDialog(null, "You previously choose to not show tips on startup.\nDo you want to cancel this choice?", "Question", 0))
					fake.setShowingOnStartup(true);
				DefaultTipModel tips = new DefaultTipModel();
				tips.add(new DefaultTip("tip1", "This is the first tip This is the first tip This is the first tip This is the first tip This is the first tip This is the first tip\nThis is the first tip This is the first tip"));
				tips.add(new DefaultTip("tip2", "<html>This is an html <b>TIP</b><br><center><table border=\"1\"><tr><td>1</td><td>entry 1</td></tr><tr><td>2</td><td>entry 2</td></tr><tr><td>3</td><td>entry 3</td></tr></table>"));
				tips.add(new DefaultTip("tip3", new JTree()));
				tips.add(new DefaultTip("tip 4", new ImageIcon((TOTDTest.class$com$l2fprod$common$swing$plaf$basic$BasicTipOfTheDayUI != null ? TOTDTest.class$com$l2fprod$common$swing$plaf$basic$BasicTipOfTheDayUI : (TOTDTest.class$com$l2fprod$common$swing$plaf$basic$BasicTipOfTheDayUI = TOTDTest._mthclass$("com.l2fprod.common.swing.plaf.basic.BasicTipOfTheDayUI"))).getResource("TipOfTheDay24.gif"))));
				JTipOfTheDay totd = new JTipOfTheDay(tips);
				totd.setCurrentTip(0);
				totd.showDialog(new JFrame("title"), fake);
			}

		};
		JButton button = new JButton(title);
		button.addActionListener(action);
		return button;
	}
}
