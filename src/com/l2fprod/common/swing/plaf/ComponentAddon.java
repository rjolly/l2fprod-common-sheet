// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   ComponentAddon.java

package com.l2fprod.common.swing.plaf;


// Referenced classes of package com.l2fprod.common.swing.plaf:
//			LookAndFeelAddons

public interface ComponentAddon {

	public abstract String getName();

	public abstract void initialize(LookAndFeelAddons lookandfeeladdons);

	public abstract void uninitialize(LookAndFeelAddons lookandfeeladdons);
}
