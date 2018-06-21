// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   PropertySheetTableModel.java

package com.l2fprod.common.propertysheet;

import com.l2fprod.common.swing.ObjectTableModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

// Referenced classes of package com.l2fprod.common.propertysheet:
//			Property, PropertySheet

public class PropertySheetTableModel extends AbstractTableModel
	implements PropertyChangeListener, PropertySheet, ObjectTableModel {
	public static class NaturalOrderStringComparator
		implements Comparator {

		public int compare(Object o1, Object o2) {
			String s1 = (String)o1;
			String s2 = (String)o2;
			if (s1 == null)
				return s2 != null ? -1 : 0;
			if (s2 == null)
				return 1;
			else
				return s1.compareTo(s2);
		}

		public NaturalOrderStringComparator() {
		}
	}

	public static class PropertyComparator
		implements Comparator {

		public int compare(Object o1, Object o2) {
			if ((o1 instanceof Property) && (o2 instanceof Property)) {
				Property prop1 = (Property)o1;
				Property prop2 = (Property)o2;
				if (prop1 == null)
					return prop2 != null ? -1 : 0;
				else
					return PropertySheetTableModel.STRING_COMPARATOR.compare(prop1.getDisplayName() != null ? ((Object) (prop1.getDisplayName().toLowerCase())) : null, prop2.getDisplayName() != null ? ((Object) (prop2.getDisplayName().toLowerCase())) : null);
			} else {
				return 0;
			}
		}

		public PropertyComparator() {
		}
	}

	public class Item {

		private String name;
		private Property property;
		private Item parent;
		private boolean hasToggle;
		private boolean visible;

		public String getName() {
			return name;
		}

		public boolean isProperty() {
			return property != null;
		}

		public Property getProperty() {
			return property;
		}

		public Item getParent() {
			return parent;
		}

		public int getDepth() {
			int depth = 0;
			if (parent != null) {
				depth = parent.getDepth();
				if (parent.isProperty())
					depth++;
			}
			return depth;
		}

		public boolean hasToggle() {
			return hasToggle;
		}

		public void toggle() {
			if (hasToggle()) {
				visible = !visible;
				visibilityChanged(false);
				fireTableDataChanged();
			}
		}

		public void setVisible(boolean visible) {
			this.visible = visible;
		}

		public boolean isVisible() {
			return (parent == null || parent.isVisible()) && (!hasToggle || visible);
		}

		public String getKey() {
			StringBuffer key = new StringBuffer(name);
			for (Item itemParent = parent; itemParent != null; itemParent = itemParent.getParent()) {
				key.append(":");
				key.append(itemParent.getName());
			}

			return key.toString();
		}

		private Item(String name, Item parent) {
			hasToggle = true;
			visible = true;
			this.name = name;
			this.parent = parent;
			hasToggle = true;
		}

		private Item(Property property, Item parent) {
			hasToggle = true;
			visible = true;
			name = property.getDisplayName();
			this.property = property;
			this.parent = parent;
			visible = property == null;
			Property subProperties[] = property.getSubProperties();
			hasToggle = subProperties != null && subProperties.length > 0;
		}


	}


	public static final int NAME_COLUMN = 0;
	public static final int VALUE_COLUMN = 1;
	public static final int NUM_COLUMNS = 2;
	private PropertyChangeSupport listeners;
	private List model;
	private List publishedModel;
	private List properties;
	private int mode;
	private boolean sortingCategories;
	private boolean sortingProperties;
	private boolean restoreToggleStates;
	private Comparator categorySortingComparator;
	private Comparator propertySortingComparator;
	private Map toggleStates;
	private static final Comparator STRING_COMPARATOR = new NaturalOrderStringComparator();

	public PropertySheetTableModel() {
		listeners = new PropertyChangeSupport(this);
		model = new ArrayList();
		publishedModel = new ArrayList();
		properties = new ArrayList();
		mode = 0;
		sortingCategories = false;
		sortingProperties = false;
		restoreToggleStates = false;
		toggleStates = new HashMap();
	}

	public void setProperties(Property newProperties[]) {
		Property prop;
		for (Iterator iter = properties.iterator(); iter.hasNext(); prop.removePropertyChangeListener(this))
			prop = (Property)iter.next();

		properties.clear();
		properties.addAll(Arrays.asList(newProperties));
		for (Iterator iter = properties.iterator(); iter.hasNext(); prop.addPropertyChangeListener(this))
			prop = (Property)iter.next();

		buildModel();
	}

	public Property[] getProperties() {
		return (Property[])properties.toArray(new Property[properties.size()]);
	}

	public void addProperty(Property property) {
		properties.add(property);
		property.addPropertyChangeListener(this);
		buildModel();
	}

	public void addProperty(int index, Property property) {
		properties.add(index, property);
		property.addPropertyChangeListener(this);
		buildModel();
	}

	public void removeProperty(Property property) {
		properties.remove(property);
		property.removePropertyChangeListener(this);
		buildModel();
	}

	public int getPropertyCount() {
		return properties.size();
	}

	public Iterator propertyIterator() {
		return properties.iterator();
	}

	public void setMode(int mode) {
		if (this.mode == mode) {
			return;
		} else {
			this.mode = mode;
			buildModel();
			return;
		}
	}

	public int getMode() {
		return mode;
	}

	public Class getColumnClass(int columnIndex) {
		return super.getColumnClass(columnIndex);
	}

	public int getColumnCount() {
		return 2;
	}

	public int getRowCount() {
		return publishedModel.size();
	}

	public Object getObject(int rowIndex) {
		return getPropertySheetElement(rowIndex);
	}

	public Item getPropertySheetElement(int rowIndex) {
		return (Item)publishedModel.get(rowIndex);
	}

	public boolean isSortingCategories() {
		return sortingCategories;
	}

	public void setSortingCategories(boolean value) {
		boolean old = sortingCategories;
		sortingCategories = value;
		if (sortingCategories != old)
			buildModel();
	}

	public boolean isSortingProperties() {
		return sortingProperties;
	}

	public void setSortingProperties(boolean value) {
		boolean old = sortingProperties;
		sortingProperties = value;
		if (sortingProperties != old)
			buildModel();
	}

	public void setCategorySortingComparator(Comparator comp) {
		Comparator old = categorySortingComparator;
		categorySortingComparator = comp;
		if (categorySortingComparator != old)
			buildModel();
	}

	public void setPropertySortingComparator(Comparator comp) {
		Comparator old = propertySortingComparator;
		propertySortingComparator = comp;
		if (propertySortingComparator != old)
			buildModel();
	}

	public void setRestoreToggleStates(boolean value) {
		restoreToggleStates = value;
		if (!restoreToggleStates)
			toggleStates.clear();
	}

	public boolean isRestoreToggleStates() {
		return restoreToggleStates;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Object result = null;
		Item item = getPropertySheetElement(rowIndex);
		if (item.isProperty())
			switch (columnIndex) {
			case 0: // '\0'
				result = item;
				break;

			case 1: // '\001'
				try {
					result = item.getProperty().getValue();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		else
			result = item;
		return result;
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		Item item = getPropertySheetElement(rowIndex);
		if (item.isProperty() && columnIndex == 1)
			try {
				item.getProperty().setValue(value);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		listeners.firePropertyChange(evt);
	}

	protected void visibilityChanged(boolean restoreOldStates) {
		Iterator iter;
		if (restoreOldStates) {
			Item item;
			for (iter = publishedModel.iterator(); iter.hasNext(); toggleStates.put(item.getKey(), item.isVisible() ? ((Object) (Boolean.TRUE)) : ((Object) (Boolean.FALSE))))
				item = (Item)iter.next();

		}
		publishedModel.clear();
		iter = model.iterator();
		do {
			if (!iter.hasNext())
				break;
			Item item = (Item)iter.next();
			Item parent = item.getParent();
			if (restoreOldStates) {
				Boolean oldState = (Boolean)toggleStates.get(item.getKey());
				if (oldState != null)
					item.setVisible(oldState.booleanValue());
				if (parent != null) {
					oldState = (Boolean)toggleStates.get(parent.getKey());
					if (oldState != null)
						parent.setVisible(oldState.booleanValue());
				}
			}
			if (parent == null || parent.isVisible())
				publishedModel.add(item);
		} while (true);
	}

	private void buildModel() {
		model.clear();
		if (properties != null && properties.size() > 0) {
			List sortedProperties = sortProperties(properties);
			switch (mode) {
			default:
				break;

			case 0: // '\0'
				addPropertiesToModel(sortedProperties, null);
				break;

			case 1: // '\001'
				List categories = sortCategories(getPropertyCategories(sortedProperties));
				String category;
				Item categoryItem;
				for (Iterator iter = categories.iterator(); iter.hasNext(); addPropertiesToModel(sortProperties(getPropertiesForCategory(properties, category)), categoryItem)) {
					category = (String)iter.next();
					categoryItem = new Item(category, null);
					model.add(categoryItem);
				}

				break;
			}
		}
		visibilityChanged(restoreToggleStates);
		fireTableDataChanged();
	}

	protected List sortProperties(List localProperties) {
		List sortedProperties = new ArrayList(localProperties);
		if (sortingProperties) {
			if (propertySortingComparator == null)
				propertySortingComparator = new PropertyComparator();
			Collections.sort(sortedProperties, propertySortingComparator);
		}
		return sortedProperties;
	}

	protected List sortCategories(List localCategories) {
		List sortedCategories = new ArrayList(localCategories);
		if (sortingCategories) {
			if (categorySortingComparator == null)
				categorySortingComparator = STRING_COMPARATOR;
			Collections.sort(sortedCategories, categorySortingComparator);
		}
		return sortedCategories;
	}

	protected List getPropertyCategories(List localProperties) {
		List categories = new ArrayList();
		Iterator iter = localProperties.iterator();
		do {
			if (!iter.hasNext())
				break;
			Property property = (Property)iter.next();
			if (!categories.contains(property.getCategory()))
				categories.add(property.getCategory());
		} while (true);
		return categories;
	}

	private void addPropertiesToModel(List localProperties, Item parent) {
		Iterator iter = localProperties.iterator();
		do {
			if (!iter.hasNext())
				break;
			Property property = (Property)iter.next();
			Item propertyItem = new Item(property, parent);
			model.add(propertyItem);
			Property subProperties[] = property.getSubProperties();
			if (subProperties != null && subProperties.length > 0)
				addPropertiesToModel(Arrays.asList(subProperties), propertyItem);
		} while (true);
	}

	private List getPropertiesForCategory(List localProperties, String category) {
		List categoryProperties = new ArrayList();
		Iterator iter = localProperties.iterator();
		do {
			if (!iter.hasNext())
				break;
			Property property = (Property)iter.next();
			if (category == property.getCategory() || category != null && category.equals(property.getCategory()))
				categoryProperties.add(property);
		} while (true);
		return categoryProperties;
	}


}
