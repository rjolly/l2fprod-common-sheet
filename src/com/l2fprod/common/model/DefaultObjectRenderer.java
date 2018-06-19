// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   DefaultObjectRenderer.java

package com.l2fprod.common.model;

import com.l2fprod.common.util.ResourceManager;
import com.l2fprod.common.util.converter.ConverterRegistry;
import java.io.File;

// Referenced classes of package com.l2fprod.common.model:
//			HasId, TitledObject, ObjectRenderer

public class DefaultObjectRenderer
	implements ObjectRenderer {

	private boolean idVisible;

	public DefaultObjectRenderer() {
		idVisible = false;
	}

	public void setIdVisible(boolean b) {
		idVisible = b;
	}

	public String getText(Object object) {
		if (object == null)
			return null;
		return (String)ConverterRegistry.instance().convert(java.lang.String.class, object);
		IllegalArgumentException e;
		e;
		if (object instanceof Boolean)
			return Boolean.TRUE.equals(object) ? ResourceManager.common().getString("true") : ResourceManager.common().getString("false");
		if (object instanceof File)
			return ((File)object).getAbsolutePath();
		StringBuffer buffer = new StringBuffer();
		if (idVisible && (object instanceof HasId))
			buffer.append(((HasId)object).getId());
		if (object instanceof TitledObject)
			buffer.append(((TitledObject)object).getTitle());
		if (!(object instanceof HasId) && !(object instanceof TitledObject))
			buffer.append(String.valueOf(object));
		return buffer.toString();
	}
}
