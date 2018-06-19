// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   AbstractComponentAddon.java

package com.l2fprod.common.swing.plaf;

import com.l2fprod.common.swing.plaf.aqua.AquaLookAndFeelAddons;
import com.l2fprod.common.swing.plaf.metal.MetalLookAndFeelAddons;
import com.l2fprod.common.swing.plaf.motif.MotifLookAndFeelAddons;
import com.l2fprod.common.swing.plaf.windows.WindowsLookAndFeelAddons;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.UIManager;

// Referenced classes of package com.l2fprod.common.swing.plaf:
//			ComponentAddon, LookAndFeelAddons

public abstract class AbstractComponentAddon
	implements ComponentAddon {

	private String name;

	protected AbstractComponentAddon(String name) {
		this.name = name;
	}

	public final String getName() {
		return name;
	}

	public void initialize(LookAndFeelAddons addon) {
		addon.loadDefaults(getDefaults(addon));
	}

	public void uninitialize(LookAndFeelAddons addon) {
		addon.unloadDefaults(getDefaults(addon));
	}

	protected void addBasicDefaults(LookAndFeelAddons lookandfeeladdons, List list) {
	}

	protected void addMacDefaults(LookAndFeelAddons addon, List defaults) {
		addBasicDefaults(addon, defaults);
	}

	protected void addMetalDefaults(LookAndFeelAddons addon, List defaults) {
		addBasicDefaults(addon, defaults);
	}

	protected void addMotifDefaults(LookAndFeelAddons addon, List defaults) {
		addBasicDefaults(addon, defaults);
	}

	protected void addWindowsDefaults(LookAndFeelAddons addon, List defaults) {
		addBasicDefaults(addon, defaults);
	}

	private Object[] getDefaults(LookAndFeelAddons addon) {
		List defaults = new ArrayList();
		if (isWindows(addon))
			addWindowsDefaults(addon, defaults);
		else
		if (isMetal(addon))
			addMetalDefaults(addon, defaults);
		else
		if (isMac(addon))
			addMacDefaults(addon, defaults);
		else
		if (isMotif(addon))
			addMotifDefaults(addon, defaults);
		else
			addBasicDefaults(addon, defaults);
		return defaults.toArray();
	}

	protected void addResource(List defaults, String bundleName) {
		ResourceBundle bundle = ResourceBundle.getBundle(bundleName);
		String key;
		for (Enumeration keys = bundle.getKeys(); keys.hasMoreElements(); defaults.add(bundle.getObject(key))) {
			key = (String)keys.nextElement();
			defaults.add(key);
		}

	}

	protected boolean isWindows(LookAndFeelAddons addon) {
		return addon instanceof WindowsLookAndFeelAddons;
	}

	protected boolean isMetal(LookAndFeelAddons addon) {
		return addon instanceof MetalLookAndFeelAddons;
	}

	protected boolean isMac(LookAndFeelAddons addon) {
		return addon instanceof AquaLookAndFeelAddons;
	}

	protected boolean isMotif(LookAndFeelAddons addon) {
		return addon instanceof MotifLookAndFeelAddons;
	}

	protected boolean isPlastic() {
		return UIManager.getLookAndFeel().getClass().getName().indexOf("Plastic") != -1;
	}

	protected boolean isSynth() {
		return UIManager.getLookAndFeel().getClass().getName().indexOf("ynth") != -1;
	}

	protected java.awt.Font getFont(String key, java.awt.Font defaultFont) {
		java.awt.Font result = UIManager.getFont(key);
		return result == null ? defaultFont : result;
	}

	protected java.awt.Color getColor(String key, java.awt.Color defaultColor) {
		java.awt.Color result = UIManager.getColor(key);
		return result == null ? defaultColor : result;
	}
}
