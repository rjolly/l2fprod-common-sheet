// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   PropertySheetPage2.java

package com.l2fprod.common.demo;

import com.l2fprod.common.propertysheet.Property;
import com.l2fprod.common.propertysheet.PropertySheetPanel;
import com.l2fprod.common.swing.LookAndFeelTweaks;
import com.l2fprod.common.util.ResourceManager;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.SimpleBeanInfo;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

// Referenced classes of package com.l2fprod.common.demo:
//			PropertySheetMain

public class PropertySheetPage2 extends JPanel {

	public PropertySheetPage2() {
		setLayout(LookAndFeelTweaks.createVerticalPercentLayout());
		final JButton button = new JButton();
		button.setText("Change my properties!");
		java.beans.BeanInfo beanInfo = new SimpleBeanInfo();
		try {
			beanInfo = Introspector.getBeanInfo(javax.swing.JButton.class);
		}
		catch (IntrospectionException e) {
			e.printStackTrace();
		}
		PropertySheetPanel sheet = new PropertySheetPanel();
		sheet.setMode(0);
		sheet.setToolBarVisible(false);
		sheet.setDescriptionVisible(false);
		sheet.setBeanInfo(beanInfo);
		JPanel panel = new JPanel(LookAndFeelTweaks.createBorderLayout());
		panel.add("Center", sheet);
		panel.add("East", button);
		Property properties[] = sheet.getProperties();
		int i = 0;
		for (int c = properties.length; i < c; i++)
			try {
				properties[i].readFromObject(button);
			}
			catch (Exception e) { }

		PropertyChangeListener listener = new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				Property prop = (Property)evt.getSource();
				prop.writeToObject(button);
				button.repaint();
			}

		};
		sheet.addPropertySheetChangeListener(listener);
		JTextArea message = new JTextArea();
		message.setText(PropertySheetMain.RESOURCE.getString("Main.sheet2.message"));
		LookAndFeelTweaks.makeMultilineLabel(message);
		panel.add("North", message);
		add(panel, "*");
	}
}
