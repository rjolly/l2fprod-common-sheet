// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   StatusBar.java

package com.l2fprod.common.swing;

import java.awt.Color;
import java.awt.Component;
import java.util.Hashtable;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.UIResource;

// Referenced classes of package com.l2fprod.common.swing:
//			LookAndFeelTweaks

public class StatusBar extends JComponent {

	public static final String DEFAULT_ZONE = "default";
	private Hashtable idToZones;
	private Border zoneBorder;

	public StatusBar() {
		setLayout(LookAndFeelTweaks.createHorizontalPercentLayout());
		idToZones = new Hashtable();
		setZoneBorder(BorderFactory.createLineBorder(Color.gray));
	}

	public void setZoneBorder(Border border) {
		zoneBorder = border;
	}

	public void addZone(String id, Component zone, String constraints) {
		Component previousZone = getZone(id);
		if (previousZone != null) {
			remove(previousZone);
			idToZones.remove(id);
		}
		if (zone instanceof JComponent) {
			JComponent jc = (JComponent)zone;
			if (jc.getBorder() == null || (jc.getBorder() instanceof UIResource))
				if (jc instanceof JLabel) {
					jc.setBorder(new CompoundBorder(zoneBorder, new EmptyBorder(0, 2, 0, 2)));
					((JLabel)jc).setText(" ");
				} else {
					jc.setBorder(zoneBorder);
				}
		}
		add(zone, constraints);
		idToZones.put(id, zone);
	}

	public Component getZone(String id) {
		return (Component)idToZones.get(id);
	}

	public void setZones(String ids[], Component zones[], String constraints[]) {
		removeAll();
		idToZones.clear();
		int i = 0;
		for (int c = zones.length; i < c; i++)
			addZone(ids[i], zones[i], constraints[i]);

	}
}
