// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   ResourceManager.java

package com.l2fprod.common.util;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ResourceManager {

	static Map nameToRM = new HashMap();
	private ResourceBundle bundle;

	public static ResourceManager get(Class clazz) {
		String bundleName = clazz.getName() + "RB";
		return get(bundleName);
	}

	public static ResourceManager get(String bundleName) {
		ResourceManager rm = (ResourceManager)nameToRM.get(bundleName);
		if (rm == null) {
			ResourceBundle rb = ResourceBundle.getBundle(bundleName);
			rm = new ResourceManager(rb);
			nameToRM.put(bundleName, rm);
		}
		return rm;
	}

	public static ResourceManager all(Class clazz) {
		return get(getPackage(clazz) + ".AllRB");
	}

	public static ResourceManager common() {
		return all(com.l2fprod.common.util.ResourceManager.class);
	}

	public static String resolve(String rbAndProperty) {
		return common().resolve0(rbAndProperty);
	}

	public static String resolve(String rbAndProperty, Object args[]) {
		String value = common().resolve0(rbAndProperty);
		return MessageFormat.format(value, args);
	}

	private ResourceManager(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	public String getString(String key) {
		return resolve0(String.valueOf(bundle.getObject(key)));
	}

	public String getString(String key, Object args[]) {
		String value = getString(key);
		return MessageFormat.format(value, args);
	}

	public char getChar(String key) {
		String s = getString(key);
		if (s == null || s.trim().length() == 0)
			return '\0';
		else
			return s.charAt(0);
	}

	private String resolve0(String property) {
		String result = property;
		if (property != null) {
			int index = property.indexOf("${");
			if (index != -1) {
				int endIndex = property.indexOf("}", index);
				String sub = property.substring(index + 2, endIndex);
				int colon = sub.indexOf(":");
				if (colon != -1) {
					String rbName = sub.substring(0, colon);
					String keyName = sub.substring(colon + 1);
					sub = get(rbName).getString(keyName);
				} else {
					sub = getString(sub);
				}
				result = property.substring(0, index) + sub + resolve0(property.substring(endIndex + 1));
			}
		}
		return result;
	}

	private static String getPackage(Class clazz) {
		String pck = clazz.getName();
		int index = pck.lastIndexOf('.');
		if (index != -1)
			pck = pck.substring(0, index);
		else
			pck = "";
		return pck;
	}

}
