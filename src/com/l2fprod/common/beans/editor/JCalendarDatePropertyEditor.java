// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JCalendarDatePropertyEditor.java

package com.l2fprod.common.beans.editor;

import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// Referenced classes of package com.l2fprod.common.beans.editor:
//			AbstractPropertyEditor

public class JCalendarDatePropertyEditor extends AbstractPropertyEditor {

	public JCalendarDatePropertyEditor() {
		editor = new JDateChooser();
	}

	public JCalendarDatePropertyEditor(String dateFormatString, Locale locale) {
		editor = new JDateChooser();
		((JDateChooser)editor).setDateFormatString(dateFormatString);
		((JDateChooser)editor).setLocale(locale);
	}

	public JCalendarDatePropertyEditor(Locale locale) {
		editor = new JDateChooser();
		((JDateChooser)editor).setLocale(locale);
	}

	public Object getValue() {
		return ((JDateChooser)editor).getDate();
	}

	public void setValue(Object value) {
		if (value != null)
			((JDateChooser)editor).setDate((Date)value);
	}

	public String getAsText() {
		Date date = (Date)getValue();
		SimpleDateFormat formatter = new SimpleDateFormat(getDateFormatString());
		String s = formatter.format(date);
		return s;
	}

	public void setDateFormatString(String dateFormatString) {
		((JDateChooser)editor).setDateFormatString(dateFormatString);
	}

	public String getDateFormatString() {
		return ((JDateChooser)editor).getDateFormatString();
	}

	public void setLocale(Locale l) {
		((JDateChooser)editor).setLocale(l);
	}

	public Locale getLocale() {
		return ((JDateChooser)editor).getLocale();
	}
}
