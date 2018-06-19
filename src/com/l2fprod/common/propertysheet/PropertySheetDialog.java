// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   PropertySheetDialog.java

package com.l2fprod.common.propertysheet;

import com.l2fprod.common.swing.BaseDialog;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

public class PropertySheetDialog extends BaseDialog {

	public PropertySheetDialog() throws HeadlessException {
	}

	public PropertySheetDialog(Dialog owner) throws HeadlessException {
		super(owner);
	}

	public PropertySheetDialog(Dialog owner, boolean modal) throws HeadlessException {
		super(owner, modal);
	}

	public PropertySheetDialog(Frame owner) throws HeadlessException {
		super(owner);
	}

	public PropertySheetDialog(Frame owner, boolean modal) throws HeadlessException {
		super(owner, modal);
	}

	public PropertySheetDialog(Dialog owner, String title) throws HeadlessException {
		super(owner, title);
	}

	public PropertySheetDialog(Dialog owner, String title, boolean modal) throws HeadlessException {
		super(owner, title, modal);
	}

	public PropertySheetDialog(Frame owner, String title) throws HeadlessException {
		super(owner, title);
	}

	public PropertySheetDialog(Frame owner, String title, boolean modal) throws HeadlessException {
		super(owner, title, modal);
	}

	public PropertySheetDialog(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) throws HeadlessException {
		super(owner, title, modal, gc);
	}

	public PropertySheetDialog(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
	}
}
