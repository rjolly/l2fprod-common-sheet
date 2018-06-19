// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   NumberPropertyEditor.java

package com.l2fprod.common.beans.editor;

import com.l2fprod.common.swing.LookAndFeelTweaks;
import com.l2fprod.common.util.converter.ConverterRegistry;
import com.l2fprod.common.util.converter.NumberConverters;
import java.lang.reflect.Constructor;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

// Referenced classes of package com.l2fprod.common.beans.editor:
//			AbstractPropertyEditor

public class NumberPropertyEditor extends AbstractPropertyEditor {

	private final Class type;
	private Object lastGoodValue;

	public NumberPropertyEditor(Class type) {
		if (!(java.lang.Number.class).isAssignableFrom(type)) {
			throw new IllegalArgumentException("type must be a subclass of Number");
		} else {
			editor = new JFormattedTextField();
			this.type = type;
			((JFormattedTextField)editor).setValue(getDefaultValue());
			((JFormattedTextField)editor).setBorder(LookAndFeelTweaks.EMPTY_BORDER);
			java.text.NumberFormat format = NumberConverters.getDefaultFormat();
			((JFormattedTextField)editor).setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(format)));
			return;
		}
	}

	public Object getValue() {
		String text = ((JTextField)editor).getText();
		if (text == null || text.trim().length() == 0)
			return getDefaultValue();
		text = text.replace(',', '.');
		StringBuffer number = new StringBuffer();
		number.ensureCapacity(text.length());
		int i = 0;
		for (int c = text.length(); i < c; i++) {
			char character = text.charAt(i);
			if ('.' == character || '-' == character || (java.lang.Double.class).equals(type) && 'E' == character || (java.lang.Float.class).equals(type) && 'E' == character || Character.isDigit(character)) {
				number.append(character);
				continue;
			}
			if (' ' != character)
				break;
		}

		try {
			lastGoodValue = ConverterRegistry.instance().convert(type, number.toString());
		}
		catch (Exception e) {
			UIManager.getLookAndFeel().provideErrorFeedback(editor);
		}
		return lastGoodValue;
	}

	public void setValue(Object value) {
		if (value instanceof Number)
			((JFormattedTextField)editor).setText(value.toString());
		else
			((JFormattedTextField)editor).setValue(getDefaultValue());
		lastGoodValue = value;
	}

	private Object getDefaultValue() {
		return type.getConstructor(new Class[] {
			java.lang.String.class
		}).newInstance(new Object[] {
			"0"
		});
	}
}
