// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   IconPool.java

package com.l2fprod.common.swing;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class IconPool {

	private static IconPool iconPool = new IconPool();
	private Map pool;

	public IconPool() {
		pool = new HashMap();
	}

	public static IconPool shared() {
		return iconPool;
	}

	public Icon get(String url) {
		StackTraceElement stacks[] = (new Exception()).getStackTrace();
		Class callerClazz = Class.forName(stacks[1].getClassName());
		return get(callerClazz.getResource(url));
		ClassNotFoundException e;
		e;
		throw new RuntimeException(e);
	}

	public synchronized Icon get(URL url) {
		if (url == null)
			return null;
		Icon icon = (Icon)pool.get(url.toString());
		if (icon == null) {
			icon = new ImageIcon(url);
			pool.put(url.toString(), icon);
		}
		return icon;
	}

	public synchronized void clear() {
		pool.clear();
	}

}
