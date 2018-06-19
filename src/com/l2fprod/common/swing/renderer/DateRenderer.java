// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   DateRenderer.java

package com.l2fprod.common.swing.renderer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// Referenced classes of package com.l2fprod.common.swing.renderer:
//			DefaultCellRenderer

public class DateRenderer extends DefaultCellRenderer {

	private DateFormat dateFormat;

	public DateRenderer() {
		this(DateFormat.getDateInstance(3));
	}

	public DateRenderer(String formatString) {
		this(formatString, Locale.getDefault());
	}

	public DateRenderer(Locale l) {
		this(DateFormat.getDateInstance(3, l));
	}

	public DateRenderer(String formatString, Locale l) {
		this(((DateFormat) (new SimpleDateFormat(formatString, l))));
	}

	public DateRenderer(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public void setValue(Object value) {
		if (value == null)
			setText("");
		else
			setText(dateFormat.format((Date)value));
	}
}
