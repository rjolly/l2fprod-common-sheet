// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   DefaultBeanDescriptor.java

package com.l2fprod.common.beans;

import com.l2fprod.common.util.ResourceManager;
import java.beans.BeanDescriptor;
import java.util.MissingResourceException;

// Referenced classes of package com.l2fprod.common.beans:
//			BaseBeanInfo

final class DefaultBeanDescriptor extends BeanDescriptor {

	private String displayName;

	public DefaultBeanDescriptor(BaseBeanInfo beanInfo) {
		super(beanInfo.getType());
		try {
			setDisplayName(beanInfo.getResources().getString("beanName"));
		}
		catch (MissingResourceException e) { }
		try {
			setShortDescription(beanInfo.getResources().getString("beanDescription"));
		}
		catch (MissingResourceException e) { }
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String p_name) {
		displayName = p_name;
	}
}
