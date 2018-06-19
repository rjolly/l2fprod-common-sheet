// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JDirectoryChooserAddon.java

package com.l2fprod.common.swing.plaf;

import java.util.Arrays;
import java.util.List;

// Referenced classes of package com.l2fprod.common.swing.plaf:
//			AbstractComponentAddon, LookAndFeelAddons

public class JDirectoryChooserAddon extends AbstractComponentAddon {

	public JDirectoryChooserAddon() {
		super("JDirectoryChooser");
	}

	protected void addBasicDefaults(LookAndFeelAddons addon, List defaults) {
		defaults.addAll(Arrays.asList(new Object[] {
			"l2fprod/DirectoryChooserUI", "com.l2fprod.common.swing.plaf.windows.WindowsDirectoryChooserUI"
		}));
		addResource(defaults, "com.l2fprod.common.swing.plaf.DirectoryChooserUIRB");
	}
}
