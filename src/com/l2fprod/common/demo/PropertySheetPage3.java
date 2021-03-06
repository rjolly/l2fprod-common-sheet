// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   PropertySheetPage3.java

package com.l2fprod.common.demo;

import com.l2fprod.common.propertysheet.DefaultProperty;
import com.l2fprod.common.propertysheet.Property;
import com.l2fprod.common.propertysheet.PropertySheetPanel;
import com.l2fprod.common.propertysheet.PropertySheetTable;
import com.l2fprod.common.swing.LookAndFeelTweaks;
import com.l2fprod.common.util.ResourceManager;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.PrintStream;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

// Referenced classes of package com.l2fprod.common.demo:
//			PropertySheetMain

public class PropertySheetPage3 extends JPanel {
	public static class ColorComponentProperty extends DefaultProperty {

		public ColorComponentProperty(String name) {
			setName(name);
			setDisplayName(PropertySheetPage3.RESOURCE.getString(name + ".name"));
			setShortDescription(PropertySheetPage3.RESOURCE.getString(name + ".desc"));
			setType(Integer.TYPE);
		}
	}

	public static class ColorProperty extends DefaultProperty {

		public ColorProperty() {
			setName("color");
			setCategory(PropertySheetPage3.RESOURCE.getString("color.cat"));
			setDisplayName(PropertySheetPage3.RESOURCE.getString("color.name"));
			setShortDescription(PropertySheetPage3.RESOURCE.getString("color.desc"));
			setType(PropertySheetPage3.class$java$awt$Color != null ? PropertySheetPage3.class$java$awt$Color : (PropertySheetPage3.class$java$awt$Color = PropertySheetPage3._mthclass$("java.awt.Color")));
			addSubProperty(new ColorComponentProperty("red"));
			addSubProperty(new ColorComponentProperty("green"));
			addSubProperty(new ColorComponentProperty("blue"));
		}
	}

	public static class Colorful {

		private Color color;

		public Color getColor() {
			return color;
		}

		public void setColor(Color color) {
			this.color = color;
		}

		public int getRed() {
			return color.getRed();
		}

		public void setRed(int red) {
			color = new Color(red, getGreen(), getBlue());
		}

		public int getGreen() {
			return color.getGreen();
		}

		public void setGreen(int green) {
			color = new Color(getRed(), green, getBlue());
		}

		public int getBlue() {
			return color.getBlue();
		}

		public void setBlue(int blue) {
			color = new Color(getRed(), getGreen(), blue);
		}

		public String toString() {
			return color.toString();
		}

		public Colorful() {
		}
	}

	static class NoReadWriteProperty extends DefaultProperty {

		public void readFromObject(Object obj) {
		}

		public void writeToObject(Object obj) {
		}

		NoReadWriteProperty() {
		}
	}


	private static final Class THIS_CLASS;
	static ResourceManager RESOURCE;
	static Class class$java$awt$Color; /* synthetic field */

	public PropertySheetPage3() {
		setLayout(LookAndFeelTweaks.createVerticalPercentLayout());
		JTextArea message = new JTextArea();
		message.setText(PropertySheetMain.RESOURCE.getString("Main.sheet1.message"));
		LookAndFeelTweaks.makeMultilineLabel(message);
		add(message);
		final Colorful data = new Colorful();
		data.setColor(new Color(255, 153, 102));
		DefaultProperty level0 = new NoReadWriteProperty();
		level0.setDisplayName("Level 0");
		level0.setCategory("A category");
		DefaultProperty level1 = new NoReadWriteProperty();
		level1.setDisplayName("Level 1");
		level1.setCategory("Another category");
		level0.addSubProperty(level1);
		DefaultProperty level2 = new NoReadWriteProperty();
		level2.setDisplayName("Level 2");
		level1.addSubProperty(level2);
		DefaultProperty level21 = new NoReadWriteProperty();
		level21.setDisplayName("Level 3");
		level1.addSubProperty(level21);
		DefaultProperty level211 = new NoReadWriteProperty();
		level211.setDisplayName("Level 3.1");
		level21.addSubProperty(level211);
		DefaultProperty root = new NoReadWriteProperty();
		root.setDisplayName("Root");
		final PropertySheetPanel sheet = new PropertySheetPanel();
		sheet.setMode(0);
		sheet.setProperties(new Property[] {
			new ColorProperty(), level0, root
		});
		sheet.readFromObject(data);
		sheet.setDescriptionVisible(true);
		sheet.setSortingCategories(true);
		sheet.setSortingProperties(true);
		add(sheet, "*");
		PropertyChangeListener listener = new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				Property prop = (Property)evt.getSource();
				prop.writeToObject(data);
				System.out.println("Updated object to " + data);
			}

		};
		sheet.addPropertySheetChangeListener(listener);
		JButton button = new JButton(new AbstractAction(sheet.toString()) {

			public void actionPerformed(ActionEvent e) {
				sheet.getTable().setWantsExtraIndent(!sheet.getTable().getWantsExtraIndent());
				putValue("Name", "Click to setWantsExtraIndent(" + (!sheet.getTable().getWantsExtraIndent()) + ")");
			}

		});
		add(button);
	}

	static Class _mthclass$(String x0) {
		try {
			return Class.forName(x0);
		} catch (final ClassNotFoundException e) {
			throw new NoClassDefFoundError(e.getMessage());
		}
	}

	static  {
		THIS_CLASS = com.l2fprod.common.demo.PropertySheetPage3.class;
		RESOURCE = ResourceManager.get(THIS_CLASS);
	}
}
