// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JTaskPaneAddon.java

package com.l2fprod.common.swing.plaf;

import com.l2fprod.common.swing.plaf.windows.WindowsClassicLookAndFeelAddons;
import com.l2fprod.common.swing.plaf.windows.WindowsLookAndFeelAddons;
import com.l2fprod.common.util.OS;
import java.util.Arrays;
import java.util.List;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;

// Referenced classes of package com.l2fprod.common.swing.plaf:
//			AbstractComponentAddon, LookAndFeelAddons

public class JTaskPaneAddon extends AbstractComponentAddon {

	public JTaskPaneAddon() {
		super("JTaskPane");
	}

	protected void addBasicDefaults(LookAndFeelAddons addon, List defaults) {
		super.addBasicDefaults(addon, defaults);
		defaults.addAll(Arrays.asList(new Object[] {
			"TaskPaneUI", "com.l2fprod.common.swing.plaf.basic.BasicTaskPaneUI", "TaskPane.useGradient", Boolean.FALSE, "TaskPane.background", UIManager.getColor("Desktop.background")
		}));
	}

	protected void addMetalDefaults(LookAndFeelAddons addon, List defaults) {
		super.addMetalDefaults(addon, defaults);
		defaults.addAll(Arrays.asList(new Object[] {
			"TaskPane.background", MetalLookAndFeel.getDesktopColor()
		}));
	}

	protected void addWindowsDefaults(LookAndFeelAddons addon, List defaults) {
		super.addWindowsDefaults(addon, defaults);
		if (addon instanceof WindowsClassicLookAndFeelAddons)
			defaults.addAll(Arrays.asList(new Object[] {
				"TaskPane.background", UIManager.getColor("List.background")
			}));
		else
		if (addon instanceof WindowsLookAndFeelAddons) {
			String xpStyle = OS.getWindowsVisualStyle();
			ColorUIResource background;
			ColorUIResource backgroundGradientStart;
			ColorUIResource backgroundGradientEnd;
			if ("HomeStead".equalsIgnoreCase(xpStyle)) {
				background = new ColorUIResource(201, 215, 170);
				backgroundGradientStart = new ColorUIResource(204, 217, 173);
				backgroundGradientEnd = new ColorUIResource(165, 189, 132);
			} else
			if ("Metallic".equalsIgnoreCase(xpStyle)) {
				background = new ColorUIResource(192, 195, 209);
				backgroundGradientStart = new ColorUIResource(196, 200, 212);
				backgroundGradientEnd = new ColorUIResource(177, 179, 200);
			} else {
				background = new ColorUIResource(117, 150, 227);
				backgroundGradientStart = new ColorUIResource(123, 162, 231);
				backgroundGradientEnd = new ColorUIResource(99, 117, 214);
			}
			defaults.addAll(Arrays.asList(new Object[] {
				"TaskPane.useGradient", Boolean.TRUE, "TaskPane.background", background, "TaskPane.backgroundGradientStart", backgroundGradientStart, "TaskPane.backgroundGradientEnd", backgroundGradientEnd
			}));
		}
	}

	protected void addMacDefaults(LookAndFeelAddons addon, List defaults) {
		super.addMacDefaults(addon, defaults);
		defaults.addAll(Arrays.asList(new Object[] {
			"TaskPane.background", new ColorUIResource(238, 238, 238)
		}));
	}
}
