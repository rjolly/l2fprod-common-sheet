// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   OS.java

package com.l2fprod.common.util;

import java.awt.Toolkit;
import javax.swing.UIManager;

public class OS {

	private static final boolean osIsMacOsX;
	private static final boolean osIsWindows;
	private static final boolean osIsWindowsXP;
	private static final boolean osIsWindows2003;

	public OS() {
	}

	public static boolean isMacOSX() {
		return osIsMacOsX;
	}

	public static boolean isWindows() {
		return osIsWindows;
	}

	public static boolean isWindowsXP() {
		return osIsWindowsXP;
	}

	public static boolean isWindows2003() {
		return osIsWindows2003;
	}

	public static boolean isUsingWindowsVisualStyles() {
		if (!isWindows())
			return false;
		boolean xpthemeActive = Boolean.TRUE.equals(Toolkit.getDefaultToolkit().getDesktopProperty("win.xpstyle.themeActive"));
		if (!xpthemeActive)
			return false;
		return System.getProperty("swing.noxp") == null;
		RuntimeException e;
		e;
		return true;
	}

	public static String getWindowsVisualStyle() {
		String style = UIManager.getString("win.xpstyle.name");
		if (style == null)
			style = (String)Toolkit.getDefaultToolkit().getDesktopProperty("win.xpstyle.colorName");
		return style;
	}

	static  {
		String os = System.getProperty("os.name").toLowerCase();
		osIsMacOsX = "mac os x".equals(os);
		osIsWindows = os.indexOf("windows") != -1;
		osIsWindowsXP = "windows xp".equals(os);
		osIsWindows2003 = "windows 2003".equals(os);
	}
}
