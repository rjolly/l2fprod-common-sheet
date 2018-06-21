// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   AbstractProperty.java

package com.l2fprod.common.propertysheet;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;

// Referenced classes of package com.l2fprod.common.propertysheet:
//			Property

public abstract class AbstractProperty
	implements Property {

	private Object value;
	private transient PropertyChangeSupport listeners;

	public AbstractProperty() {
		listeners = new PropertyChangeSupport(this);
	}

	public Object getValue() {
		return value;
	}

	public Object clone() {
		try {
			AbstractProperty clone = null;
			clone = (AbstractProperty)super.clone();
			return clone;
		} catch (final CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	public void setValue(Object value) {
		Object oldValue = this.value;
		this.value = value;
		if (value != oldValue && (value == null || !value.equals(oldValue)))
			firePropertyChange(oldValue, getValue());
	}

	protected void initializeValue(Object value) {
		this.value = value;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
		Property subProperties[] = getSubProperties();
		if (subProperties != null) {
			for (int i = 0; i < subProperties.length; i++)
				subProperties[i].addPropertyChangeListener(listener);

		}
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
		Property subProperties[] = getSubProperties();
		if (subProperties != null) {
			for (int i = 0; i < subProperties.length; i++)
				subProperties[i].removePropertyChangeListener(listener);

		}
	}

	protected void firePropertyChange(Object oldValue, Object newValue) {
		listeners.firePropertyChange("value", oldValue, newValue);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		listeners = new PropertyChangeSupport(this);
	}

	public Property getParentProperty() {
		return null;
	}

	public Property[] getSubProperties() {
		return null;
	}
}
