// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JTaskPaneGroupAddon.java

package com.l2fprod.common.swing.plaf;

import com.l2fprod.common.swing.plaf.windows.WindowsClassicLookAndFeelAddons;
import com.l2fprod.common.swing.plaf.windows.WindowsLookAndFeelAddons;
import com.l2fprod.common.util.JVM;
import com.l2fprod.common.util.OS;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.lang.reflect.Method;
import java.util.Arrays;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;

// Referenced classes of package com.l2fprod.common.swing.plaf:
//			AbstractComponentAddon, LookAndFeelAddons

public class JTaskPaneGroupAddon extends AbstractComponentAddon {

	public JTaskPaneGroupAddon() {
		super("JTaskPaneGroup");
	}

	protected void addBasicDefaults(LookAndFeelAddons addon, java.util.List defaults) {
		Color menuBackground = new ColorUIResource(SystemColor.menu);
		defaults.addAll(Arrays.asList(new Object[] {
			"TaskPaneGroupUI", "com.l2fprod.common.swing.plaf.basic.BasicTaskPaneGroupUI", "TaskPaneGroup.font", new FontUIResource(getFont("Label.font", new Font("Dialog", 0, 12)).deriveFont(1)), "TaskPaneGroup.background", UIManager.getColor("List.background"), "TaskPaneGroup.specialTitleBackground", new ColorUIResource(menuBackground.darker()), "TaskPaneGroup.titleBackgroundGradientStart", menuBackground, 
			"TaskPaneGroup.titleBackgroundGradientEnd", menuBackground, "TaskPaneGroup.titleForeground", new ColorUIResource(SystemColor.menuText), "TaskPaneGroup.specialTitleForeground", (new ColorUIResource(SystemColor.menuText)).brighter(), "TaskPaneGroup.animate", Boolean.TRUE, "TaskPaneGroup.focusInputMap", new javax.swing.UIDefaults.LazyInputMap(new Object[] {
				"ENTER", "toggleExpanded", "SPACE", "toggleExpanded"
			})
		}));
	}

	protected void addMetalDefaults(LookAndFeelAddons addon, java.util.List defaults) {
		super.addMetalDefaults(addon, defaults);
		String taskPaneGroupUI = "com.l2fprod.common.swing.plaf.metal.MetalTaskPaneGroupUI";
		if (JVM.current().isOrLater(15))
			try {
				Method method = (javax.swing.plaf.metal.MetalLookAndFeel.class).getMethod("getCurrentTheme", null);
				Object currentTheme = method.invoke(null, null);
				if (Class.forName("javax.swing.plaf.metal.OceanTheme").isInstance(currentTheme))
					taskPaneGroupUI = "com.l2fprod.common.swing.plaf.misc.GlossyTaskPaneGroupUI";
			}
			catch (Exception e) { }
		defaults.addAll(Arrays.asList(new Object[] {
			"TaskPaneGroupUI", taskPaneGroupUI, "TaskPaneGroup.foreground", UIManager.getColor("activeCaptionText"), "TaskPaneGroup.background", MetalLookAndFeel.getControl(), "TaskPaneGroup.specialTitleBackground", MetalLookAndFeel.getPrimaryControl(), "TaskPaneGroup.titleBackgroundGradientStart", MetalLookAndFeel.getPrimaryControl(), 
			"TaskPaneGroup.titleBackgroundGradientEnd", MetalLookAndFeel.getPrimaryControlHighlight(), "TaskPaneGroup.titleForeground", MetalLookAndFeel.getControlTextColor(), "TaskPaneGroup.specialTitleForeground", MetalLookAndFeel.getControlTextColor(), "TaskPaneGroup.borderColor", MetalLookAndFeel.getPrimaryControl(), "TaskPaneGroup.titleOver", MetalLookAndFeel.getControl().darker(), 
			"TaskPaneGroup.specialTitleOver", MetalLookAndFeel.getPrimaryControlHighlight()
		}));
	}

