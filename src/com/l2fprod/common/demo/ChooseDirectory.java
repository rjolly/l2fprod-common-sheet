// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   ChooseDirectory.java

package com.l2fprod.common.demo;

import com.l2fprod.common.swing.JDirectoryChooser;
import com.l2fprod.common.swing.LookAndFeelTweaks;
import com.l2fprod.common.swing.PercentLayout;
import com.l2fprod.common.util.ResourceManager;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;

// Referenced classes of package com.l2fprod.common.demo:
//			FakeFileSystemView

public class ChooseDirectory extends JPanel {

	public static final ResourceManager RESOURCE;

	public ChooseDirectory() {
		setLayout(new PercentLayout(1, 3));
		if (System.getProperty("javawebstart.version") != null) {
			JTextArea area = new JTextArea(RESOURCE.getString("message.webstart"));
			LookAndFeelTweaks.makeMultilineLabel(area);
			area.setFocusable(false);
			add(area);
		}
		final JButton button = new JButton(RESOURCE.getString("selectDirectory"));
		add(button);
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ChooseDirectory.selectDirectory(button, null);
			}

		});
	}

	static void selectDirectory(Component parent, String selectedFile) {
		JDirectoryChooser chooser;
		if (System.getProperty("javawebstart.version") != null) {
			chooser = new JDirectoryChooser(new FakeFileSystemView()) {

				public void rescanCurrentDirectory() {
				}

				public void setCurrentDirectory(File file) {
				}

			};
			chooser.setShowingCreateDirectory(false);
		} else {
			chooser = new JDirectoryChooser();
			if (selectedFile != null)
				chooser.setSelectedFile(new File(selectedFile));
		}
		JTextArea accessory = new JTextArea(RESOURCE.getString("selectDirectory.message"));
		accessory.setLineWrap(true);
		accessory.setWrapStyleWord(true);
		accessory.setEditable(false);
		accessory.setOpaque(false);
		accessory.setFont(UIManager.getFont("Tree.font"));
		accessory.setFocusable(false);
		chooser.setAccessory(accessory);
		chooser.setMultiSelectionEnabled(true);
		int choice = chooser.showOpenDialog(parent);
		if (choice == 0) {
			String filenames = "";
			File selectedFiles[] = chooser.getSelectedFiles();
			int i = 0;
			for (int c = selectedFiles.length; i < c; i++)
				filenames = filenames + "\n" + selectedFiles[i];

			JOptionPane.showMessageDialog(parent, RESOURCE.getString("selectDirectory.confirm", new Object[] {
				filenames
			}));
		} else {
			JOptionPane.showMessageDialog(parent, RESOURCE.getString("selectDirectory.cancel"));
		}
	}

	public static void main(String args[]) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		if (args.length > 0)
			selectDirectory(null, args[0]);
		else
			selectDirectory(null, null);
		System.exit(0);
	}

	static Class _mthclass$(String x0) {
		return Class.forName(x0);
		ClassNotFoundException x1;
		x1;
		throw new NoClassDefFoundError(x1.getMessage());
	}

	static  {
		RESOURCE = ResourceManager.get(com.l2fprod.common.demo.ChooseDirectory.class);
	}
}
