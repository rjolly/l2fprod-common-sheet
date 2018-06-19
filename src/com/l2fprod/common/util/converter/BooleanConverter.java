// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   BooleanConverter.java

package com.l2fprod.common.util.converter;


// Referenced classes of package com.l2fprod.common.util.converter:
//			Converter, ConverterRegistry

public class BooleanConverter
	implements Converter {

	public BooleanConverter() {
	}

	public void register(ConverterRegistry registry) {
		registry.addConverter(java.lang.String.class, Boolean.TYPE, this);
		registry.addConverter(java.lang.String.class, java.lang.Boolean.class, this);
		registry.addConverter(java.lang.Boolean.class, java.lang.String.class, this);
		registry.addConverter(Boolean.TYPE, java.lang.String.class, this);
	}

	public Object convert(Class type, Object value) {
		if ((java.lang.String.class).equals(type) && (java.lang.Boolean.class).equals(value.getClass()))
			return String.valueOf(value);
		if (Boolean.TYPE.equals(type) || (java.lang.Boolean.class).equals(type))
			return Boolean.valueOf(String.valueOf(value));
		else
			throw new IllegalArgumentException("Can't convert " + value + " to " + type.getName());
	}
}
