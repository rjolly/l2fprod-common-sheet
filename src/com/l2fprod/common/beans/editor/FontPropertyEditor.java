// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   FontPropertyEditor.java

package com.l2fprod.common.beans.editor;

import com.l2fprod.common.swing.ComponentFactory;
import com.l2fprod.common.swing.JFontChooser;
import com.l2fprod.common.swing.PercentLayout;
import com.l2fprod.common.swing.renderer.DefaultCellRenderer;
import com.l2fprod.common.util.ResourceManager;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

// Referenced classes of package com.l2fprod.common.beans.editor:
//			AbstractPropertyEditor

public class FontPropertyEditor extends AbstractPropertyEditor {

	private DefaultCellRenderer label;
	private JButton button;
	private Font font;

	public FontPropertyEditor() {
		editor = new JPanel(new PercentLayout(0, 0));
		((JPanel)editor).add("*", label = new DefaultCellRenderer());
		label.setOpaque(false);
		((JPanel)editor).add(button = com.l2fprod.common.swing.ComponentFactory.Helper.getFactory().createMiniButton());
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				selectFont();
			}

		});
		((JPanel)editor).add(button = com.l2fprod.common.swing.ComponentFactory.Helper.getFactory().createMiniButton());
		button.setText("X");
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				selectNull();
			}

		});
		((JPanel)editor).setOpaque(false);
	}

	public Object getValue() {
		return font;
	}

	public void setValue(Object value) {
		font = (Font)value;
		label.setValue(value);
	}

	protected void selectFont() {
		ResourceManager rm = ResourceManager.all(com.l2fprod.common.beans.editor.FontPropertyEditor.class);
		String title = rm.getString("FontPropertyEditor.title");
		Font selectedFont = JFontChooser.showDialog(editor, title, font);
		if (selectedFont != null) {
			Font oldFont = font;
			Font newFont = selectedFont;
			label.setValue(newFont);
			font = newFont;
			firePropertyChange(oldFont, newFont);
		}
	}

	protected void selectNull() {
		Object oldFont = font;
		label.setValue(null);
		font = null;
		firePropertyChange(oldFont, null);
	}
}
