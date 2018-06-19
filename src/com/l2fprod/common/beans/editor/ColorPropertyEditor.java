// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   ColorPropertyEditor.java

package com.l2fprod.common.beans.editor;

import com.l2fprod.common.swing.ComponentFactory;
import com.l2fprod.common.swing.PercentLayout;
import com.l2fprod.common.swing.renderer.ColorCellRenderer;
import com.l2fprod.common.util.ResourceManager;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

// Referenced classes of package com.l2fprod.common.beans.editor:
//			AbstractPropertyEditor

public class ColorPropertyEditor extends AbstractPropertyEditor {

	private ColorCellRenderer label;
	private JButton button;
	private Color color;

	public ColorPropertyEditor() {
		editor = new JPanel(new PercentLayout(0, 0));
		((JPanel)editor).add("*", label = new ColorCellRenderer());
		label.setOpaque(false);
		((JPanel)editor).add(button = com.l2fprod.common.swing.ComponentFactory.Helper.getFactory().createMiniButton());
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				selectColor();
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
		return color;
	}

	public void setValue(Object value) {
		color = (Color)value;
		label.setValue(color);
	}

	protected void selectColor() {
		ResourceManager rm = ResourceManager.all(com.l2fprod.common.beans.editor.FilePropertyEditor.class);
		String title = rm.getString("ColorPropertyEditor.title");
		Color selectedColor = JColorChooser.showDialog(editor, title, color);
		if (selectedColor != null) {
			Color oldColor = color;
			Color newColor = selectedColor;
			label.setValue(newColor);
			color = newColor;
			firePropertyChange(oldColor, newColor);
		}
	}

	protected void selectNull() {
		Color oldColor = color;
		label.setValue(null);
		color = null;
		firePropertyChange(oldColor, null);
	}
}
