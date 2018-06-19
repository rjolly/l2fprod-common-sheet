// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   NumberConverters.java

package com.l2fprod.common.util.converter;

import java.text.NumberFormat;

// Referenced classes of package com.l2fprod.common.util.converter:
//			Converter, ConverterRegistry

public class NumberConverters
	implements Converter {

	private static NumberFormat defaultFormat;
	private NumberFormat format;

	public NumberConverters() {
		this(getDefaultFormat());
	}

	public NumberConverters(NumberFormat format) {
		this.format = format;
	}

	public static NumberFormat getDefaultFormat() {
		synchronized (com.l2fprod.common.util.converter.NumberConverters.class) {
			if (defaultFormat == null) {
				defaultFormat = NumberFormat.getNumberInstance();
				defaultFormat.setMinimumIntegerDigits(1);
				defaultFormat.setMaximumIntegerDigits(64);
				defaultFormat.setMinimumFractionDigits(0);
				defaultFormat.setMaximumFractionDigits(64);
			}
		}
		return defaultFormat;
	}

	public void register(ConverterRegistry registry) {
		registry.addConverter(java.lang.Number.class, java.lang.Double.class, this);
		registry.addConverter(java.lang.Number.class, java.lang.Float.class, this);
		registry.addConverter(java.lang.Number.class, java.lang.Integer.class, this);
		registry.addConverter(java.lang.Number.class, java.lang.Long.class, this);
		registry.addConverter(java.lang.Number.class, java.lang.Short.class, this);
		registry.addConverter(java.lang.Double.class, java.lang.Double.class, this);
		registry.addConverter(java.lang.Double.class, java.lang.Float.class, this);
		registry.addConverter(java.lang.Double.class, java.lang.Integer.class, this);
		registry.addConverter(java.lang.Double.class, java.lang.Long.class, this);
		registry.addConverter(java.lang.Double.class, java.lang.Short.class, this);
		registry.addConverter(java.lang.Double.class, java.lang.String.class, this);
		registry.addConverter(java.lang.Float.class, java.lang.Double.class, this);
		registry.addConverter(java.lang.Float.class, java.lang.Float.class, this);
		registry.addConverter(java.lang.Float.class, java.lang.Integer.class, this);
		registry.addConverter(java.lang.Float.class, java.lang.Long.class, this);
		registry.addConverter(java.lang.Float.class, java.lang.Short.class, this);
		registry.addConverter(java.lang.Float.class, java.lang.String.class, this);
		registry.addConverter(java.lang.Integer.class, java.lang.Double.class, this);
		registry.addConverter(java.lang.Integer.class, java.lang.Float.class, this);
		registry.addConverter(java.lang.Integer.class, java.lang.Integer.class, this);
		registry.addConverter(java.lang.Integer.class, java.lang.Long.class, this);
		registry.addConverter(java.lang.Integer.class, java.lang.Short.class, this);
		registry.addConverter(java.lang.Integer.class, java.lang.String.class, this);
		registry.addConverter(java.lang.Long.class, java.lang.Double.class, this);
		registry.addConverter(java.lang.Long.class, java.lang.Float.class, this);
		registry.addConverter(java.lang.Long.class, java.lang.Integer.class, this);
		registry.addConverter(java.lang.Long.class, java.lang.Long.class, this);
		registry.addConverter(java.lang.Long.class, java.lang.Short.class, this);
		registry.addConverter(java.lang.Long.class, java.lang.String.class, this);
		registry.addConverter(java.lang.Short.class, java.lang.Double.class, this);
		registry.addConverter(java.lang.Short.class, java.lang.Float.class, this);
		registry.addConverter(java.lang.Short.class, java.lang.Integer.class, this);
		registry.addConverter(java.lang.Short.class, java.lang.Long.class, this);
		registry.addConverter(java.lang.Short.class, java.lang.Short.class, this);
		registry.addConverter(java.lang.Short.class, java.lang.String.class, this);
		registry.addConverter(java.lang.String.class, java.lang.Double.class, this);
		registry.addConverter(java.lang.String.class, java.lang.Float.class, this);
		registry.addConverter(java.lang.String.class, java.lang.Integer.class, this);
		registry.addConverter(java.lang.String.class, java.lang.Long.class, this);
		registry.addConverter(java.lang.String.class, java.lang.Short.class, this);
	}

	public Object convert(Class targetType, Object value) {
		if ((value instanceof Number) && (java.lang.Number.class).isAssignableFrom(targetType)) {
			if ((java.lang.Double.class).equals(targetType))
				return new Double(((Number)value).doubleValue());
			if ((java.lang.Float.class).equals(targetType))
				return new Float(((Number)value).floatValue());
			if ((java.lang.Integer.class).equals(targetType))
				return new Integer(((Number)value).intValue());
			if ((java.lang.Long.class).equals(targetType))
				return new Long(((Number)value).longValue());
			if ((java.lang.Short.class).equals(targetType))
				return new Short(((Number)value).shortValue());
			else
				throw new IllegalArgumentException("this code must not be reached");
		}
		if ((value instanceof Number) && (java.lang.String.class).equals(targetType))
			if ((value instanceof Double) || (value instanceof Float))
				return format.format(((Number)value).doubleValue());
			else
				return format.format(((Number)value).longValue());
		if ((value instanceof String) && (java.lang.Number.class).isAssignableFrom(targetType)) {
			if ((java.lang.Double.class).equals(targetType))
				return new Double((String)value);
			if ((java.lang.Float.class).equals(targetType))
				return new Float((String)value);
			if ((java.lang.Integer.class).equals(targetType))
				return new Integer((String)value);
			if ((java.lang.Long.class).equals(targetType))
				return new Long((String)value);
			if ((java.lang.Short.class).equals(targetType))
				return new Short((String)value);
			else
				throw new IllegalArgumentException("this code must not be reached");
		} else {
			throw new IllegalArgumentException("no conversion supported");
		}
	}
}
