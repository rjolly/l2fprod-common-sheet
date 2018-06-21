// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   PropertyDescriptorAdapter.java

package com.l2fprod.common.propertysheet;

import com.l2fprod.common.beans.ExtendedPropertyDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

// Referenced classes of package com.l2fprod.common.propertysheet:
//			AbstractProperty

class PropertyDescriptorAdapter extends AbstractProperty {

	private PropertyDescriptor descriptor;

	public PropertyDescriptorAdapter() {
	}

	public PropertyDescriptorAdapter(PropertyDescriptor descriptor) {
		this();
		setDescriptor(descriptor);
	}

	public void setDescriptor(PropertyDescriptor descriptor) {
		this.descriptor = descriptor;
	}

	public PropertyDescriptor getDescriptor() {
		return descriptor;
	}

	public String getName() {
		return descriptor.getName();
	}

	public String getDisplayName() {
		return descriptor.getDisplayName();
	}

	public String getShortDescription() {
		return descriptor.getShortDescription();
	}

	public Class getType() {
		return descriptor.getPropertyType();
	}

	public Object clone() {
		PropertyDescriptorAdapter clone = new PropertyDescriptorAdapter(descriptor);
		clone.setValue(getValue());
		return clone;
	}

	public void readFromObject(Object object) {
		try {
			Method method = descriptor.getReadMethod();
			if (method != null)
				setValue(method.invoke(object));
		}
		catch (Exception e) {
			String message = "Got exception when reading property " + getName();
			if (object == null)
				message = message + ", object was 'null'";
			else
				message = message + ", object was " + String.valueOf(object);
			throw new RuntimeException(message, e);
		}
	}

	public void writeToObject(Object object) {
		try {
			Method method = descriptor.getWriteMethod();
			if (method != null)
				method.invoke(object, new Object[] {
					getValue()
				});
		}
		catch (Exception e) {
			if ((e instanceof InvocationTargetException) && (((InvocationTargetException)e).getTargetException() instanceof PropertyVetoException))
				throw new RuntimeException(((InvocationTargetException)e).getTargetException());
			String message = "Got exception when writing property " + getName();
			if (object == null)
				message = message + ", object was 'null'";
			else
				message = message + ", object was " + String.valueOf(object);
			throw new RuntimeException(message, e);
		}
	}

	public boolean isEditable() {
		return descriptor.getWriteMethod() != null;
	}

	public String getCategory() {
		if (descriptor instanceof ExtendedPropertyDescriptor)
			return ((ExtendedPropertyDescriptor)descriptor).getCategory();
		else
			return null;
	}
}
