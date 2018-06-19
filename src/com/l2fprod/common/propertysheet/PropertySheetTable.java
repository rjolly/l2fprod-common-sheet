// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   PropertySheetTable.java

package com.l2fprod.common.propertysheet;

import com.l2fprod.common.swing.HeaderlessColumnResizer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.CellEditor;
import javax.swing.Icon;
import javax.swing.InputMap;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

// Referenced classes of package com.l2fprod.common.propertysheet:
//			PropertySheetTableModel, PropertyRendererRegistry, PropertyEditorRegistry, CellEditorAdapter, 
//			Property, PropertyEditorFactory, PropertyRendererFactory

public class PropertySheetTable extends JTable {
	private class NameRenderer extends DefaultTableCellRenderer {

		private CellBorder border;

		private Color getForeground(boolean isProperty, boolean isSelected) {
			return isProperty ? isSelected ? selectedPropertyForeground : propertyForeground : isSelected ? selectedCategoryForeground : categoryForeground;
		}

		private Color getBackground(boolean isProperty, boolean isSelected) {
			return isProperty ? isSelected ? selectedPropertyBackground : propertyBackground : isSelected ? selectedCategoryBackground : categoryBackground;
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, false, row, column);
			PropertySheetTableModel.Item item = (PropertySheetTableModel.Item)value;
			if (column == 1 && !item.isProperty()) {
				setBackground(getBackground(item.isProperty(), isSelected));
				setText("");
				return this;
			} else {
				setBorder(border);
				border.configure((PropertySheetTable)table, item);
				setBackground(getBackground(item.isProperty(), isSelected));
				setForeground(getForeground(item.isProperty(), isSelected));
				setEnabled(!isSelected && item.isProperty() ? item.getProperty().isEditable() : true);
				setText(item.getName());
				return this;
			}
		}

