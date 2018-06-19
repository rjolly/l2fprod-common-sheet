// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   DirectoryPropertyEditor.java

package com.l2fprod.common.beans.editor;

import com.l2fprod.common.swing.UserPreferences;
import com.l2fprod.common.util.ResourceManager;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

// Referenced classes of package com.l2fprod.common.beans.editor:
//			FilePropertyEditor

public class DirectoryPropertyEditor extends FilePropertyEditor {

	public DirectoryPropertyEditor() {
	}

	protected void selectFile() {
		ResourceManager rm = ResourceManager.all(com.l2fprod.common.beans.editor.FilePropertyEditor.class);
		JFileChooser chooser = UserPreferences.getDefaultDirectoryChooser();
		chooser.setDialogTitle(rm.getString("DirectoryPropertyEditor.dialogTitle"));
		chooser.setApproveButtonText(rm.getString("DirectoryPropertyEditor.approveButtonText"));
		chooser.setApproveButtonMnemonic(rm.getChar("DirectoryPropertyEditor.approveButtonMnemonic"));
		File oldFile = (File)getValue();
		if (oldFile != null && oldFile.isDirectory())
			try {
				chooser.setCurrentDirectory(oldFile.getCanonicalFile());
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		if (0 == chooser.showOpenDialog(editor)) {
			File newFile = chooser.getSelectedFile();
			String text = newFile.getAbsolutePath();
			textfield.setText(text);
			firePropertyChange(oldFile, newFile);
		}
	}
}
