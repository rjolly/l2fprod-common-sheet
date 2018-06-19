// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   ConverterRegistry.java

package com.l2fprod.common.util.converter;

import java.util.HashMap;
import java.util.Map;

// Referenced classes of package com.l2fprod.common.util.converter:
//			BooleanConverter, AWTConverters, NumberConverters, Converter

public class ConverterRegistry
	implements Converter {

	private static ConverterRegistry sharedInstance = new ConverterRegistry();
	private Map fromMap;

	public ConverterRegistry() {
		fromMap = new HashMap();
		(new BooleanConverter()).register(this);
		(new AWTConverters()).register(this);
		(new NumberConverters()).register(this);
	}

	public void addConverter(Class from, Class to, Converter converter) {
		Map toMap = (Map)fromMap.get(from);
		if (toMap == null) {
			toMap = new HashMap();
			fromMap.put(from, toMap);
		}
		toMap.put(to, converter);
	}

	public Converter getConverter(Class from, Class to) {
		Map toMap = (Map)fromMap.get(from);
		if (toMap != null)
			return (Converter)toMap.get(to);
		else
			return null;
	}

	public Object convert(Class targetType, Object value) {
		if (value == null)
			return null;
		Converter converter = getConverter(value.getClass(), targetType);
		if (converter == null)
			throw new IllegalArgumentException("No converter from " + value.getClass() + " to " + targetType.getName());
		else
			return converter.convert(targetType, value);
	}

	public static ConverterRegistry instance() {
		return sharedInstance;
	}

}
