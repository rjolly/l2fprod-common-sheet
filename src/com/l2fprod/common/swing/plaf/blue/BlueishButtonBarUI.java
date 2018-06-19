// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   BlueishButtonBarUI.java

package com.l2fprod.common.swing.plaf.blue;

import com.l2fprod.common.swing.JButtonBar;
import com.l2fprod.common.swing.plaf.ButtonBarButtonUI;
import com.l2fprod.common.swing.plaf.basic.BasicButtonBarUI;
import java.awt.Color;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;

// Referenced classes of package com.l2fprod.common.swing.plaf.blue:
//			BlueishButtonUI

public class BlueishButtonBarUI extends BasicButtonBarUI {
	static class BlueishButtonBarButtonUI extends BlueishButtonUI
		implements ButtonBarButtonUI {

		BlueishButtonBarButtonUI() {
		}
	}


	public BlueishButtonBarUI() {
	}

	public static ComponentUI createUI(JComponent c) {
		return new BlueishButtonBarUI();
	}

	protected void installDefaults() {
		javax.swing.border.Border b = bar.getBorder();
		if (b == null || (b instanceof UIResource))
			bar.setBorder(new BorderUIResource(new CompoundBorder(BorderFactory.createLineBorder(UIManager.getColor("controlDkShadow")), BorderFactory.createEmptyBorder(1, 1, 1, 1))));
		Color color = bar.getBackground();
		if (color == null || (color instanceof ColorUIResource)) {
			bar.setOpaque(true);
			bar.setBackground(new ColorUIResource(Color.white));
		}
	}

	public void installButtonBarUI(AbstractButton button) {
		button.setUI(new BlueishButtonBarButtonUI());
		button.setHorizontalTextPosition(0);
		button.setVerticalTextPosition(3);
		button.setOpaque(false);
	}
}
