// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   DefaultFontChooserModel.java

package com.l2fprod.common.swing;

import java.awt.GraphicsEnvironment;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.SortedMap;

// Referenced classes of package com.l2fprod.common.swing:
//			FontChooserModel

public class DefaultFontChooserModel
	implements FontChooserModel {

	public static final int DEFAULT_FONT_SIZES[] = {
		6, 8, 10, 11, 12, 14, 16, 18, 20, 22, 
		24, 26, 28, 32, 40, 48, 56, 64, 72
	};
	protected String fontFamilies[];
	private String charSets[];
	private int defaultFontSizes[];
	private String previewMessage;

	public DefaultFontChooserModel() {
		ResourceBundle bundle = ResourceBundle.getBundle((com.l2fprod.common.swing.plaf.FontChooserUI.class).getName() + "RB");
		setPreviewMessage(bundle.getString("FontChooserUI.previewText"));
		String fontFamilies[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		Arrays.sort(fontFamilies);
		setFontFamilies(fontFamilies);
		SortedMap map = Charset.availableCharsets();
		String charSets[] = new String[map.size()];
		int i = 0;
		for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
			charSets[i] = (String)iter.next();
			i++;
		}

		setCharSets(charSets);
		setDefaultFontSizes(DEFAULT_FONT_SIZES);
	}

	public void setFontFamilies(String fontFamilies[]) {
		this.fontFamilies = fontFamilies;
	}

	public String[] getFontFamilies(String charSetName) {
		return fontFamilies;
	}

	public void setDefaultFontSizes(int defaultFontSizes[]) {
		this.defaultFontSizes = defaultFontSizes;
	}

	public int[] getDefaultSizes() {
		return defaultFontSizes;
	}

	public void setCharSets(String charSets[]) {
		this.charSets = charSets;
	}

	public String[] getCharSets() {
		return charSets;
	}

	public void setPreviewMessage(String previewMessage) {
		this.previewMessage = previewMessage;
	}

	public String getPreviewMessage(String charSetName) {
		return previewMessage;
	}

}
