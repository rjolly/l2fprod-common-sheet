// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   BaseObject.java

package com.l2fprod.common.model;

import java.util.Observable;

// Referenced classes of package com.l2fprod.common.model:
//			HasId

public class BaseObject extends Observable
	implements HasId {

	private Object id;

	public BaseObject() {
	}

	public void setId(Object id) {
		this.id = id;
	}

	public Object getId() {
		return id;
	}

	public String toString() {
		return super.toString() + "[" + paramString() + "]";
	}

	protected String paramString() {
		return "id=" + getId();
	}
}
