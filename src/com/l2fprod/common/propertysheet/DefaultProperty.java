// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   DefaultProperty.java

package com.l2fprod.common.propertysheet;

import com.l2fprod.common.beans.BeanUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

// Referenced classes of package com.l2fprod.common.propertysheet:
//			AbstractProperty, Property

public class DefaultProperty extends AbstractProperty {

	private String name;
	private String displayName;
	private String shortDescription;
	private Class type;
	private boolean editable;
	private String category;
	private Property parent;
	private List subProperties;

	public DefaultProperty() {
		editable = true;
		subProperties = new ArrayList();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public Class getType() {
		return type;
	}

	public void setType(Class type) {
		this.type = type;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void readFromObject(Object object) {
		try {
			Method method = BeanUtils.getReadMethod(object.getClass(), getName());
			if (method != null) {
				Object value = method.invoke(object, null);
				initializeValue(value);
				if (value != null) {
					Property subProperty;
					for (Iterator iter = subProperties.iterator(); iter.hasNext(); subProperty.readFromObject(value))
						subProperty = (Property)iter.next();

				}
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void writeToObject(Object object) {
		try {
			Method method = BeanUtils.getWriteMethod(object.getClass(), getName(), getType());
			if (method != null)
				method.invoke(object, new Object[] {
					getValue()
				});
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setValue(Object value) {
		super.setValue(value);
		if (parent != null) {
			Object parentValue = parent.getValue();
			if (parentValue != null) {
				writeToObject(parentValue);
				parent.setValue(parentValue);
			}
		}
		if (value != null) {
			Property subProperty;
			for (Iterator iter = subProperties.iterator(); iter.hasNext(); subProperty.readFromObject(value))
				subProperty = (Property)iter.next();

		}
	}

	public int hashCode() {
		return 28 + (name == null ? 3 : name.hashCode()) + (displayName == null ? 94 : displayName.hashCode()) + (shortDescription == null ? '\u018A' : shortDescription.hashCode()) + (category == null ? 34 : category.hashCode()) + (type == null ? 39 : type.hashCode()) + Boolean.valueOf(editable).hashCode();
	}

	public boolean equals(Object other) {
		if (other == null || getClass() != other.getClass())
			return false;
		if (other == this) {
			return true;
		} else {
			DefaultProperty dp = (DefaultProperty)other;
			return compare(name, dp.name) && compare(displayName, dp.displayName) && compare(shortDescription, dp.shortDescription) && compare(category, dp.category) && compare(type, dp.type) && editable == dp.editable;
		}
	}

	private boolean compare(Object o1, Object o2) {
		return o1 == null ? o2 == null : o1.equals(o2);
	}

	public String toString() {
		return "name=" + getName() + ", displayName=" + getDisplayName() + ", type=" + getType() + ", category=" + getCategory() + ", editable=" + isEditable() + ", value=" + getValue();
	}

	public Property getParentProperty() {
		return parent;
	}

	public void setParentProperty(Property parent) {
		this.parent = parent;
	}

	public Property[] getSubProperties() {
		return (Property[])subProperties.toArray(new Property[subProperties.size()]);
	}

	public void clearSubProperties() {
		Iterator iter = subProperties.iterator();
		do {
			if (!iter.hasNext())
				break;
			Property subProp = (Property)iter.next();
			if (subProp instanceof DefaultProperty)
				((DefaultProperty)subProp).setParentProperty(null);
		} while (true);
		subProperties.clear();
	}

	public void addSubProperties(Collection subProperties) {
		this.subProperties.addAll(subProperties);
		Iterator iter = this.subProperties.iterator();
		do {
			if (!iter.hasNext())
				break;
			Property subProp = (Property)iter.next();
			if (subProp instanceof DefaultProperty)
				((DefaultProperty)subProp).setParentProperty(this);
		} while (true);
	}

	public void addSubProperties(Property subProperties[]) {
		addSubProperties(((Collection) (Arrays.asList(subProperties))));
	}

	public void addSubProperty(Property subProperty) {
		subProperties.add(subProperty);
		if (subProperty instanceof DefaultProperty)
			((DefaultProperty)subProperty).setParentProperty(this);
	}
}
