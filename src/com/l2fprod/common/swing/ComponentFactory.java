// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   ComponentFactory.java

package com.l2fprod.common.swing;

import com.l2fprod.common.beans.editor.FixedButton;
import javax.swing.JButton;
import javax.swing.JComboBox;

public interface ComponentFactory {
	public static class DefaultComponentFactory
		implements ComponentFactory {

		public JButton createMiniButton() {
			return new FixedButton();
		}

		public JComboBox createComboBox() {
			return new JComboBox();
		}

		public DefaultComponentFactory() {
		}
	}

	public static class Helper {

		static ComponentFactory factory = new DefaultComponentFactory();

		public static ComponentFactory getFactory() {
			return factory;
		}

		public static void setFactory(ComponentFactory factory) {
			factory = factory;
		}


		public Helper() {
		}
	}


	public abstract JButton createMiniButton();

	public abstract JComboBox createComboBox();
}
