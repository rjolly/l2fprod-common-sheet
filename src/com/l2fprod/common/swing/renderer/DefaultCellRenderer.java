// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   DefaultCellRenderer.java

package com.l2fprod.common.swing.renderer;

import com.l2fprod.common.model.DefaultObjectRenderer;
import com.l2fprod.common.model.ObjectRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;

public class DefaultCellRenderer extends DefaultTableCellRenderer
	implements ListCellRenderer {

	private ObjectRenderer objectRenderer;
	private Color oddBackgroundColor;
	private Color evenBackgroundColor;
	private boolean showOddAndEvenRows;

	public DefaultCellRenderer() {
		objectRenderer = new DefaultObjectRenderer();
		oddBackgroundColor = SystemColor.window;
		evenBackgroundColor = SystemColor.window;
		showOddAndEvenRows = true;
	}

	public void setOddBackgroundColor(Color c) {
		oddBackgroundColor = c;
	}

	public void setEvenBackgroundColor(Color c) {
		evenBackgroundColor = c;
	}

	public void setShowOddAndEvenRows(boolean b) {
		showOddAndEvenRows = b;
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		setBorder(null);
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		setValue(value);
		return this;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (showOddAndEvenRows && !isSelected)
			if (row % 2 == 0)
				setBackground(oddBackgroundColor);
			else
				setBackground(evenBackgroundColor);
		setValue(value);
		return this;
	}

	public void setValue(Object value) {
		String text = convertToString(value);
		Icon icon = convertToIcon(value);
		setText(text != null ? text : "");
		setIcon(icon);
	}

	protected String convertToString(Object value) {
		return objectRenderer.getText(value);
	}

	protected Icon convertToIcon(Object value) {
		return null;
	}
}
