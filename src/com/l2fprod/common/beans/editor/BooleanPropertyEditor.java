// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   BooleanPropertyEditor.java

package com.l2fprod.common.beans.editor;

import com.l2fprod.common.util.ResourceManager;

// Referenced classes of package com.l2fprod.common.beans.editor:
//			ComboBoxPropertyEditor

public class BooleanPropertyEditor extends ComboBoxPropertyEditor {

	public BooleanPropertyEditor() {
		Object values[] = {
			new ComboBoxPropertyEditor.Value(Boolean.TRUE, ResourceManager.common().getString("true")), new ComboBoxPropertyEditor.Value(Boolean.FALSE, ResourceManager.common().getString("false"))
		};
		setAvailableValues(values);
	}
}