		public NameRenderer() {
			border = new CellBorder();
		}
	}

	private static class CellBorder
		implements Border {

		private int indentWidth;
		private boolean showToggle;
		private boolean toggleState;
		private Icon expandedIcon;
		private Icon collapsedIcon;
		private Insets insets;
		private boolean isProperty;

		public void configure(PropertySheetTable table, PropertySheetTableModel.Item item) {
			isProperty = item.isProperty();
			toggleState = item.isVisible();
			showToggle = item.hasToggle();
			indentWidth = PropertySheetTable.getIndent(table, item);
			insets.left = indentWidth + (showToggle ? 18 : 0) + 2;
		}

		public Insets getBorderInsets(Component c) {
			return insets;
		}

		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			if (!isProperty) {
				Color oldColor = g.getColor();
				g.setColor(c.getBackground());
				g.fillRect(x, y, (x + 18) - 2, y + height);
				g.setColor(oldColor);
			}
			if (showToggle) {
				Icon drawIcon = toggleState ? expandedIcon : collapsedIcon;
				drawIcon.paintIcon(c, g, x + indentWidth + (16 - drawIcon.getIconWidth()) / 2, y + (height - drawIcon.getIconHeight()) / 2);
			}
		}

		public boolean isBorderOpaque() {
			return true;
		}

		private CellBorder() {
			expandedIcon = (Icon)UIManager.get("Tree.expandedIcon");
			collapsedIcon = (Icon)UIManager.get("Tree.collapsedIcon");
			insets = new Insets(1, 0, 1, 1);
		}

	}

	private static class ToggleMouseHandler extends MouseAdapter {

		public void mouseReleased(MouseEvent event) {
			PropertySheetTable table = (PropertySheetTable)event.getComponent();
			int row = table.rowAtPoint(event.getPoint());
			int column = table.columnAtPoint(event.getPoint());
			if (row != -1 && column == 0) {
				PropertySheetTableModel.Item item = table.getSheetModel().getPropertySheetElement(row);
				int x = event.getX() - PropertySheetTable.getIndent(table, item);
				if (x > 0 && x < 18)
					item.toggle();
			}
		}

		private ToggleMouseHandler() {
		}

	}

	private class ToggleAction extends AbstractAction {

		public void actionPerformed(ActionEvent e) {
			int row = getSelectedRow();
			PropertySheetTableModel.Item item = getSheetModel().getPropertySheetElement(row);
			item.toggle();
			addRowSelectionInterval(row, row);
		}

		public boolean isEnabled() {
			int row = getSelectedRow();
			if (row != -1) {
				PropertySheetTableModel.Item item = getSheetModel().getPropertySheetElement(row);
				return item.hasToggle();
			} else {
				return false;
			}
		}

		private ToggleAction() {
		}

	}

	private static class StartEditingAction extends AbstractAction {

		public void actionPerformed(ActionEvent e) {
			JTable table = (JTable)e.getSource();
			if (!table.hasFocus()) {
				CellEditor cellEditor = table.getCellEditor();
				if (cellEditor != null && !cellEditor.stopCellEditing()) {
					return;
				} else {
					table.requestFocus();
					return;
				}
			}
			ListSelectionModel rsm = table.getSelectionModel();
			int anchorRow = rsm.getAnchorSelectionIndex();
			table.editCellAt(anchorRow, 1);
			Component editorComp = table.getEditorComponent();
			if (editorComp != null)
				editorComp.requestFocus();
		}

		private StartEditingAction() {
		}

	}

	private class CancelEditing
		implements TableModelListener {

		public void tableChanged(TableModelEvent e) {
			if (e.getType() == 0) {
				int first = e.getFirstRow();
				int last = e.getLastRow();
				int editingRow = getEditingRow();
				TableCellEditor editor = getCellEditor();
				if (editor != null && first <= editingRow && editingRow <= last)
					editor.cancelCellEditing();
			}
		}

		private CancelEditing() {
		}

	}


	private static final int HOTSPOT_SIZE = 18;
	private static final String TREE_EXPANDED_ICON_KEY = "Tree.expandedIcon";
	private static final String TREE_COLLAPSED_ICON_KEY = "Tree.collapsedIcon";
	private static final String TABLE_BACKGROUND_COLOR_KEY = "Table.background";
	private static final String TABLE_FOREGROUND_COLOR_KEY = "Table.foreground";
	private static final String TABLE_SELECTED_BACKGROUND_COLOR_KEY = "Table.selectionBackground";
	private static final String TABLE_SELECTED_FOREGROUND_COLOR_KEY = "Table.selectionForeground";
	private static final String PANEL_BACKGROUND_COLOR_KEY = "Panel.background";
	private PropertyEditorFactory editorFactory;
	private PropertyRendererFactory rendererFactory;
	private TableCellRenderer nameRenderer;
	private boolean wantsExtraIndent;
	private TableModelListener cancelEditing;
	private Color categoryBackground;
	private Color categoryForeground;
	private Color propertyBackground;
	private Color propertyForeground;
	private Color selectedPropertyBackground;
	private Color selectedPropertyForeground;
	private Color selectedCategoryBackground;
	private Color selectedCategoryForeground;

	public PropertySheetTable() {
		this(new PropertySheetTableModel());
	}

	public PropertySheetTable(PropertySheetTableModel dm) {
		super(dm);
		wantsExtraIndent = false;
		initDefaultColors();
		getSelectionModel().setSelectionMode(0);
		Dimension nullSize = new Dimension(0, 0);
		getTableHeader().setPreferredSize(nullSize);
		getTableHeader().setMinimumSize(nullSize);
		getTableHeader().setMaximumSize(nullSize);
		getTableHeader().setVisible(false);
		new HeaderlessColumnResizer(this);
		setRendererFactory(new PropertyRendererRegistry());
		setEditorFactory(new PropertyEditorRegistry());
		nameRenderer = new NameRenderer();
		putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		setColumnSelectionAllowed(false);
		setRowSelectionAllowed(true);
		getActionMap().put("startEditing", new StartEditingAction());
		getInputMap().put(KeyStroke.getKeyStroke(9, 0), "selectNextRowCell");
		getInputMap().put(KeyStroke.getKeyStroke(9, 64), "selectPreviousRowCell");
		getActionMap().put("toggle", new ToggleAction());
		getInputMap().put(KeyStroke.getKeyStroke(32, 0), "toggle");
		addMouseListener(new ToggleMouseHandler());
	}

	private void initDefaultColors() {
		categoryBackground = UIManager.getColor("Panel.background");
		categoryForeground = UIManager.getColor("Table.foreground").darker().darker().darker();
		selectedCategoryBackground = categoryBackground.darker();
		selectedCategoryForeground = categoryForeground;
		propertyBackground = UIManager.getColor("Table.background");
		propertyForeground = UIManager.getColor("Table.foreground");
		selectedPropertyBackground = UIManager.getColor("Table.selectionBackground");
		selectedPropertyForeground = UIManager.getColor("Table.selectionForeground");
		setGridColor(categoryBackground);
	}

	public Color getCategoryBackground() {
		return categoryBackground;
	}

	public void setCategoryBackground(Color categoryBackground) {
		this.categoryBackground = categoryBackground;
		repaint();
	}

	public Color getCategoryForeground() {
		return categoryForeground;
	}

	public void setCategoryForeground(Color categoryForeground) {
		this.categoryForeground = categoryForeground;
		repaint();
	}

	public Color getSelectedCategoryBackground() {
		return selectedCategoryBackground;
	}

	public void setSelectedCategoryBackground(Color selectedCategoryBackground) {
		this.selectedCategoryBackground = selectedCategoryBackground;
		repaint();
	}

	public Color getSelectedCategoryForeground() {
		return selectedCategoryForeground;
	}

	public void setSelectedCategoryForeground(Color selectedCategoryForeground) {
		this.selectedCategoryForeground = selectedCategoryForeground;
		repaint();
	}

	public Color getPropertyBackground() {
		return propertyBackground;
	}

	public void setPropertyBackground(Color propertyBackground) {
		this.propertyBackground = propertyBackground;
		repaint();
	}

	public Color getPropertyForeground() {
		return propertyForeground;
	}

	public void setPropertyForeground(Color propertyForeground) {
		this.propertyForeground = propertyForeground;
		repaint();
	}

	public Color getSelectedPropertyBackground() {
		return selectedPropertyBackground;
	}

	public void setSelectedPropertyBackground(Color selectedPropertyBackground) {
		this.selectedPropertyBackground = selectedPropertyBackground;
		repaint();
	}

	public Color getSelectedPropertyForeground() {
		return selectedPropertyForeground;
	}

	public void setSelectedPropertyForeground(Color selectedPropertyForeground) {
		this.selectedPropertyForeground = selectedPropertyForeground;
		repaint();
	}

	public void setEditorFactory(PropertyEditorFactory factory) {
		editorFactory = factory;
	}

	public final PropertyEditorFactory getEditorFactory() {
		return editorFactory;
	}

	/**
	 * @deprecated Method setEditorRegistry is deprecated
	 */

	public void setEditorRegistry(PropertyEditorRegistry registry) {
		setEditorFactory(registry);
	}

	/**
	 * @deprecated Method getEditorRegistry is deprecated
	 */

	public PropertyEditorRegistry getEditorRegistry() {
		return (PropertyEditorRegistry)editorFactory;
	}

	public void setRendererFactory(PropertyRendererFactory factory) {
		rendererFactory = factory;
	}

	public PropertyRendererFactory getRendererFactory() {
		return rendererFactory;
	}

	/**
	 * @deprecated Method setRendererRegistry is deprecated
	 */

	public void setRendererRegistry(PropertyRendererRegistry registry) {
		setRendererFactory(registry);
	}

	/**
	 * @deprecated Method getRendererRegistry is deprecated
	 */

	public PropertyRendererRegistry getRendererRegistry() {
		return (PropertyRendererRegistry)getRendererFactory();
	}

	public boolean isCellEditable(int row, int column) {
		if (column == 0) {
			return false;
		} else {
			PropertySheetTableModel.Item item = getSheetModel().getPropertySheetElement(row);
			return item.isProperty() && item.getProperty().isEditable();
		}
	}

	public TableCellEditor getCellEditor(int row, int column) {
		if (column == 0)
			return null;
		PropertySheetTableModel.Item item = getSheetModel().getPropertySheetElement(row);
		if (!item.isProperty())
			return null;
		TableCellEditor result = null;
		Property propery = item.getProperty();
		java.beans.PropertyEditor editor = getEditorFactory().createPropertyEditor(propery);
		if (editor != null)
			result = new CellEditorAdapter(editor);
		return result;
	}

	public TableCellRenderer getCellRenderer(int row, int column) {
		PropertySheetTableModel.Item item = getSheetModel().getPropertySheetElement(row);
		switch (column) {
		case 0: // '\0'
			return nameRenderer;

		case 1: // '\001'
			if (!item.isProperty())
				return nameRenderer;
			Property property = item.getProperty();
			TableCellRenderer renderer = getRendererFactory().createTableCellRenderer(property);
			if (renderer == null)
				renderer = getCellRenderer(property.getType());
			return renderer;
		}
		return super.getCellRenderer(row, column);
	}

	private TableCellRenderer getCellRenderer(Class type) {
		TableCellRenderer renderer = getRendererFactory().createTableCellRenderer(type);
		if (renderer == null && type != null)
			renderer = getCellRenderer(type.getSuperclass());
		if (renderer == null)
			renderer = super.getDefaultRenderer(java.lang.Object.class);
		return renderer;
	}

	public final PropertySheetTableModel getSheetModel() {
		return (PropertySheetTableModel)getModel();
	}

	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		Object value = getValueAt(row, column);
		boolean isSelected = isCellSelected(row, column);
		Component component = renderer.getTableCellRendererComponent(this, value, isSelected, false, row, column);
		PropertySheetTableModel.Item item = getSheetModel().getPropertySheetElement(row);
		if (item.isProperty())
			component.setEnabled(item.getProperty().isEditable());
		return component;
	}

	public void setModel(TableModel newModel) {
		if (!(newModel instanceof PropertySheetTableModel))
			throw new IllegalArgumentException("dataModel must be of type " + (com.l2fprod.common.propertysheet.PropertySheetTableModel.class).getName());
		if (cancelEditing == null)
			cancelEditing = new CancelEditing();
		TableModel oldModel = getModel();
		if (oldModel != null)
			oldModel.removeTableModelListener(cancelEditing);
		super.setModel(newModel);
		newModel.addTableModelListener(cancelEditing);
		getColumnModel().getColumn(1).setResizable(false);
	}

	public boolean getWantsExtraIndent() {
		return wantsExtraIndent;
	}

	public void setWantsExtraIndent(boolean wantsExtraIndent) {
		this.wantsExtraIndent = wantsExtraIndent;
		repaint();
	}

	public boolean getScrollableTracksViewportHeight() {
		return getPreferredSize().height < getParent().getHeight();
	}

	public void commitEditing() {
		TableCellEditor editor = getCellEditor();
		if (editor != null)
			editor.stopCellEditing();
	}

	public void cancelEditing() {
		TableCellEditor editor = getCellEditor();
		if (editor != null)
			editor.cancelCellEditing();
	}

	static int getIndent(PropertySheetTable table, PropertySheetTableModel.Item item) {
		int indent = 0;
		if (item.isProperty()) {
			if ((item.getParent() == null || !item.getParent().isProperty()) && !item.hasToggle())
				indent = table.getWantsExtraIndent() ? 18 : 0;
			else
			if (item.hasToggle())
				indent = item.getDepth() * 18;
			else
				indent = (item.getDepth() + 1) * 18;
			if (table.getSheetModel().getMode() == 1 && table.getWantsExtraIndent())
				indent += 18;
		} else {
			indent = 0;
		}
		return indent;
	}








}
