// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   TipOfTheDayUI.java

package com.l2fprod.common.swing.plaf;

import com.l2fprod.common.swing.JTipOfTheDay;
import java.awt.Component;
import javax.swing.JDialog;
import javax.swing.plaf.ComponentUI;

public abstract class TipOfTheDayUI extends ComponentUI {

	public TipOfTheDayUI() {
	}

	public abstract JDialog createDialog(Component component, com.l2fprod.common.swing.JTipOfTheDay.ShowOnStartupChoice showonstartupchoice);
}
