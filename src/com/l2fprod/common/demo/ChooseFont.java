// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   ChooseFont.java

package com.l2fprod.common.demo;

import com.l2fprod.common.swing.JFontChooser;
import com.l2fprod.common.swing.PercentLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class ChooseFont extends JPanel {

	public ChooseFont() {
		setLayout(new PercentLayout(1, 3));
		JFontChooser chooser = new JFontChooser();
		chooser.setSelectedFont(new Font("Dialog", 3, 56));
		add("*", chooser);
		JButton button = new JButton("Click here to show JFontChooser");
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ChooseFont.selectFont(ChooseFont.this);
			}

		});
		add(button);
	}

	private static void selectFont(Component parent) {
		Font selectedFont = JFontChooser.showDialog(parent, "Choose Font", null);
		if (selectedFont == null)
			JOptionPane.showMessageDialog(parent, "You clicked 'Cancel'");
		else
			JOptionPane.showMessageDialog(parent, "You selected '" + selectedFont.getName() + "'");
	}

	public static void main(String args[]) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		selectFont(null);
		System.exit(0);
	}

}
