// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JTaskPaneGroup.java

package com.l2fprod.common.swing;

import com.l2fprod.common.swing.plaf.JTaskPaneGroupAddon;
import com.l2fprod.common.swing.plaf.LookAndFeelAddons;
import com.l2fprod.common.swing.plaf.TaskPaneGroupUI;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.UIManager;

// Referenced classes of package com.l2fprod.common.swing:
//			JCollapsiblePane

public class JTaskPaneGroup extends JPanel
	implements JCollapsiblePane.JCollapsiblePaneContainer {

	public static final String UI_CLASS_ID = "TaskPaneGroupUI";
	public static final String EXPANDED_CHANGED_KEY = "expanded";
	public static final String SCROLL_ON_EXPAND_CHANGED_KEY = "scrollOnExpand";
	public static final String TITLE_CHANGED_KEY = "title";
	public static final String ICON_CHANGED_KEY = "icon";
	public static final String SPECIAL_CHANGED_KEY = "special";
	public static final String ANIMATED_CHANGED_KEY = "animated";
	private String title;
	private Icon icon;
	private boolean special;
	private boolean expanded;
	private boolean scrollOnExpand;
	private JCollapsiblePane collapsePane;

	public JTaskPaneGroup() {
		expanded = true;
		collapsePane = new JCollapsiblePane();
		super.setLayout(new BorderLayout(0, 0));
		super.addImpl(collapsePane, "Center", -1);
		updateUI();
		setFocusable(true);
		setOpaque(false);
		setAnimated(!Boolean.FALSE.equals(UIManager.get("TaskPaneGroup.animate")));
		collapsePane.addPropertyChangeListener("animationState", new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
			}

		});
	}

	public Container getContentPane() {
		return collapsePane.getContentPane();
	}

	public void updateUI() {
		if (collapsePane == null) {
			return;
		} else {
			setUI((TaskPaneGroupUI)LookAndFeelAddons.getUI(this, com.l2fprod.common.swing.plaf.TaskPaneGroupUI.class));
			return;
		}
	}

	public void setUI(TaskPaneGroupUI ui) {
		super.setUI(ui);
	}

	public String getUIClassID() {
		return "TaskPaneGroupUI";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		String old = title;
		this.title = title;
		firePropertyChange("title", old, title);
	}

	/**
	 * @deprecated Method setText is deprecated
	 */

	public void setText(String text) {
		setTitle(text);
	}

	/**
	 * @deprecated Method getText is deprecated
	 */

	public String getText() {
		return getTitle();
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		Icon old = icon;
		this.icon = icon;
		firePropertyChange("icon", old, icon);
	}

	public boolean isSpecial() {
		return special;
	}

	public void setSpecial(boolean special) {
		if (this.special != special) {
			this.special = special;
			firePropertyChange("special", !special, special);
		}
	}

	public void setScrollOnExpand(boolean scrollOnExpand) {
		if (this.scrollOnExpand != scrollOnExpand) {
			this.scrollOnExpand = scrollOnExpand;
			firePropertyChange("scrollOnExpand", !scrollOnExpand, scrollOnExpand);
		}
	}

	public boolean isScrollOnExpand() {
		return scrollOnExpand;
	}

	public void setExpanded(boolean expanded) {
		if (this.expanded != expanded) {
			this.expanded = expanded;
			collapsePane.setCollapsed(!expanded);
			firePropertyChange("expanded", !expanded, expanded);
		}
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setAnimated(boolean animated) {
		if (isAnimated() != animated) {
			collapsePane.setAnimated(animated);
			firePropertyChange("animated", !isAnimated(), isAnimated());
		}
	}

	public boolean isAnimated() {
		return collapsePane.isAnimated();
	}

	public Component add(Action action) {
		Component c = ((TaskPaneGroupUI)ui).createAction(action);
		add(c);
		return c;
	}

	public Container getValidatingContainer() {
		return getParent();
	}

	protected void addImpl(Component comp, Object constraints, int index) {
		getContentPane().add(comp, constraints, index);
	}

	public void setLayout(LayoutManager mgr) {
		if (collapsePane != null)
			getContentPane().setLayout(mgr);
	}

	public void remove(Component comp) {
		getContentPane().remove(comp);
	}

	public void remove(int index) {
		getContentPane().remove(index);
	}

	public void removeAll() {
		getContentPane().removeAll();
	}

	protected String paramString() {
		return super.paramString() + ",title=" + getTitle() + ",icon=" + getIcon() + ",expanded=" + String.valueOf(isExpanded()) + ",special=" + String.valueOf(isSpecial()) + ",scrollOnExpand=" + String.valueOf(isScrollOnExpand()) + ",ui=" + getUI();
	}

	static  {
		LookAndFeelAddons.contribute(new JTaskPaneGroupAddon());
	}

}
