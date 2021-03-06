// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   FilePropertyEditor.java

package com.l2fprod.common.beans.editor;

import com.l2fprod.common.swing.ComponentFactory;
import com.l2fprod.common.swing.LookAndFeelTweaks;
import com.l2fprod.common.swing.PercentLayout;
import com.l2fprod.common.swing.UserPreferences;
import com.l2fprod.common.util.ResourceManager;
import java.io.File;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.TransferHandler;

// Referenced classes of package com.l2fprod.common.beans.editor:
//			AbstractPropertyEditor

public class FilePropertyEditor extends AbstractPropertyEditor {
	class FileTransferHandler extends TransferHandler {

		public boolean canImport(JComponent comp, java.awt.datatransfer.DataFlavor transferFlavors[]) {
			int i = 0;
			for (int c = transferFlavors.length; i < c; i++)
				if (transferFlavors[i].equals(java.awt.datatransfer.DataFlavor.javaFileListFlavor))
					return true;

			return false;
		}

		public boolean importData(JComponent comp, java.awt.datatransfer.Transferable t) {
			try {
				List list = (List)t.getTransferData(java.awt.datatransfer.DataFlavor.javaFileListFlavor);
				if (list.size() > 0) {
					File oldFile = (File)getValue();
					File newFile = (File)list.get(0);
					String text = newFile.getAbsolutePath();
					textfield.setText(text);
					firePropertyChange(oldFile, newFile);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}

		FileTransferHandler() {
		}
	}


	protected JTextField textfield;
	private JButton button;
	private JButton cancelButton;

	public FilePropertyEditor() {
		this(true);
	}

	public FilePropertyEditor(boolean asTableEditor) {
		editor = new JPanel(new PercentLayout(0, 0)) {

			public void setEnabled(boolean enabled) {
				super.setEnabled(enabled);
				textfield.setEnabled(enabled);
				button.setEnabled(enabled);
				cancelButton.setEnabled(enabled);
			}

		};
		((JPanel)editor).add("*", textfield = new JTextField());
		((JPanel)editor).add(button = com.l2fprod.common.swing.ComponentFactory.Helper.getFactory().createMiniButton());
		if (asTableEditor)
			textfield.setBorder(LookAndFeelTweaks.EMPTY_BORDER);
		button.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent e) {
				selectFile();
			}

		});
		((JPanel)editor).add(cancelButton = com.l2fprod.common.swing.ComponentFactory.Helper.getFactory().createMiniButton());
		cancelButton.setText("X");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent e) {
				selectNull();
			}

		});
		textfield.setTransferHandler(new FileTransferHandler());
	}

	public Object getValue() {
		if ("".equals(textfield.getText().trim()))
			return null;
		else
			return new File(textfield.getText());
	}

	public void setValue(Object value) {
		if (value instanceof File)
			textfield.setText(((File)value).getAbsolutePath());
		else
			textfield.setText("");
	}

	protected void selectFile() {
		ResourceManager rm = ResourceManager.all(com.l2fprod.common.beans.editor.FilePropertyEditor.class);
		JFileChooser chooser = UserPreferences.getDefaultFileChooser();
		chooser.setDialogTitle(rm.getString("FilePropertyEditor.dialogTitle"));
		chooser.setApproveButtonText(rm.getString("FilePropertyEditor.approveButtonText"));
		chooser.setApproveButtonMnemonic(rm.getChar("FilePropertyEditor.approveButtonMnemonic"));
		customizeFileChooser(chooser);
		if (0 == chooser.showOpenDialog(editor)) {
			File oldFile = (File)getValue();
			File newFile = chooser.getSelectedFile();
			String text = newFile.getAbsolutePath();
			textfield.setText(text);
			firePropertyChange(oldFile, newFile);
		}
	}

	protected void customizeFileChooser(JFileChooser jfilechooser) {
	}

	protected void selectNull() {
		Object oldFile = getValue();
		textfield.setText("");
		firePropertyChange(oldFile, null);
	}


}
