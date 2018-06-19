// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   DefaultTip.java

package com.l2fprod.common.swing.tips;

import com.l2fprod.common.swing.TipModel;

public class DefaultTip
	implements com.l2fprod.common.swing.TipModel.Tip {

	private String name;
	private Object tip;

	public DefaultTip() {
	}

	public DefaultTip(String name, Object tip) {
		this.name = name;
		this.tip = tip;
	}

	public Object getTip() {
		return tip;
	}

	public void setTip(Object tip) {
		this.tip = tip;
	}

	public String getTipName() {
		return name;
	}

	public void setTipName(String name) {
		this.name = name;
	}

	public String toString() {
		return getTipName();
	}
}
