// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JTipOfTheDayAddon.java

package com.l2fprod.common.swing.plaf;

import com.l2fprod.common.swing.plaf.windows.WindowsTipOfTheDayUI;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;

// Referenced classes of package com.l2fprod.common.swing.plaf:
//			AbstractComponentAddon, LookAndFeelAddons

public class JTipOfTheDayAddon extends AbstractComponentAddon {

	public JTipOfTheDayAddon() {
		super("JTipOfTheDay");
	}

	protected void addBasicDefaults(LookAndFeelAddons addon, java.util.List defaults) {
		defaults.add("l2fprod/TipOfTheDayUI");
		defaults.add((com.l2fprod.common.swing.plaf.basic.BasicTipOfTheDayUI.class).getName());
		defaults.add("TipOfTheDay.font");
		defaults.add(UIManager.getFont("TextPane.font"));
		defaults.add("TipOfTheDay.tipFont");
		defaults.add(UIManager.getFont("Label.font").deriveFont(1, 13F));
		defaults.add("TipOfTheDay.background");
		defaults.add(new ColorUIResource(Color.white));
		defaults.add("TipOfTheDay.icon");
		defaults.add(LookAndFeel.makeIcon(com.l2fprod.common.swing.plaf.basic.BasicTipOfTheDayUI.class, "TipOfTheDay24.gif"));
		defaults.add("TipOfTheDay.border");
		defaults.add(new BorderUIResource(BorderFactory.createLineBorder(new Color(117, 117, 117))));
		addResource(defaults, "com.l2fprod.common.swing.plaf.basic.resources.TipOfTheDay");
	}

	protected void addWindowsDefaults(LookAndFeelAddons addon, java.util.List defaults) {
		super.addWindowsDefaults(addon, defaults);
		defaults.add("l2fprod/TipOfTheDayUI");
		defaults.add((com.l2fprod.common.swing.plaf.windows.WindowsTipOfTheDayUI.class).getName());
		defaults.add("TipOfTheDay.background");
		defaults.add(new ColorUIResource(128, 128, 128));
		defaults.add("TipOfTheDay.font");
		defaults.add(UIManager.getFont("Label.font").deriveFont(13F));
		defaults.add("TipOfTheDay.icon");
		defaults.add(LookAndFeel.makeIcon(com.l2fprod.common.swing.plaf.windows.WindowsTipOfTheDayUI.class, "tipoftheday.png"));
		defaults.add("TipOfTheDay.border");
		defaults.add(new BorderUIResource(new com.l2fprod.common.swing.plaf.windows.WindowsTipOfTheDayUI.TipAreaBorder()));
		addResource(defaults, "com.l2fprod.common.swing.plaf.windows.resources.TipOfTheDay");
	}
}
