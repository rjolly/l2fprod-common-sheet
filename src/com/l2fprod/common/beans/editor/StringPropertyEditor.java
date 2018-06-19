// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   StringPropertyEditor.java

package com.l2fprod.common.beans.editor;

import com.l2fprod.common.swing.LookAndFeelTweaks;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

// Referenced classes of package com.l2fprod.common.beans.editor:
//			AbstractPropertyEditor

public class StringPropertyEditor extends AbstractPropertyEditor {

	public StringPropertyEditor() {
		editor = new JTextField();
		((JTextField)editor).setBorder(LookAndFeelTweaks.EMPTY_BORDER);
	}

	public Object getValue() {
		return ((JTextComponent)editor).getText();
	}

	public void setValue(Object value) {
		if (value == null)
			((JTextComponent)editor).setText("");
		else
			((JTextComponent)editor).setText(String.valueOf(value));
	}
}
