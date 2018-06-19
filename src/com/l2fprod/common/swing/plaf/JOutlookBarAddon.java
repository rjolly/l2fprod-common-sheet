// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JOutlookBarAddon.java

package com.l2fprod.common.swing.plaf;

import com.l2fprod.common.swing.border.FourLineBorder;
import com.l2fprod.common.swing.plaf.basic.BasicOutlookButtonUI;
import com.l2fprod.common.swing.plaf.windows.WindowsOutlookBarUI;
import java.awt.Color;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;

// Referenced classes of package com.l2fprod.common.swing.plaf:
//			AbstractComponentAddon, LookAndFeelAddons

public class JOutlookBarAddon extends AbstractComponentAddon {

	public JOutlookBarAddon() {
		super("JOutlookBar");
	}

	protected void addBasicDefaults(LookAndFeelAddons addon, java.util.List defaults) {
		Color barBackground = new Color(167, 166, 170);
		Color background = UIManager.getColor("Button.background");
		if (background == null)
			background = UIManager.getColor("control");
		if (background == null)
			background = new Color(238, 238, 238);
		Color color1 = background.brighter();
		Color color2 = background.darker();
		javax.swing.border.Border outlookBarButtonBorder = new com.l2fprod.common.swing.plaf.windows.WindowsOutlookBarUI.WindowsTabButtonBorder(color1, color2);
		outlookBarButtonBorder = new CompoundBorder(outlookBarButtonBorder, BorderFactory.createEmptyBorder(3, 3, 3, 3));
		javax.swing.border.Border outlookBarBorder = new FourLineBorder(color2, color2, color1, color1);
		color1 = barBackground.brighter();
		color2 = barBackground.darker();
		javax.swing.border.Border outlookButtonBorder = new com.l2fprod.common.swing.plaf.basic.BasicOutlookButtonUI.OutlookButtonBorder(color1, color2);
		outlookButtonBorder = new CompoundBorder(outlookButtonBorder, BorderFactory.createEmptyBorder(3, 3, 3, 3));
		defaults.addAll(Arrays.asList(new Object[] {
			"OutlookBarUI", (com.l2fprod.common.swing.plaf.windows.WindowsOutlookBarUI.class).getName(), "OutlookButtonUI", (com.l2fprod.common.swing.plaf.basic.BasicOutlookButtonUI.class).getName(), "OutlookBar.background", barBackground, "OutlookBar.border", outlookBarBorder, "OutlookBar.tabButtonBorder", outlookBarButtonBorder, 
			"OutlookButton.border", outlookButtonBorder, "OutlookBar.tabAlignment", new Integer(0)
		}));
	}
}
