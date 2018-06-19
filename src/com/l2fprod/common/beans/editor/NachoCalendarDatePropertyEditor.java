// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   NachoCalendarDatePropertyEditor.java

package com.l2fprod.common.beans.editor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import net.sf.nachocalendar.CalendarFactory;
import net.sf.nachocalendar.components.DateField;

// Referenced classes of package com.l2fprod.common.beans.editor:
//			AbstractPropertyEditor

public class NachoCalendarDatePropertyEditor extends AbstractPropertyEditor {

	private String dateFormatString;

	public NachoCalendarDatePropertyEditor() {
		editor = CalendarFactory.createDateField();
		((DateField)editor).setValue(new Date());
	}

	public NachoCalendarDatePropertyEditor(String dateFormatString, Locale locale) {
		editor = CalendarFactory.createDateField();
		((DateField)editor).setValue(new Date());
	}

	public NachoCalendarDatePropertyEditor(Locale locale) {
		editor = CalendarFactory.createDateField();
		((DateField)editor).setValue(new Date());
		((DateField)editor).setLocale(locale);
	}

	public Object getValue() {
		return ((DateField)editor).getValue();
	}

	public void setValue(Object value) {
		if (value != null)
			((DateField)editor).setValue(value);
	}

	public String getAsText() {
		Date date = (Date)getValue();
		SimpleDateFormat formatter = new SimpleDateFormat(getDateFormatString());
		String s = formatter.format(date);
		return s;
	}

	public void setDateFormatString(String dateFormatString) {
		this.dateFormatString = dateFormatString;
	}

	public String getDateFormatString() {
		return dateFormatString;
	}

	public void setLocale(Locale l) {
		((DateField)editor).setLocale(l);
	}

	public Locale getLocale() {
		return ((DateField)editor).getLocale();
	}
}
