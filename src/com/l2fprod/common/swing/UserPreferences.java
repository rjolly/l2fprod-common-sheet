// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   UserPreferences.java

package com.l2fprod.common.swing;

import com.l2fprod.common.util.converter.ConverterRegistry;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.prefs.Preferences;
import javax.swing.JFileChooser;
import javax.swing.JSplitPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

public class UserPreferences {
	private static class TextListener
		implements DocumentListener {

		private JTextComponent text;

		public void changedUpdate(DocumentEvent e) {
			store();
		}

		public void insertUpdate(DocumentEvent e) {
			store();
		}

		public void removeUpdate(DocumentEvent e) {
			store();
		}

		void restore() {
			Preferences prefs = UserPreferences.node().node("JTextComponent");
			text.setText(prefs.get(text.getName(), ""));
		}

		void store() {
			Preferences prefs = UserPreferences.node().node("JTextComponent");
			prefs.put(text.getName(), text.getText());
		}

		public TextListener(JTextComponent text) {
			this.text = text;
			restore();
			text.getDocument().addDocumentListener(this);
		}
	}


	private static ComponentListener windowDimension = new ComponentAdapter() {

		public void componentMoved(ComponentEvent e) {
			store((Window)e.getComponent());
		}

		public void componentResized(ComponentEvent e) {
			store((Window)e.getComponent());
		}

		private void store(Window w) {
			String bounds = (String)ConverterRegistry.instance().convert(UserPreferences.class$java$lang$String != null ? UserPreferences.class$java$lang$String : (UserPreferences.class$java$lang$String = UserPreferences._mthclass$("java.lang.String")), w.getBounds());
			UserPreferences.node().node("Windows").put(w.getName() + ".bounds", bounds);
		}

	};
	private static PropertyChangeListener splitPaneListener = new PropertyChangeListener() {

		public void propertyChange(PropertyChangeEvent evt) {
			JSplitPane split = (JSplitPane)evt.getSource();
			UserPreferences.node().node("JSplitPane").put(split.getName() + ".dividerLocation", String.valueOf(split.getDividerLocation()));
		}

	};
	static Class class$java$lang$String; /* synthetic field */

	public UserPreferences() {
	}

	public static JFileChooser getDefaultFileChooser() {
		return getFileChooser("default");
	}

	public static JFileChooser getDefaultDirectoryChooser() {
		return getDirectoryChooser("default");
	}

	public static JFileChooser getFileChooser(String id) {
		JFileChooser chooser = new JFileChooser();
		track(chooser, "FileChooser." + id + ".path");
		return chooser;
	}

	public static JFileChooser getDirectoryChooser(String id) {
		JFileChooser chooser;
		try {
			Class directoryChooserClass = Class.forName("com.l2fprod.common.swing.JDirectoryChooser");
			chooser = (JFileChooser)directoryChooserClass.newInstance();
		}
		catch (Exception e) {
			chooser = new JFileChooser();
			chooser.setFileSelectionMode(1);
		}
		track(chooser, "DirectoryChooser." + id + ".path");
		return chooser;
	}

	private static void track(JFileChooser chooser, final String key) {
		String path = node().get(key, null);
		if (path != null) {
			File file = new File(path);
			if (file.exists())
				chooser.setCurrentDirectory(file);
		}
		PropertyChangeListener trackPath = new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getNewValue() instanceof File)
					UserPreferences.node().put(key, ((File)evt.getNewValue()).getAbsolutePath());
			}

		};
		chooser.addPropertyChangeListener("directoryChanged", trackPath);
	}

	public static void track(Window window) {
		Preferences prefs = node().node("Windows");
		String bounds = prefs.get(window.getName() + ".bounds", null);
		if (bounds != null) {
			Rectangle rect = (Rectangle)ConverterRegistry.instance().convert(java.awt.Rectangle.class, bounds);
			window.setBounds(rect);
		}
		window.addComponentListener(windowDimension);
	}

	public static void track(JTextComponent text) {
		new TextListener(text);
	}

	public static void track(JSplitPane split) {
		Preferences prefs = node().node("JSplitPane");
		int dividerLocation = prefs.getInt(split.getName() + ".dividerLocation", -1);
		if (dividerLocation >= 0)
			split.setDividerLocation(dividerLocation);
		split.addPropertyChangeListener("dividerLocation", splitPaneListener);
	}

	private static Preferences node() {
		return Preferences.userNodeForPackage(com.l2fprod.common.swing.UserPreferences.class).node("UserPreferences");
	}


}
