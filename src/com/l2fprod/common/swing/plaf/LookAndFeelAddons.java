// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   LookAndFeelAddons.java

package com.l2fprod.common.swing.plaf;

import com.l2fprod.common.util.OS;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalLookAndFeel;

// Referenced classes of package com.l2fprod.common.swing.plaf:
//			ComponentAddon

public class LookAndFeelAddons {
	private static class UpdateAddon
		implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {
			try {
				LookAndFeelAddons.setAddon(LookAndFeelAddons.getBestMatchAddonClassName());
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		private UpdateAddon() {
		}

	}


	private static List contributedComponents = new ArrayList();
	private static final Object APPCONTEXT_INITIALIZED = new Object();
	private static boolean trackingChanges = false;
	private static PropertyChangeListener changeListener;
	private static LookAndFeelAddons currentAddon;

	public LookAndFeelAddons() {
	}

	public void initialize() {
		ComponentAddon addon;
		for (Iterator iter = contributedComponents.iterator(); iter.hasNext(); addon.initialize(this))
			addon = (ComponentAddon)iter.next();

	}

	public void uninitialize() {
		ComponentAddon addon;
		for (Iterator iter = contributedComponents.iterator(); iter.hasNext(); addon.uninitialize(this))
			addon = (ComponentAddon)iter.next();

	}

	public void loadDefaults(Object keysAndValues[]) {
		for (int i = keysAndValues.length - 2; i >= 0; i -= 2)
			if (UIManager.getLookAndFeelDefaults().get(keysAndValues[i]) == null)
				UIManager.getLookAndFeelDefaults().put(keysAndValues[i], keysAndValues[i + 1]);

	}

	public void unloadDefaults(Object keysAndValues[]) {
		int i = 0;
		for (int c = keysAndValues.length; i < c; i += 2)
			UIManager.getLookAndFeelDefaults().put(keysAndValues[i], null);

	}

	public static void setAddon(String addonClassName) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		setAddon(Class.forName(addonClassName));
	}

	public static void setAddon(Class addonClass) throws InstantiationException, IllegalAccessException {
		LookAndFeelAddons addon = (LookAndFeelAddons)addonClass.newInstance();
		setAddon(addon);
	}

	public static void setAddon(LookAndFeelAddons addon) {
		if (currentAddon != null)
			currentAddon.uninitialize();
		addon.initialize();
		currentAddon = addon;
		UIManager.put(APPCONTEXT_INITIALIZED, Boolean.TRUE);
	}

	public static LookAndFeelAddons getAddon() {
		return currentAddon;
	}

	public static String getBestMatchAddonClassName() {
		String lnf = UIManager.getLookAndFeel().getClass().getName();
		String addon;
		if (UIManager.getCrossPlatformLookAndFeelClassName().equals(lnf))
			addon = (com.l2fprod.common.swing.plaf.metal.MetalLookAndFeelAddons.class).getName();
		else
		if (UIManager.getSystemLookAndFeelClassName().equals(lnf))
			addon = getSystemAddonClassName();
		else
		if ("com.sun.java.swing.plaf.windows.WindowsLookAndFeel".equals(lnf) || "com.jgoodies.looks.windows.WindowsLookAndFeel".equals(lnf)) {
			if (OS.isUsingWindowsVisualStyles())
				addon = (com.l2fprod.common.swing.plaf.windows.WindowsLookAndFeelAddons.class).getName();
			else
				addon = (com.l2fprod.common.swing.plaf.windows.WindowsClassicLookAndFeelAddons.class).getName();
		} else
		if ("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel".equals(lnf))
			addon = (com.l2fprod.common.swing.plaf.windows.WindowsClassicLookAndFeelAddons.class).getName();
		else
		if (UIManager.getLookAndFeel() instanceof MetalLookAndFeel)
			addon = (com.l2fprod.common.swing.plaf.metal.MetalLookAndFeelAddons.class).getName();
		else
			addon = getSystemAddonClassName();
		return addon;
	}

	public static String getSystemAddonClassName() {
		String addon = (com.l2fprod.common.swing.plaf.windows.WindowsClassicLookAndFeelAddons.class).getName();
		if (OS.isMacOSX())
			addon = (com.l2fprod.common.swing.plaf.aqua.AquaLookAndFeelAddons.class).getName();
		else
		if (OS.isWindows())
			if (OS.isUsingWindowsVisualStyles())
				addon = (com.l2fprod.common.swing.plaf.windows.WindowsLookAndFeelAddons.class).getName();
			else
				addon = (com.l2fprod.common.swing.plaf.windows.WindowsClassicLookAndFeelAddons.class).getName();
		return addon;
	}

	public static void contribute(ComponentAddon component) {
		contributedComponents.add(component);
		if (currentAddon != null)
			component.initialize(currentAddon);
	}

	public static void uncontribute(ComponentAddon component) {
		contributedComponents.remove(component);
		if (currentAddon != null)
			component.uninitialize(currentAddon);
	}

	public static ComponentUI getUI(JComponent component, Class expectedUIClass) {
		String realUI;
		Method createUIMethod;
		maybeInitialize();
		String uiClassname = (String)UIManager.get(component.getUIClassID());
		try {
			Class uiClass = Class.forName(uiClassname);
			UIManager.put(uiClassname, uiClass);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		ComponentUI ui = UIManager.getUI(component);
		if (expectedUIClass.isInstance(ui))
			return ui;
		realUI = ui.getClass().getName();
		Class realUIClass;
		try {
			realUIClass = expectedUIClass.getClassLoader().loadClass(realUI);
		}
		catch (ClassNotFoundException e) {
			throw new RuntimeException("Failed to load class " + realUI, e);
		}
		createUIMethod = null;
		try {
			createUIMethod = realUIClass.getMethod("createUI", new Class[] {
				javax.swing.JComponent.class
			});
		}
		catch (NoSuchMethodException e1) {
			throw new RuntimeException("Class " + realUI + " has no method createUI(JComponent)");
		}
		return (ComponentUI)createUIMethod.invoke(null, new Object[] {
			component
		});
		Exception e2;
		e2;
		throw new RuntimeException("Failed to invoke " + realUI + "#createUI(JComponent)");
	}

	private static synchronized void maybeInitialize() {
		if (currentAddon != null) {
			UIManager.getLookAndFeelDefaults();
			if (!UIManager.getBoolean(APPCONTEXT_INITIALIZED))
				setAddon(currentAddon);
		}
	}

	public static synchronized void setTrackingLookAndFeelChanges(boolean tracking) {
		if (trackingChanges != tracking) {
			if (tracking) {
				if (changeListener == null)
					changeListener = new UpdateAddon();
				UIManager.addPropertyChangeListener(changeListener);
			} else {
				if (changeListener != null)
					UIManager.removePropertyChangeListener(changeListener);
				changeListener = null;
			}
			trackingChanges = tracking;
		}
	}

	public static synchronized boolean isTrackingLookAndFeelChanges() {
		return trackingChanges;
	}

	static  {
		String addonClassname = getBestMatchAddonClassName();
		try {
			addonClassname = System.getProperty("swing.addon", addonClassname);
		}
		catch (SecurityException e) { }
		try {
			setAddon(addonClassname);
			setTrackingLookAndFeelChanges(true);
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
