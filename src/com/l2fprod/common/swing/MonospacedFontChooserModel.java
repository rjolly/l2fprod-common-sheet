// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   MonospacedFontChooserModel.java

package com.l2fprod.common.swing;

import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Arrays;

// Referenced classes of package com.l2fprod.common.swing:
//			DefaultFontChooserModel

public class MonospacedFontChooserModel extends DefaultFontChooserModel {

	public MonospacedFontChooserModel() {
		java.util.List monospaces = new ArrayList();
		String fontFamilies[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		Arrays.sort(fontFamilies);
		int i = 0;
		for (int c = fontFamilies.length; i < c; i++)
			if (isMonospaced(fontFamilies[i]))
				monospaces.add(fontFamilies[i]);

		setFontFamilies((String[])monospaces.toArray(new String[monospaces.size()]));
	}

	protected boolean isMonospaced(String fontFamily) {
		String lower = fontFamily.toLowerCase();
		return lower.indexOf("fixed") >= 0 || lower.indexOf("monospaced") >= 0 || lower.indexOf("profont") >= 0 || lower.indexOf("console") >= 0 || lower.indexOf("typewriter") >= 0;
	}
}
