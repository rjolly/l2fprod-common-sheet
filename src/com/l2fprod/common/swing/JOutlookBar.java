// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JOutlookBar.java

package com.l2fprod.common.swing;

import com.l2fprod.common.swing.plaf.JOutlookBarAddon;
import com.l2fprod.common.swing.plaf.LookAndFeelAddons;
import com.l2fprod.common.swing.plaf.OutlookBarUI;
import java.awt.Color;
import java.awt.Component;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import javax.swing.Icon;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.plaf.TabbedPaneUI;

public class JOutlookBar extends JTabbedPane {
	private class ExtendedPage {

		Component component;
		int alignment;
		Color background;
		Color foreground;

		public void setTabAlignment(int alignment) {
			if (this.alignment != alignment) {
				this.alignment = alignment;
				firePropertyChange("tabPropertyChangedAtIndex", null, new Integer(getIndex()));
			}
		}

		public int getIndex() {
			return indexOfComponent(component);
		}

		public int getTabAlignment() {
			return alignment;
		}

		public Color getBackground() {
			return background;
		}

		public void setBackground(Color background) {
			this.background = background;
		}

		public Color getForeground() {
			return foreground;
		}

		public void setForeground(Color foreground) {
			this.foreground = foreground;
		}

		private ExtendedPage() {
			alignment = UIManager.getInt("OutlookBar.tabAlignment");
			background = null;
			foreground = null;
		}

	}


	public static final String UI_CLASS_ID = "OutlookBarUI";
	public static final String ANIMATED_CHANGED_KEY = "animated";
	protected Map extendedPages;
	private boolean animated;

	public JOutlookBar() {
		this(2);
	}

	public JOutlookBar(int tabPlacement) {
		super(tabPlacement, 0);
		animated = true;
		extendedPages = new WeakHashMap();
		updateUI();
	}

	public void updateUI() {
		setUI((OutlookBarUI)LookAndFeelAddons.getUI(this, com.l2fprod.common.swing.plaf.OutlookBarUI.class));
	}

	public void setUI(OutlookBarUI ui) {
		super.setUI((TabbedPaneUI)ui);
	}

	public String getUIClassID() {
		return "OutlookBarUI";
	}

	public void setAnimated(boolean animated) {
		if (this.animated != animated) {
			this.animated = animated;
			firePropertyChange("animated", !animated, animated);
		}
	}

	public boolean isAnimated() {
		return animated;
	}

	public JScrollPane makeScrollPane(Component component) {
		return ((OutlookBarUI)getUI()).makeScrollPane(component);
	}

	public void removeTabAt(int index) {
		checkIndex(index);
		removeExtendedPage(index);
		super.removeTabAt(index);
	}

	public void setAllTabsAlignment(int alignment) {
		ExtendedPage page;
		for (Iterator iter = extendedPages.values().iterator(); iter.hasNext(); page.setTabAlignment(alignment))
			page = (ExtendedPage)iter.next();

	}

	public void setAlignmentAt(int index, int alignment) {
		getExtendedPage(index).setTabAlignment(alignment);
	}

	public int getAlignmentAt(int index) {
		return getExtendedPage(index).getTabAlignment();
	}

	public void setTitleAt(int index, String title) {
		super.setTitleAt(index, title);
		firePropertyChange("tabPropertyChangedAtIndex", null, new Integer(index));
	}

	public void setIconAt(int index, Icon icon) {
		super.setIconAt(index, icon);
		firePropertyChange("tabPropertyChangedAtIndex", null, new Integer(index));
	}

	public Color getBackgroundAt(int index) {
		return getExtendedPage(index).getBackground();
	}

	public void setBackgroundAt(int index, Color background) {
		getExtendedPage(index).setBackground(background);
		firePropertyChange("tabPropertyChangedAtIndex", null, new Integer(index));
	}

	public Color getForegroundAt(int index) {
		return getExtendedPage(index).getForeground();
	}

	public void setForegroundAt(int index, Color foreground) {
		getExtendedPage(index).setForeground(foreground);
		firePropertyChange("tabPropertyChangedAtIndex", null, new Integer(index));
	}

	public void setToolTipTextAt(int index, String toolTipText) {
		super.setToolTipTextAt(index, toolTipText);
		firePropertyChange("tabPropertyChangedAtIndex", null, new Integer(index));
	}

	public void setDisplayedMnemonicIndexAt(int tabIndex, int mnemonicIndex) {
		super.setDisplayedMnemonicIndexAt(tabIndex, mnemonicIndex);
		firePropertyChange("tabPropertyChangedAtIndex", null, new Integer(tabIndex));
	}

	public void setMnemonicAt(int index, int mnemonic) {
		super.setMnemonicAt(index, mnemonic);
		firePropertyChange("tabPropertyChangedAtIndex", null, new Integer(index));
	}

	public void setDisabledIconAt(int index, Icon disabledIcon) {
		super.setDisabledIconAt(index, disabledIcon);
		firePropertyChange("tabPropertyChangedAtIndex", null, new Integer(index));
	}

	public void setEnabledAt(int index, boolean enabled) {
		super.setEnabledAt(index, enabled);
		firePropertyChange("tabPropertyChangedAtIndex", null, new Integer(index));
	}

	protected void addImpl(Component comp, Object constraints, int index) {
		if (index != -1) {
			super.addImpl(comp, constraints, index);
		} else {
			int pageIndex = indexOfComponent(comp);
			if (pageIndex == -1)
				super.addImpl(comp, constraints, index);
			else
				super.addImpl(comp, constraints, pageIndex * 2);
		}
	}

	protected void removeExtendedPage(int index) {
		Component component = getComponentAt(index);
		extendedPages.remove(component);
	}

	protected ExtendedPage getExtendedPage(int index) {
		checkIndex(index);
		Component component = getComponentAt(index);
		ExtendedPage page = (ExtendedPage)extendedPages.get(component);
		if (page == null) {
			page = new ExtendedPage();
			page.component = component;
			extendedPages.put(component, page);
		}
		return page;
	}

	private void checkIndex(int index) {
		if (index < 0 || index >= getTabCount())
			throw new IndexOutOfBoundsException("Index: " + index + ", Tab count: " + getTabCount());
		else
			return;
	}

	static  {
		LookAndFeelAddons.contribute(new JOutlookBarAddon());
	}

}
