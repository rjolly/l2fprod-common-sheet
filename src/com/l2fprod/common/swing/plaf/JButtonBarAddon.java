// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JButtonBarAddon.java

package com.l2fprod.common.swing.plaf;

import java.util.Arrays;
import java.util.List;

// Referenced classes of package com.l2fprod.common.swing.plaf:
//			AbstractComponentAddon, LookAndFeelAddons

public class JButtonBarAddon extends AbstractComponentAddon {

	public JButtonBarAddon() {
		super("JButtonBar");
	}

	protected void addBasicDefaults(LookAndFeelAddons addon, List defaults) {
		defaults.addAll(Arrays.asList(new Object[] {
			"ButtonBarUI", "com.l2fprod.common.swing.plaf.basic.BasicButtonBarUI"
		}));
	}

	protected void addMetalDefaults(LookAndFeelAddons addon, List defaults) {
		defaults.addAll(Arrays.asList(new Object[] {
			"ButtonBarUI", "com.l2fprod.common.swing.plaf.blue.BlueishButtonBarUI"
		}));
	}

	protected void addWindowsDefaults(LookAndFeelAddons addon, List defaults) {
		defaults.addAll(Arrays.asList(new Object[] {
			"ButtonBarUI", "com.l2fprod.common.swing.plaf.blue.BlueishButtonBarUI"
		}));
	}
}
