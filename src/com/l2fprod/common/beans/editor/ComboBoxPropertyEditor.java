// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   ComboBoxPropertyEditor.java

package com.l2fprod.common.beans.editor;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

// Referenced classes of package com.l2fprod.common.beans.editor:
//			AbstractPropertyEditor

public class ComboBoxPropertyEditor extends AbstractPropertyEditor {
	public static final class Value {

		private Object value;
		private Object visualValue;

		public boolean equals(Object o) {
			if (o == this)
				return true;
			return value == o || value != null && value.equals(o);
		}

		public int hashCode() {
			return value != null ? value.hashCode() : 0;
		}



		public Value(Object value, Object visualValue) {
			this.value = value;
			this.visualValue = visualValue;
		}
	}

	public class Renderer extends DefaultListCellRenderer {

		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			Component component = super.getListCellRendererComponent(list, (value instanceof Value) ? ((Value)value).visualValue : value, index, isSelected, cellHasFocus);
			if (icons != null && index >= 0 && (component instanceof JLabel))
				((JLabel)component).setIcon(icons[index]);
			return component;
		}

		public Renderer() {
		}
	}


	private Object oldValue;
	private Icon icons[];

	public ComboBoxPropertyEditor() {
		editor = new JComboBox() {

			public void setSelectedItem(Object anObject) {
				oldValue = getSelectedItem();
				super.setSelectedItem(anObject);
			}

		};
		final JComboBox combo = (JComboBox)editor;
		combo.setRenderer(new Renderer());
		combo.addPopupMenuListener(new PopupMenuListener() {

			public void popupMenuCanceled(PopupMenuEvent popupmenuevent) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				firePropertyChange(oldValue, combo.getSelectedItem());
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent popupmenuevent) {
			}

		});
		combo.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10)
					firePropertyChange(oldValue, combo.getSelectedItem());
			}

		});
	}

	public Object getValue() {
		Object selected = ((JComboBox)editor).getSelectedItem();
		if (selected instanceof Value)
			return ((Value)selected).value;
		else
			return selected;
	}

	public void setValue(Object value) {
		JComboBox combo = (JComboBox)editor;
		Object current = null;
		int index = -1;
		int i = 0;
		int c = combo.getModel().getSize();
		do {
			if (i >= c)
				break;
			current = combo.getModel().getElementAt(i);
			if (value == current || current != null && current.equals(value)) {
				index = i;
				break;
			}
			i++;
		} while (true);
		((JComboBox)editor).setSelectedIndex(index);
	}

	public void setAvailableValues(Object values[]) {
		((JComboBox)editor).setModel(new DefaultComboBoxModel(values));
	}

	public void setAvailableIcons(Icon icons[]) {
		this.icons = icons;
	}



}
