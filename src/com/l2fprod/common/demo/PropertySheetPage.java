// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   PropertySheetPage.java

package com.l2fprod.common.demo;

import com.l2fprod.common.beans.BaseBeanInfo;
import com.l2fprod.common.beans.ExtendedPropertyDescriptor;
import com.l2fprod.common.beans.editor.ComboBoxPropertyEditor;
import com.l2fprod.common.propertysheet.PropertySheetPanel;
import com.l2fprod.common.swing.LookAndFeelTweaks;
import com.l2fprod.common.util.ResourceManager;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.Arrays;
import java.util.ListResourceBundle;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

// Referenced classes of package com.l2fprod.common.demo:
//			BeanBinder, PropertySheetMain

public class PropertySheetPage extends JPanel {
	public static class BeanRB extends ListResourceBundle {

		protected Object[][] getContents() {
			return (new Object[][] {
				new Object[] {
					"name", "Name"
				}, new Object[] {
					"name.shortDescription", "The name of this object<br>Here I'm using multple lines<br>for the property<br>so scrollbars will get enabled"
				}, new Object[] {
					"text", "Text"
				}, new Object[] {
					"time", "Time"
				}, new Object[] {
					"color", "Background"
				}, new Object[] {
					"aDouble", "a double"
				}, new Object[] {
					"season", "Season"
				}, new Object[] {
					"constrained.shortDescription", "This property is constrained. Try using <b>blah</b> as the value, the previous value will be restored"
				}
			});
		}

		public BeanRB() {
		}
	}

	public static class SeasonEditor extends ComboBoxPropertyEditor {

		public SeasonEditor() {
			setAvailableValues(new String[] {
				"Spring", "Summer", "Fall", "Winter"
			});
			Icon icons[] = new Icon[4];
			Arrays.fill(icons, UIManager.getIcon("Tree.openIcon"));
			setAvailableIcons(icons);
		}
	}

	public static class BeanBeanInfo extends BaseBeanInfo {

		public BeanBeanInfo() {
			super(PropertySheetPage.class$com$l2fprod$common$demo$PropertySheetPage$Bean != null ? PropertySheetPage.class$com$l2fprod$common$demo$PropertySheetPage$Bean : (PropertySheetPage.class$com$l2fprod$common$demo$PropertySheetPage$Bean = PropertySheetPage._mthclass$("com.l2fprod.common.demo.PropertySheetPage$Bean")));
			addProperty("id").setCategory("General");
			addProperty("name").setCategory("General");
			addProperty("text").setCategory("General");
			addProperty("visible").setCategory("General");
			if (System.getProperty("javawebstart.version") == null)
				addProperty("path").setCategory("Details");
			addProperty("time").setCategory("Details");
			addProperty("color").setCategory("Details");
			addProperty("aDouble").setCategory("Numbers");
			addProperty("season").setCategory("Details").setPropertyEditorClass(PropertySheetPage.class$com$l2fprod$common$demo$PropertySheetPage$SeasonEditor != null ? PropertySheetPage.class$com$l2fprod$common$demo$PropertySheetPage$SeasonEditor : (PropertySheetPage.class$com$l2fprod$common$demo$PropertySheetPage$SeasonEditor = PropertySheetPage._mthclass$("com.l2fprod.common.demo.PropertySheetPage$SeasonEditor")));
			addProperty("version");
			addProperty("constrained");
		}
	}

	public static class Bean {

		private String name;
		private String text;
		private long time;
		private boolean visible;
		private int id;
		private File path;
		private Color color;
		private double doubleValue;
		private String season;
		private String constrained;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public long getTime() {
			return time;
		}

		public void setTime(long time) {
			this.time = time;
		}

		public String getVersion() {
			return "1.0";
		}

		public boolean isVisible() {
			return visible;
		}

		public void setVisible(boolean visible) {
			this.visible = visible;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public File getPath() {
			return path;
		}

		public void setPath(File path) {
			this.path = path;
		}

		public Color getColor() {
			return color;
		}

		public void setColor(Color color) {
			this.color = color;
		}

		public void setADouble(double d) {
			doubleValue = d;
		}

		public double getADouble() {
			return doubleValue;
		}

		public void setSeason(String s) {
			season = s;
		}

		public String getSeason() {
			return season;
		}

		public String getConstrained() {
			return constrained;
		}

		public void setConstrained(String constrained) throws PropertyVetoException {
			if ("blah".equals(constrained)) {
				throw new PropertyVetoException("e", new PropertyChangeEvent(this, "constrained", this.constrained, constrained));
			} else {
				this.constrained = constrained;
				return;
			}
		}

		public String toString() {
			return "[name=" + getName() + ",text=" + getText() + ",time=" + getTime() + ",version=" + getVersion() + ",visible=" + isVisible() + ",id=" + getId() + ",path=" + getPath() + ",aDouble=" + getADouble() + ",season=" + getSeason() + "]";
		}

		public Bean() {
			color = Color.blue;
			doubleValue = 121210.4343543D;
		}
	}


	static Class class$com$l2fprod$common$demo$PropertySheetPage$Bean; /* synthetic field */
	static Class class$com$l2fprod$common$demo$PropertySheetPage$SeasonEditor; /* synthetic field */

	public PropertySheetPage() {
		setLayout(LookAndFeelTweaks.createVerticalPercentLayout());
		JTextArea message = new JTextArea();
		message.setText(PropertySheetMain.RESOURCE.getString("Main.sheet1.message"));
		LookAndFeelTweaks.makeMultilineLabel(message);
		add(message);
		Bean data = new Bean();
		data.setName("John Smith");
		data.setText("Any text here");
		data.setColor(Color.green);
		data.setPath(new File("."));
		data.setVisible(true);
		data.setTime(System.currentTimeMillis());
		PropertySheetPanel sheet = new PropertySheetPanel();
		sheet.setMode(1);
		sheet.setDescriptionVisible(true);
		sheet.setSortingCategories(true);
		sheet.setSortingProperties(true);
		sheet.setRestoreToggleStates(true);
		add(sheet, "*");
		new BeanBinder(data, sheet);
	}

	static Class _mthclass$(String x0) {
		try {
			return Class.forName(x0);
		} catch (final ClassNotFoundException e) {
			throw new NoClassDefFoundError(e.getMessage());
		}
	}
}
