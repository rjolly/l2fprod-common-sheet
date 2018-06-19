// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   PropertySheetPanel.java

package com.l2fprod.common.propertysheet;

import com.l2fprod.common.swing.IconPool;
import com.l2fprod.common.swing.LookAndFeelTweaks;
import com.l2fprod.common.swing.plaf.blue.BlueishButtonUI;
import com.l2fprod.common.util.ResourceManager;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.beans.BeanInfo;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.util.Comparator;
import java.util.Iterator;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

// Referenced classes of package com.l2fprod.common.propertysheet:
//			PropertySheetTable, PropertySheetTableModel, Property, PropertyDescriptorAdapter, 
//			PropertyEditorRegistry, PropertySheet, PropertyEditorFactory, PropertyRendererFactory, 
//			PropertyRendererRegistry

public class PropertySheetPanel extends JPanel
	implements PropertySheet, PropertyChangeListener {
	class ToggleSortingAction extends AbstractAction {

		public void actionPerformed(ActionEvent e) {
			setSorting(sortButton.isSelected());
		}

		public ToggleSortingAction() {
			super("toggleSorting", IconPool.shared().get((PropertySheetPanel.class$com$l2fprod$common$propertysheet$PropertySheet != null ? PropertySheetPanel.class$com$l2fprod$common$propertysheet$PropertySheet : (PropertySheetPanel.class$com$l2fprod$common$propertysheet$PropertySheet = PropertySheetPanel._mthclass$("com.l2fprod.common.propertysheet.PropertySheet"))).getResource("icons/sort.gif")));
			putValue("ShortDescription", ResourceManager.get(PropertySheetPanel.class$com$l2fprod$common$propertysheet$PropertySheet != null ? PropertySheetPanel.class$com$l2fprod$common$propertysheet$PropertySheet : (PropertySheetPanel.class$com$l2fprod$common$propertysheet$PropertySheet = PropertySheetPanel._mthclass$("com.l2fprod.common.propertysheet.PropertySheet"))).getString("PropertySheetPanel.sort.shortDescription"));
		}
	}

	class ToggleDescriptionAction extends AbstractAction {

		public void actionPerformed(ActionEvent e) {
			setDescriptionVisible(descriptionButton.isSelected());
		}

		public ToggleDescriptionAction() {
			super("toggleDescription", IconPool.shared().get((PropertySheetPanel.class$com$l2fprod$common$propertysheet$PropertySheet != null ? PropertySheetPanel.class$com$l2fprod$common$propertysheet$PropertySheet : (PropertySheetPanel.class$com$l2fprod$common$propertysheet$PropertySheet = PropertySheetPanel._mthclass$("com.l2fprod.common.propertysheet.PropertySheet"))).getResource("icons/description.gif")));
			putValue("ShortDescription", ResourceManager.get(PropertySheetPanel.class$com$l2fprod$common$propertysheet$PropertySheet != null ? PropertySheetPanel.class$com$l2fprod$common$propertysheet$PropertySheet : (PropertySheetPanel.class$com$l2fprod$common$propertysheet$PropertySheet = PropertySheetPanel._mthclass$("com.l2fprod.common.propertysheet.PropertySheet"))).getString("PropertySheetPanel.description.shortDescription"));
		}
	}

	class ToggleModeAction extends AbstractAction {

		public void actionPerformed(ActionEvent e) {
			if (asCategoryButton.isSelected())
				model.setMode(1);
			else
				model.setMode(0);
		}

		public ToggleModeAction() {
			super("toggle", IconPool.shared().get((PropertySheetPanel.class$com$l2fprod$common$propertysheet$PropertySheet != null ? PropertySheetPanel.class$com$l2fprod$common$propertysheet$PropertySheet : (PropertySheetPanel.class$com$l2fprod$common$propertysheet$PropertySheet = PropertySheetPanel._mthclass$("com.l2fprod.common.propertysheet.PropertySheet"))).getResource("icons/category.gif")));
			putValue("ShortDescription", ResourceManager.get(PropertySheetPanel.class$com$l2fprod$common$propertysheet$PropertySheet != null ? PropertySheetPanel.class$com$l2fprod$common$propertysheet$PropertySheet : (PropertySheetPanel.class$com$l2fprod$common$propertysheet$PropertySheet = PropertySheetPanel._mthclass$("com.l2fprod.common.propertysheet.PropertySheet"))).getString("PropertySheetPanel.category.shortDescription"));
		}
	}

	class SelectionListener
		implements ListSelectionListener {

		public void valueChanged(ListSelectionEvent e) {
			int row = table.getSelectedRow();
			Property prop = null;
			if (row >= 0 && table.getRowCount() > row)
				prop = model.getPropertySheetElement(row).getProperty();
			if (prop != null)
				descriptionPanel.setText("<html><b>" + (prop.getDisplayName() != null ? prop.getDisplayName() : "") + "</b><br>" + (prop.getShortDescription() != null ? prop.getShortDescription() : ""));
			else
				descriptionPanel.setText("<html>");
			descriptionPanel.setCaretPosition(0);
		}

		SelectionListener() {
		}
	}


	private PropertySheetTable table;
	private PropertySheetTableModel model;
	private JScrollPane tableScroll;
	private ListSelectionListener selectionListener;
	private JPanel actionPanel;
	private JToggleButton sortButton;
	private JToggleButton asCategoryButton;
	private JToggleButton descriptionButton;
	private JSplitPane split;
	private int lastDescriptionHeight;
	private JEditorPane descriptionPanel;
	private JScrollPane descriptionScrollPane;
	static Class class$com$l2fprod$common$propertysheet$PropertySheet; /* synthetic field */

	public PropertySheetPanel() {
		this(new PropertySheetTable());
	}

	public PropertySheetPanel(PropertySheetTable table) {
		selectionListener = new SelectionListener();
		buildUI();
		setTable(table);
	}

	public void setTable(PropertySheetTable table) {
		if (table == null)
			throw new IllegalArgumentException("table must not be null");
		if (model != null)
			model.removePropertyChangeListener(this);
		model = (PropertySheetTableModel)table.getModel();
		model.addPropertyChangeListener(this);
		if (this.table != null)
			this.table.getSelectionModel().removeListSelectionListener(selectionListener);
		table.getSelectionModel().addListSelectionListener(selectionListener);
		tableScroll.getViewport().setView(table);
		this.table = table;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		repaint();
	}

	public PropertySheetTable getTable() {
		return table;
	}

	public void setDescriptionVisible(boolean visible) {
		if (visible) {
			add("Center", split);
			split.setTopComponent(tableScroll);
			split.setBottomComponent(descriptionScrollPane);
			split.setDividerLocation(split.getHeight() - lastDescriptionHeight);
		} else {
			lastDescriptionHeight = split.getHeight() - split.getDividerLocation();
			remove(split);
			add("Center", tableScroll);
		}
		descriptionButton.setSelected(visible);
		revalidate();
	}

	public void setToolBarVisible(boolean visible) {
		actionPanel.setVisible(visible);
		revalidate();
	}

	public void setMode(int mode) {
		model.setMode(mode);
		asCategoryButton.setSelected(1 == mode);
	}

	public void setProperties(Property properties[]) {
		model.setProperties(properties);
	}

	public Property[] getProperties() {
		return model.getProperties();
	}

	public void addProperty(Property property) {
		model.addProperty(property);
	}

	public void addProperty(int index, Property property) {
		model.addProperty(index, property);
	}

	public void removeProperty(Property property) {
		model.removeProperty(property);
	}

	public int getPropertyCount() {
		return model.getPropertyCount();
	}

	public Iterator propertyIterator() {
		return model.propertyIterator();
	}

	public void setBeanInfo(BeanInfo beanInfo) {
		setProperties(beanInfo.getPropertyDescriptors());
	}

	public void setProperties(PropertyDescriptor descriptors[]) {
		Property properties[] = new Property[descriptors.length];
		int i = 0;
		for (int c = descriptors.length; i < c; i++)
			properties[i] = new PropertyDescriptorAdapter(descriptors[i]);

		setProperties(properties);
	}

	public void readFromObject(Object data) {
		getTable().cancelEditing();
		Property properties[] = model.getProperties();
		int i = 0;
		for (int c = properties.length; i < c; i++)
			properties[i].readFromObject(data);

		repaint();
	}

	public void writeToObject(Object data) {
		getTable().commitEditing();
		Property properties[] = getProperties();
		int i = 0;
		for (int c = properties.length; i < c; i++)
			properties[i].writeToObject(data);

	}

	public void addPropertySheetChangeListener(PropertyChangeListener listener) {
		model.addPropertyChangeListener(listener);
	}

	public void removePropertySheetChangeListener(PropertyChangeListener listener) {
		model.removePropertyChangeListener(listener);
	}

	public void setEditorFactory(PropertyEditorFactory factory) {
		table.setEditorFactory(factory);
	}

	public PropertyEditorFactory getEditorFactory() {
		return table.getEditorFactory();
	}

	/**
	 * @deprecated Method setEditorRegistry is deprecated
	 */

	public void setEditorRegistry(PropertyEditorRegistry registry) {
		table.setEditorFactory(registry);
	}

	/**
	 * @deprecated Method getEditorRegistry is deprecated
	 */

	public PropertyEditorRegistry getEditorRegistry() {
		return (PropertyEditorRegistry)table.getEditorFactory();
	}

	public void setRendererFactory(PropertyRendererFactory factory) {
		table.setRendererFactory(factory);
	}

	public PropertyRendererFactory getRendererFactory() {
		return table.getRendererFactory();
	}

	/**
	 * @deprecated Method setRendererRegistry is deprecated
	 */

	public void setRendererRegistry(PropertyRendererRegistry registry) {
		table.setRendererRegistry(registry);
	}

	/**
	 * @deprecated Method getRendererRegistry is deprecated
	 */

	public PropertyRendererRegistry getRendererRegistry() {
		return table.getRendererRegistry();
	}

	public void setSortingCategories(boolean value) {
		model.setSortingCategories(value);
		sortButton.setSelected(isSorting());
	}

	public boolean isSortingCategories() {
		return model.isSortingCategories();
	}

	public void setSortingProperties(boolean value) {
		model.setSortingProperties(value);
		sortButton.setSelected(isSorting());
	}

	public boolean isSortingProperties() {
		return model.isSortingProperties();
	}

	public void setSorting(boolean value) {
		model.setSortingCategories(value);
		model.setSortingProperties(value);
		sortButton.setSelected(value);
	}

	public boolean isSorting() {
		return model.isSortingCategories() || model.isSortingProperties();
	}

	public void setCategorySortingComparator(Comparator comp) {
		model.setCategorySortingComparator(comp);
	}

	public void setPropertySortingComparator(Comparator comp) {
		model.setPropertySortingComparator(comp);
	}

	public void setRestoreToggleStates(boolean value) {
		model.setRestoreToggleStates(value);
	}

	public boolean isRestoreToggleStates() {
		return model.isRestoreToggleStates();
	}

	private void buildUI() {
		LookAndFeelTweaks.setBorderLayout(this);
		LookAndFeelTweaks.setBorder(this);
		actionPanel = new JPanel(new FlowLayout(3, 2, 0));
		actionPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
		actionPanel.setOpaque(false);
		add("North", actionPanel);
		sortButton = new JToggleButton(new ToggleSortingAction());
		sortButton.setUI(new BlueishButtonUI());
		sortButton.setText(null);
		sortButton.setOpaque(false);
		actionPanel.add(sortButton);
		asCategoryButton = new JToggleButton(new ToggleModeAction());
		asCategoryButton.setUI(new BlueishButtonUI());
		asCategoryButton.setText(null);
		asCategoryButton.setOpaque(false);
		actionPanel.add(asCategoryButton);
		descriptionButton = new JToggleButton(new ToggleDescriptionAction());
		descriptionButton.setUI(new BlueishButtonUI());
		descriptionButton.setText(null);
		descriptionButton.setOpaque(false);
		actionPanel.add(descriptionButton);
		split = new JSplitPane(0);
		split.setBorder(null);
		split.setResizeWeight(1.0D);
		split.setContinuousLayout(true);
		add("Center", split);
		tableScroll = new JScrollPane();
		tableScroll.setBorder(BorderFactory.createEmptyBorder());
		split.setTopComponent(tableScroll);
		descriptionPanel = new JEditorPane("text/html", "<html>");
		descriptionPanel.setBorder(BorderFactory.createEmptyBorder());
		descriptionPanel.setEditable(false);
		descriptionPanel.setBackground(UIManager.getColor("Panel.background"));
		LookAndFeelTweaks.htmlize(descriptionPanel);
		selectionListener = new SelectionListener();
		descriptionScrollPane = new JScrollPane(descriptionPanel);
		descriptionScrollPane.setBorder(LookAndFeelTweaks.addMargin(BorderFactory.createLineBorder(UIManager.getColor("controlDkShadow"))));
		descriptionScrollPane.getViewport().setBackground(descriptionPanel.getBackground());
		descriptionScrollPane.setMinimumSize(new Dimension(50, 50));
		split.setBottomComponent(descriptionScrollPane);
		setDescriptionVisible(false);
		setToolBarVisible(true);
	}

	static Class _mthclass$(String x0) {
		return Class.forName(x0);
		ClassNotFoundException x1;
		x1;
		throw new NoClassDefFoundError(x1.getMessage());
	}






}
