// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   TipLoader.java

package com.l2fprod.common.swing.tips;

import com.l2fprod.common.swing.TipModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

// Referenced classes of package com.l2fprod.common.swing.tips:
//			DefaultTip, DefaultTipModel

public class TipLoader {

	public TipLoader() {
	}

	public static TipModel load(Properties props) {
		List tips = new ArrayList();
		int count = 1;
		do {
			String nameKey = "tip." + count + ".name";
			String nameValue = props.getProperty(nameKey);
			String descriptionKey = "tip." + count + ".description";
			String descriptionValue = props.getProperty(descriptionKey);
			if (nameValue != null && descriptionValue == null)
				throw new IllegalArgumentException("No description for name " + nameValue);
			if (descriptionValue != null) {
				DefaultTip tip = new DefaultTip(nameValue, descriptionValue);
				tips.add(tip);
				count++;
			} else {
				return new DefaultTipModel(tips);
			}
		} while (true);
	}
}
