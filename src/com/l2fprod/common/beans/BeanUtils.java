// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   BeanUtils.java

package com.l2fprod.common.beans;

import java.lang.reflect.Method;

public class BeanUtils {

	private BeanUtils() {
	}

	public static Method getReadMethod(Class clazz, String propertyName) {
		Method readMethod = null;
		String base = capitalize(propertyName);
		try {
			readMethod = clazz.getMethod("is" + base);
		}
		catch (Exception getterExc) {
			try {
				readMethod = clazz.getMethod("get" + base);
			}
			catch (Exception e) { }
		}
		return readMethod;
	}

	public static Method getWriteMethod(Class clazz, String propertyName, Class propertyType) {
		Method writeMethod = null;
		String base = capitalize(propertyName);
		Class params[] = {
			propertyType
		};
		try {
			writeMethod = clazz.getMethod("set" + base, params);
		}
		catch (Exception e) { }
		return writeMethod;
	}

	private static String capitalize(String s) {
		if (s.length() == 0) {
			return s;
		} else {
			char chars[] = s.toCharArray();
			chars[0] = Character.toUpperCase(chars[0]);
			return String.valueOf(chars);
		}
	}
}