	protected void addWindowsDefaults(LookAndFeelAddons addon, java.util.List defaults) {
		super.addWindowsDefaults(addon, defaults);
		if (addon instanceof WindowsLookAndFeelAddons) {
			defaults.addAll(Arrays.asList(new Object[] {
				"TaskPaneGroupUI", "com.l2fprod.common.swing.plaf.windows.WindowsTaskPaneGroupUI"
			}));
			String xpStyle = OS.getWindowsVisualStyle();
			if ("HomeStead".equalsIgnoreCase(xpStyle))
				defaults.addAll(Arrays.asList(new Object[] {
					"TaskPaneGroup.foreground", new ColorUIResource(86, 102, 45), "TaskPaneGroup.background", new ColorUIResource(246, 246, 236), "TaskPaneGroup.specialTitleBackground", new ColorUIResource(224, 231, 184), "TaskPaneGroup.titleBackgroundGradientStart", new ColorUIResource(255, 255, 255), "TaskPaneGroup.titleBackgroundGradientEnd", new ColorUIResource(224, 231, 184), 
					"TaskPaneGroup.titleForeground", new ColorUIResource(86, 102, 45), "TaskPaneGroup.titleOver", new ColorUIResource(114, 146, 29), "TaskPaneGroup.specialTitleForeground", new ColorUIResource(86, 102, 45), "TaskPaneGroup.specialTitleOver", new ColorUIResource(114, 146, 29), "TaskPaneGroup.borderColor", new ColorUIResource(255, 255, 255)
				}));
			else
			if ("Metallic".equalsIgnoreCase(xpStyle))
				defaults.addAll(Arrays.asList(new Object[] {
					"TaskPaneGroup.foreground", new ColorUIResource(Color.black), "TaskPaneGroup.background", new ColorUIResource(240, 241, 245), "TaskPaneGroup.specialTitleBackground", new ColorUIResource(222, 222, 222), "TaskPaneGroup.titleBackgroundGradientStart", new ColorUIResource(Color.white), "TaskPaneGroup.titleBackgroundGradientEnd", new ColorUIResource(214, 215, 224), 
					"TaskPaneGroup.titleForeground", new ColorUIResource(Color.black), "TaskPaneGroup.titleOver", new ColorUIResource(126, 124, 124), "TaskPaneGroup.specialTitleForeground", new ColorUIResource(Color.black), "TaskPaneGroup.specialTitleOver", new ColorUIResource(126, 124, 124), "TaskPaneGroup.borderColor", new ColorUIResource(Color.white)
				}));
			else
				defaults.addAll(Arrays.asList(new Object[] {
					"TaskPaneGroup.foreground", new ColorUIResource(Color.white), "TaskPaneGroup.background", new ColorUIResource(214, 223, 247), "TaskPaneGroup.specialTitleBackground", new ColorUIResource(33, 89, 201), "TaskPaneGroup.titleBackgroundGradientStart", new ColorUIResource(Color.white), "TaskPaneGroup.titleBackgroundGradientEnd", new ColorUIResource(199, 212, 247), 
					"TaskPaneGroup.titleForeground", new ColorUIResource(33, 89, 201), "TaskPaneGroup.specialTitleForeground", new ColorUIResource(Color.white), "TaskPaneGroup.borderColor", new ColorUIResource(Color.white)
				}));
		}
		if (addon instanceof WindowsClassicLookAndFeelAddons)
			defaults.addAll(Arrays.asList(new Object[] {
				"TaskPaneGroupUI", "com.l2fprod.common.swing.plaf.windows.WindowsClassicTaskPaneGroupUI", "TaskPaneGroup.foreground", new ColorUIResource(Color.black), "TaskPaneGroup.background", new ColorUIResource(Color.white), "TaskPaneGroup.specialTitleBackground", new ColorUIResource(10, 36, 106), "TaskPaneGroup.titleBackgroundGradientStart", new ColorUIResource(212, 208, 200), 
				"TaskPaneGroup.titleBackgroundGradientEnd", new ColorUIResource(212, 208, 200), "TaskPaneGroup.titleForeground", new ColorUIResource(Color.black), "TaskPaneGroup.specialTitleForeground", new ColorUIResource(Color.white), "TaskPaneGroup.borderColor", new ColorUIResource(212, 208, 200)
			}));
	}

	protected void addMacDefaults(LookAndFeelAddons addon, java.util.List defaults) {
		super.addMacDefaults(addon, defaults);
		defaults.addAll(Arrays.asList(new Object[] {
			"TaskPaneGroupUI", "com.l2fprod.common.swing.plaf.misc.GlossyTaskPaneGroupUI", "TaskPaneGroup.background", new ColorUIResource(245, 245, 245), "TaskPaneGroup.titleForeground", new ColorUIResource(Color.black), "TaskPaneGroup.specialTitleBackground", new ColorUIResource(188, 188, 188), "TaskPaneGroup.specialTitleForeground", new ColorUIResource(Color.black), 
			"TaskPaneGroup.titleBackgroundGradientStart", new ColorUIResource(250, 250, 250), "TaskPaneGroup.titleBackgroundGradientEnd", new ColorUIResource(188, 188, 188), "TaskPaneGroup.borderColor", new ColorUIResource(97, 97, 97), "TaskPaneGroup.titleOver", new ColorUIResource(125, 125, 97), "TaskPaneGroup.specialTitleOver", new ColorUIResource(125, 125, 97)
		}));
	}
}
