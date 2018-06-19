// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   PercentLayout.java

package com.l2fprod.common.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

public class PercentLayout
	implements LayoutManager2 {
	static class PercentConstraint extends Constraint {

		public float floatValue() {
			return ((Float)value).floatValue();
		}

		public PercentConstraint(float d) {
			super(new Float(d));
		}
	}

	static class NumberConstraint extends Constraint {

		public int intValue() {
			return ((Integer)value).intValue();
		}

		public NumberConstraint(int d) {
			this(new Integer(d));
		}

		public NumberConstraint(Integer d) {
			super(d);
		}
	}

	static class Constraint {

		protected Object value;

		private Constraint(Object value) {
			this.value = value;
		}

	}


	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1;
	private static final Constraint REMAINING_SPACE = new Constraint("*");
	private static final Constraint PREFERRED_SIZE = new Constraint("");
	private int orientation;
	private int gap;
	private Hashtable m_ComponentToConstraint;

	public PercentLayout() {
		this(0, 0);
	}

	public PercentLayout(int orientation, int gap) {
		setOrientation(orientation);
		this.gap = gap;
		m_ComponentToConstraint = new Hashtable();
	}

	public void setGap(int gap) {
		this.gap = gap;
	}

	public int getGap() {
		return gap;
	}

	public void setOrientation(int orientation) {
		if (orientation != 0 && orientation != 1) {
			throw new IllegalArgumentException("Orientation must be one of HORIZONTAL or VERTICAL");
		} else {
			this.orientation = orientation;
			return;
		}
	}

	public int getOrientation() {
		return orientation;
	}

	public Constraint getConstraint(Component component) {
		return (Constraint)m_ComponentToConstraint.get(component);
	}

	public void setConstraint(Component component, Object constraints) {
		if (constraints instanceof Constraint)
			m_ComponentToConstraint.put(component, constraints);
		else
		if (constraints instanceof Number)
			setConstraint(component, new NumberConstraint(((Number)constraints).intValue()));
		else
		if ("*".equals(constraints))
			setConstraint(component, REMAINING_SPACE);
		else
		if ("".equals(constraints))
			setConstraint(component, PREFERRED_SIZE);
		else
		if (constraints instanceof String) {
			String s = (String)constraints;
			if (s.endsWith("%")) {
				float value = Float.valueOf(s.substring(0, s.length() - 1)).floatValue() / 100F;
				if (value > 1.0F || value < 0.0F)
					throw new IllegalArgumentException("percent value must be >= 0 and <= 100");
				setConstraint(component, new PercentConstraint(value));
			} else {
				setConstraint(component, new NumberConstraint(Integer.valueOf(s)));
			}
		} else
		if (constraints == null)
			setConstraint(component, PREFERRED_SIZE);
		else
			throw new IllegalArgumentException("Invalid Constraint");
	}

	public void addLayoutComponent(Component component, Object constraints) {
		setConstraint(component, constraints);
	}

	public float getLayoutAlignmentX(Container target) {
		return 0.5F;
	}

	public float getLayoutAlignmentY(Container target) {
		return 0.5F;
	}

	public void invalidateLayout(Container container) {
	}

	public void addLayoutComponent(String s, Component component) {
	}

	public void removeLayoutComponent(Component comp) {
		m_ComponentToConstraint.remove(comp);
	}

	public Dimension minimumLayoutSize(Container parent) {
		return preferredLayoutSize(parent);
	}

	public Dimension maximumLayoutSize(Container parent) {
		return new Dimension(0x7fffffff, 0x7fffffff);
	}

	public Dimension preferredLayoutSize(Container parent) {
		Component components[] = parent.getComponents();
		Insets insets = parent.getInsets();
		int width = 0;
		int height = 0;
		boolean firstVisibleComponent = true;
		int i = 0;
		for (int c = components.length; i < c; i++) {
			if (!components[i].isVisible())
				continue;
			Dimension componentPreferredSize = components[i].getPreferredSize();
			if (orientation == 0) {
				height = Math.max(height, componentPreferredSize.height);
				width += componentPreferredSize.width;
				if (firstVisibleComponent)
					firstVisibleComponent = false;
				else
					width += gap;
				continue;
			}
			height += componentPreferredSize.height;
			width = Math.max(width, componentPreferredSize.width);
			if (firstVisibleComponent)
				firstVisibleComponent = false;
			else
				height += gap;
		}

		return new Dimension(width + insets.right + insets.left, height + insets.top + insets.bottom);
	}

	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		Dimension d = parent.getSize();
		d.width = d.width - insets.left - insets.right;
		d.height = d.height - insets.top - insets.bottom;
		Component components[] = parent.getComponents();
		int sizes[] = new int[components.length];
		int totalSize = (0 != orientation ? d.height : d.width) - (components.length - 1) * gap;
		int availableSize = totalSize;
		int i = 0;
		for (int c = components.length; i < c; i++) {
			if (!components[i].isVisible())
				continue;
			Constraint constraint = (Constraint)m_ComponentToConstraint.get(components[i]);
			if (constraint == null || constraint == PREFERRED_SIZE) {
				sizes[i] = 0 != orientation ? components[i].getPreferredSize().height : components[i].getPreferredSize().width;
				availableSize -= sizes[i];
				continue;
			}
			if (constraint instanceof NumberConstraint) {
				sizes[i] = ((NumberConstraint)constraint).intValue();
				availableSize -= sizes[i];
			}
		}

		int remainingSize = availableSize;
		int i = 0;
		for (int c = components.length; i < c; i++) {
			if (!components[i].isVisible())
				continue;
			Constraint constraint = (Constraint)m_ComponentToConstraint.get(components[i]);
			if (constraint instanceof PercentConstraint) {
				sizes[i] = (int)((float)remainingSize * ((PercentConstraint)constraint).floatValue());
				availableSize -= sizes[i];
			}
		}

		ArrayList remaining = new ArrayList();
		int i = 0;
		for (int c = components.length; i < c; i++) {
			if (!components[i].isVisible())
				continue;
			Constraint constraint = (Constraint)m_ComponentToConstraint.get(components[i]);
			if (constraint == REMAINING_SPACE) {
				remaining.add(new Integer(i));
				sizes[i] = 0;
			}
		}

		if (remaining.size() > 0) {
			int rest = availableSize / remaining.size();
			for (Iterator iter = remaining.iterator(); iter.hasNext();)
				sizes[((Integer)iter.next()).intValue()] = rest;

		}
		int currentOffset = 0 != orientation ? insets.top : insets.left;
		int i = 0;
		for (int c = components.length; i < c; i++) {
			if (!components[i].isVisible())
				continue;
			if (0 == orientation)
				components[i].setBounds(currentOffset, insets.top, sizes[i], d.height);
			else
				components[i].setBounds(insets.left, currentOffset, d.width, sizes[i]);
			currentOffset += gap + sizes[i];
		}

	}

}
