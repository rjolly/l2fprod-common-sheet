// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JDirectoryChooser.java

package com.l2fprod.common.swing;

import com.l2fprod.common.swing.plaf.DirectoryChooserUI;
import com.l2fprod.common.swing.plaf.JDirectoryChooserAddon;
import com.l2fprod.common.swing.plaf.LookAndFeelAddons;
import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.ComponentUI;

// Referenced classes of package com.l2fprod.common.swing:
//			LookAndFeelTweaks

public class JDirectoryChooser extends JFileChooser {

	public static final String UI_CLASS_ID = "l2fprod/DirectoryChooserUI";
	private boolean showingCreateDirectory;
	public static final String SHOWING_CREATE_DIRECTORY_CHANGED_KEY = "showingCreateDirectory";

	public JDirectoryChooser() {
		setShowingCreateDirectory(true);
	}

	public JDirectoryChooser(File currentDirectory) {
		super(currentDirectory);
		setShowingCreateDirectory(true);
	}

	public JDirectoryChooser(File currentDirectory, FileSystemView fsv) {
		super(currentDirectory, fsv);
		setShowingCreateDirectory(true);
	}

	public JDirectoryChooser(FileSystemView fsv) {
		super(fsv);
		setShowingCreateDirectory(true);
	}

	public JDirectoryChooser(String currentDirectoryPath) {
		super(currentDirectoryPath);
		setShowingCreateDirectory(true);
	}

	public JDirectoryChooser(String currentDirectoryPath, FileSystemView fsv) {
		super(currentDirectoryPath, fsv);
		setShowingCreateDirectory(true);
	}

	public void updateUI() {
		setUI((DirectoryChooserUI)LookAndFeelAddons.getUI(this, com.l2fprod.common.swing.plaf.DirectoryChooserUI.class));
	}

	public void setUI(DirectoryChooserUI ui) {
		super.setUI((ComponentUI)ui);
	}

	public String getUIClassID() {
		return "l2fprod/DirectoryChooserUI";
	}

	public boolean isShowingCreateDirectory() {
		return showingCreateDirectory;
	}

	public void setShowingCreateDirectory(boolean showingCreateDirectory) {
		this.showingCreateDirectory = showingCreateDirectory;
		firePropertyChange("showingCreateDirectory", !showingCreateDirectory, showingCreateDirectory);
	}

	protected JDialog createDialog(Component parent) throws HeadlessException {
		JDialog dialog = super.createDialog(parent);
		((JComponent)dialog.getContentPane()).setBorder(LookAndFeelTweaks.WINDOW_BORDER);
		return dialog;
	}

	static  {
		LookAndFeelAddons.contribute(new JDirectoryChooserAddon());
	}
}
