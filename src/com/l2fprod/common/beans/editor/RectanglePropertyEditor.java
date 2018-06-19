// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   RectanglePropertyEditor.java

package com.l2fprod.common.beans.editor;

import com.l2fprod.common.util.converter.ConverterRegistry;

// Referenced classes of package com.l2fprod.common.beans.editor:
//			StringConverterPropertyEditor

public class RectanglePropertyEditor extends StringConverterPropertyEditor {

	public RectanglePropertyEditor() {
	}

	protected Object convertFromString(String text) {
		return ConverterRegistry.instance().convert(java.awt.Rectangle.class, text);
	}
}
