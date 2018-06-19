// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   PropertyEditorFactory.java

package com.l2fprod.common.propertysheet;

import java.beans.PropertyEditor;

// Referenced classes of package com.l2fprod.common.propertysheet:
//			Property

public interface PropertyEditorFactory {

	public abstract PropertyEditor createPropertyEditor(Property property);
}
