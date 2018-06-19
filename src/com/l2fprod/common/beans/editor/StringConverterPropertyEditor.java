// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   StringConverterPropertyEditor.java

package com.l2fprod.common.beans.editor;

import com.l2fprod.common.swing.LookAndFeelTweaks;
import com.l2fprod.common.util.converter.ConverterRegistry;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

// Referenced classes of package com.l2fprod.common.beans.editor:
//			AbstractPropertyEditor

public abstract class StringConverterPropertyEditor extends AbstractPropertyEditor {

	private Object oldValue;

	public StringConverterPropertyEditor() {
		editor = new JTextField();
		((JTextField)editor).setBorder(LookAndFeelTweaks.EMPTY_BORDER);
	}

	public Object getValue() {
		String text;
		text = ((JTextComponent)editor).getText();
		if (text == null || text.trim().length() == 0)
			return null;
		return convertFromString(text.trim());
		Exception e;
		e;
		return oldValue;
	}

	public void setValue(Object value) {
		if (value == null) {
			((JTextComponent)editor).setText("");
		} else {
			oldValue = value;
			((JTextComponent)editor).setText(convertToString(value));
		}
	}

	protected abstract Object convertFromString(String s);

	protected String convertToString(Object value) {
		return (String)ConverterRegistry.instance().convert(java.lang.String.class, value);
	}
}
