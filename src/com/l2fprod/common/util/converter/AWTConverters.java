// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   AWTConverters.java

package com.l2fprod.common.util.converter;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.StringTokenizer;

// Referenced classes of package com.l2fprod.common.util.converter:
//			Converter, ConverterRegistry

public class AWTConverters
	implements Converter {

	public AWTConverters() {
	}

	public void register(ConverterRegistry registry) {
		registry.addConverter(java.awt.Dimension.class, java.lang.String.class, this);
		registry.addConverter(java.lang.String.class, java.awt.Dimension.class, this);
		registry.addConverter(javax.swing.plaf.DimensionUIResource.class, java.lang.String.class, this);
		registry.addConverter(java.awt.Insets.class, java.lang.String.class, this);
		registry.addConverter(java.lang.String.class, java.awt.Insets.class, this);
		registry.addConverter(javax.swing.plaf.InsetsUIResource.class, java.lang.String.class, this);
		registry.addConverter(java.awt.Point.class, java.lang.String.class, this);
		registry.addConverter(java.lang.String.class, java.awt.Point.class, this);
		registry.addConverter(java.awt.Rectangle.class, java.lang.String.class, this);
		registry.addConverter(java.lang.String.class, java.awt.Rectangle.class, this);
		registry.addConverter(java.awt.Font.class, java.lang.String.class, this);
		registry.addConverter(javax.swing.plaf.FontUIResource.class, java.lang.String.class, this);
	}

	public Object convert(Class type, Object value) {
		if ((java.lang.String.class).equals(type)) {
			if (value instanceof Rectangle)
				return ((Rectangle)value).getX() + " " + ((Rectangle)value).getY() + " " + ((Rectangle)value).getWidth() + " " + ((Rectangle)value).getHeight();
			if (value instanceof Insets)
				return ((Insets)value).top + " " + ((Insets)value).left + " " + ((Insets)value).bottom + " " + ((Insets)value).right;
			if (value instanceof Dimension)
				return ((Dimension)value).getWidth() + " x " + ((Dimension)value).getHeight();
			if ((java.awt.Point.class).equals(value.getClass()))
				return ((Point)value).getX() + " " + ((Point)value).getY();
			if (value instanceof Font)
				return ((Font)value).getFontName() + ", " + ((Font)value).getStyle() + ", " + ((Font)value).getSize();
		}
		if (value instanceof String) {
			if ((java.awt.Rectangle.class).equals(type)) {
				double values[] = convert((String)value, 4, " ");
				if (values == null) {
					throw new IllegalArgumentException("Invalid format");
				} else {
					Rectangle rect = new Rectangle();
					rect.setFrame(values[0], values[1], values[2], values[3]);
					return rect;
				}
			}
			if ((java.awt.Insets.class).equals(type)) {
				double values[] = convert((String)value, 4, " ");
				if (values == null)
					throw new IllegalArgumentException("Invalid format");
				else
					return new Insets((int)values[0], (int)values[1], (int)values[2], (int)values[3]);
			}
			if ((java.awt.Dimension.class).equals(type)) {
				double values[] = convert((String)value, 2, "x");
				if (values == null) {
					throw new IllegalArgumentException("Invalid format");
				} else {
					Dimension dim = new Dimension();
					dim.setSize(values[0], values[1]);
					return dim;
				}
			}
			if ((java.awt.Point.class).equals(type)) {
				double values[] = convert((String)value, 2, " ");
				if (values == null) {
					throw new IllegalArgumentException("Invalid format");
				} else {
					Point p = new Point();
					p.setLocation(values[0], values[1]);
					return p;
				}
			}
		}
		return null;
	}

	private double[] convert(String text, int tokenCount, String delimiters) {
		StringTokenizer tokenizer;
		tokenizer = new StringTokenizer(text, delimiters);
		if (tokenizer.countTokens() != tokenCount)
			return null;
		double values[];
		values = new double[tokenCount];
		for (int i = 0; tokenizer.hasMoreTokens(); i++)
			values[i] = Double.parseDouble(tokenizer.nextToken());

		return values;
		Exception e;
		e;
		return null;
	}
}
