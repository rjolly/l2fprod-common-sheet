// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   CellEditorAdapter.java

package com.l2fprod.common.propertysheet;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreeCellEditor;

public class CellEditorAdapter extends AbstractCellEditor
	implements TableCellEditor, TreeCellEditor {
	class SelectOnFocus
		implements FocusListener {

		public void focusGained(final FocusEvent e) {
			if (!(e.getSource() instanceof JTextField)) {
				return;
			} else {
				SwingUtilities.invokeLater(new Runnable() {

					public void run() {
						((JTextField)e.getSource()).selectAll();
					}

				});
				return;
			}
		}

		public void focusLost(final FocusEvent e) {
			if (!(e.getSource() instanceof JTextField)) {
				return;
			} else {
				SwingUtilities.invokeLater(new Runnable() {

					public void run() {
						((JTextField)e.getSource()).select(0, 0);
					}

				});
				return;
			}
		}

		SelectOnFocus() {
		}
	}

	class CancelEditing
		implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			cancelCellEditing();
		}

		CancelEditing() {
		}
	}

	class CommitEditing
		implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			stopCellEditing();
		}

		CommitEditing() {
		}
	}


	protected PropertyEditor editor;
	protected int clickCountToStart;

	public CellEditorAdapter(PropertyEditor editor) {
		clickCountToStart = 1;
		this.editor = editor;
		Component component = editor.getCustomEditor();
		if (component instanceof JTextField) {
			JTextField field = (JTextField)component;
			field.addFocusListener(new SelectOnFocus());
			field.addActionListener(new CommitEditing());
			field.registerKeyboardAction(new CancelEditing(), KeyStroke.getKeyStroke(27, 0), 0);
		}
		editor.addPropertyChangeListener(new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				stopCellEditing();
			}

		});
	}

	public Component getTreeCellEditorComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row) {
		return getEditor(value);
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean selected, int row, int column) {
		return getEditor(value);
	}

	public void setClickCountToStart(int count) {
		clickCountToStart = count;
	}

	public int getClickCountToStart() {
		return clickCountToStart;
	}

	public Object getCellEditorValue() {
		return editor.getValue();
	}

	public boolean isCellEditable(EventObject event) {
		if (event instanceof MouseEvent)
			return ((MouseEvent)event).getClickCount() >= clickCountToStart;
		else
			return true;
	}

	public boolean shouldSelectCell(EventObject event) {
		return true;
	}

	public boolean stopCellEditing() {
		fireEditingStopped();
		return true;
	}

	public void cancelCellEditing() {
		fireEditingCanceled();
	}

	private Component getEditor(Object value) {
		editor.setValue(value);
		final Component cellEditor = editor.getCustomEditor();
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				cellEditor.requestFocus();
			}

		});
		return cellEditor;
	}
}
