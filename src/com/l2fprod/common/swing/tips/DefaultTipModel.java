// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   DefaultTipModel.java

package com.l2fprod.common.swing.tips;

import com.l2fprod.common.swing.TipModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class DefaultTipModel
	implements TipModel {

	private List tips;

	public DefaultTipModel() {
		tips = new ArrayList();
	}

	public DefaultTipModel(com.l2fprod.common.swing.TipModel.Tip tips[]) {
		this(((Collection) (Arrays.asList(tips))));
	}

	public DefaultTipModel(Collection tips) {
		this.tips = new ArrayList();
		this.tips.addAll(tips);
	}

	public com.l2fprod.common.swing.TipModel.Tip getTipAt(int index) {
		return (com.l2fprod.common.swing.TipModel.Tip)tips.get(index);
	}

	public int getTipCount() {
		return tips.size();
	}

	public void add(com.l2fprod.common.swing.TipModel.Tip tip) {
		tips.add(tip);
	}

	public void remove(com.l2fprod.common.swing.TipModel.Tip tip) {
		tips.remove(tip);
	}

	public com.l2fprod.common.swing.TipModel.Tip[] getTips() {
		return (com.l2fprod.common.swing.TipModel.Tip[])tips.toArray(new com.l2fprod.common.swing.TipModel.Tip[tips.size()]);
	}

	public void setTips(com.l2fprod.common.swing.TipModel.Tip tips[]) {
		this.tips.clear();
		this.tips.addAll(Arrays.asList(tips));
	}
}
