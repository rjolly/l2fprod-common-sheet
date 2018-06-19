// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   LazyMutableTreeNode.java

package com.l2fprod.common.swing.tree;

import javax.swing.tree.DefaultMutableTreeNode;

public abstract class LazyMutableTreeNode extends DefaultMutableTreeNode {

	private boolean loaded;

	public LazyMutableTreeNode() {
		loaded = false;
	}

	public LazyMutableTreeNode(Object userObject) {
		super(userObject);
		loaded = false;
	}

	public LazyMutableTreeNode(Object userObject, boolean allowsChildren) {
		super(userObject, allowsChildren);
		loaded = false;
	}

	public int getChildCount() {
		synchronized (this) {
			if (!loaded) {
				loaded = true;
				loadChildren();
			}
		}
		return super.getChildCount();
	}

	public void clear() {
		removeAllChildren();
		loaded = false;
	}

	public boolean isLoaded() {
		return loaded;
	}

	protected abstract void loadChildren();
}
