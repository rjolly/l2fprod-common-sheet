// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   BasicOutlookBarUI.java

package com.l2fprod.common.swing.plaf.basic;

import com.l2fprod.common.swing.JOutlookBar;
import com.l2fprod.common.swing.PercentLayout;
import com.l2fprod.common.swing.PercentLayoutAnimator;
import com.l2fprod.common.swing.plaf.OutlookBarUI;
import com.l2fprod.common.util.JVM;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.LookAndFeel;
import javax.swing.Scrollable;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class BasicOutlookBarUI extends BasicTabbedPaneUI
	implements OutlookBarUI {
	private static class ScrollableJPanel extends JPanel
		implements Scrollable {

		public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
			return 16;
		}

		public Dimension getPreferredScrollableViewportSize() {
			return super.getPreferredSize();
		}

		public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
			return 16;
		}

		public boolean getScrollableTracksViewportWidth() {
			return true;
		}

		public boolean getScrollableTracksViewportHeight() {
			return false;
		}

		public ScrollableJPanel(Component component) {
			setLayout(new BorderLayout(0, 0));
			add("Center", component);
			setOpaque(false);
		}
	}

	protected static class TabButton extends JButton
		implements UIResource {

		public TabButton() {
		}

		public TabButton(ButtonUI ui) {
			setUI(ui);
		}
	}

	protected class TabLayout extends PercentLayout {

		public void addLayoutComponent(Component component, Object constraints) {
			if (constraints == null) {
				if (component instanceof TabButton)
					super.addLayoutComponent(component, "");
				else
					super.addLayoutComponent(component, "100%");
			} else {
				super.addLayoutComponent(component, constraints);
			}
		}

		public void setLayoutConstraints(Container parent) {
			Component components[] = parent.getComponents();
			int i = 0;
			for (int c = components.length; i < c; i++)
				if (!(components[i] instanceof TabButton))
					super.addLayoutComponent(components[i], "100%");

		}

		public void layoutContainer(Container parent) {
			int selectedIndex = BasicOutlookBarUI.this.PercentLayout.getSelectedIndex();
			Component visibleComponent = getVisibleComponent();
			if (selectedIndex < 0) {
				if (visibleComponent != null)
					setVisibleComponent(null);
			} else {
				Component selectedComponent = BasicOutlookBarUI.this.PercentLayout.getComponentAt(selectedIndex);
				boolean shouldChangeFocus = false;
				if (selectedComponent != null) {
					if (selectedComponent != visibleComponent && visibleComponent != null) {
						Component currentFocusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
						if (currentFocusOwner != null && SwingUtilities.isDescendingFrom(currentFocusOwner, visibleComponent))
							shouldChangeFocus = true;
					}
					setVisibleComponent(selectedComponent);
					Component components[] = parent.getComponents();
					for (int i = 0; i < components.length; i++)
						if (!(components[i] instanceof UIResource) && components[i].isVisible() && components[i] != selectedComponent) {
							components[i].setVisible(false);
							setConstraint(components[i], "*");
						}

					if (nextVisibleComponent != null)
						nextVisibleComponent.setVisible(true);
				}
				super.layoutContainer(parent);
				if (shouldChangeFocus && !requestFocusForVisibleComponent0())
					BasicOutlookBarUI.this.PercentLayout.requestFocus();
			}
		}

		protected TabLayout() {
		}
	}

	class ContainerTabHandler extends ContainerAdapter {

		public void componentAdded(ContainerEvent e) {
			if (!(e.getChild() instanceof UIResource)) {
				Component newTab = e.getChild();
				tabAdded(newTab);
			}
		}

		public void componentRemoved(ContainerEvent e) {
			if (!(e.getChild() instanceof UIResource)) {
				Component oldTab = e.getChild();
				tabRemoved(oldTab);
			}
		}

		ContainerTabHandler() {
		}
	}

	class PropertyChangeHandler
		implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent e) {
			String name = e.getPropertyName();
			if ("tabPropertyChangedAtIndex".equals(name)) {
				int index = ((Integer)e.getNewValue()).intValue();
				updateTabButtonAt(index);
			} else
			if ("tabPlacement".equals(name))
				updateTabLayoutOrientation();
		}

		PropertyChangeHandler() {
		}
	}


	private static final String BUTTON_ORIGINAL_FOREGROUND = "TabButton/foreground";
	private static final String BUTTON_ORIGINAL_BACKGROUND = "TabButton/background";
	private ContainerListener tabListener;
	private Map buttonToTab;
	private Map tabToButton;
	private Component nextVisibleComponent;
	private PercentLayoutAnimator animator;

	public BasicOutlookBarUI() {
	}

	public static ComponentUI createUI(JComponent c) {
		return new BasicOutlookBarUI();
	}

	public JScrollPane makeScrollPane(Component component) {
		JScrollPane scroll = new JScrollPane();
		scroll.setBorder(BorderFactory.createEmptyBorder());
		if (component instanceof Scrollable)
			scroll.getViewport().setView(component);
		else
			scroll.getViewport().setView(new ScrollableJPanel(component));
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		return scroll;
	}

	protected void installDefaults() {
		super.installDefaults();
		TabLayout layout = new TabLayout();
		tabPane.setLayout(layout);
		layout.setLayoutConstraints(tabPane);
		updateTabLayoutOrientation();
		buttonToTab = new HashMap();
		tabToButton = new HashMap();
		LookAndFeel.installBorder(tabPane, "OutlookBar.border");
		LookAndFeel.installColors(tabPane, "OutlookBar.background", "OutlookBar.foreground");
		tabPane.setOpaque(true);
		Component components[] = tabPane.getComponents();
		int i = 0;
		for (int c = components.length; i < c; i++)
			tabAdded(components[i]);

	}

	protected void uninstallDefaults() {
		java.util.List tabs = new ArrayList(buttonToTab.values());
		Component tab;
		for (Iterator iter = tabs.iterator(); iter.hasNext(); tabRemoved(tab))
			tab = (Component)iter.next();

		super.uninstallDefaults();
	}

	protected void installListeners() {
		tabPane.addContainerListener(tabListener = createTabListener());
		super.installListeners();
	}

	protected ContainerListener createTabListener() {
		return new ContainerTabHandler();
	}

	protected PropertyChangeListener createPropertyChangeListener() {
		return new PropertyChangeHandler();
	}

	protected void uninstallListeners() {
		super.uninstallListeners();
		tabPane.removeContainerListener(tabListener);
	}

	public Rectangle getTabBounds(JTabbedPane pane, int index) {
		Component tab = pane.getComponentAt(index);
		return tab.getBounds();
	}

	public int getTabRunCount(JTabbedPane pane) {
		return 0;
	}

	public int tabForCoordinate(JTabbedPane pane, int x, int y) {
		int index = -1;
		int i = 0;
		int c = pane.getTabCount();
		do {
			if (i >= c)
				break;
			if (pane.getComponentAt(i).contains(x, y)) {
				index = i;
				break;
			}
			i++;
		} while (true);
		return index;
	}

	protected int indexOfComponent(Component component) {
		int index = -1;
		Component components[] = tabPane.getComponents();
		int i = 0;
		do {
			if (i >= components.length)
				break;
			if (components[i] == component) {
				index = i;
				break;
			}
			i++;
		} while (true);
		return index;
	}

	protected TabButton createTabButton() {
		TabButton button = new TabButton();
		button.setOpaque(true);
		return button;
	}

	protected void tabAdded(final Component newTab) {
		TabButton button = (TabButton)tabToButton.get(newTab);
		if (button == null) {
			button = createTabButton();
			button.putClientProperty("TabButton/foreground", button.getForeground());
			button.putClientProperty("TabButton/background", button.getBackground());
			buttonToTab.put(button, newTab);
			tabToButton.put(newTab, button);
			button.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					Component current = getVisibleComponent();
					Component target = newTab;
					if (((JOutlookBar)BasicOutlookBarUI.this.this$0).isAnimated() && current != target && current != null && target != null) {
						if (animator != null)
							animator.stop();
						animator = new PercentLayoutAnimator((PercentLayout)BasicOutlookBarUI.this.this$0.getLayout(), current) {

							protected void complete() {
								super.complete();
								_fld0.PercentLayoutAnimator.setSelectedComponent(newTab);
								nextVisibleComponent = null;
								if (current.getParent() == _fld0.PercentLayoutAnimator)
									((PercentLayout)_fld0.PercentLayoutAnimator.getLayout()).setConstraint(current, "100%");
							}

						};
						nextVisibleComponent = newTab;
						animator.setTargetPercent(current, 1.0F, 0.0F);
						animator.setTargetPercent(newTab, 0.0F, 1.0F);
						animator.start();
					} else {
						nextVisibleComponent = null;
						BasicOutlookBarUI.this.this$0.setSelectedComponent(newTab);
					}
				}



			});
		} else {
			tabPane.remove(button);
		}
		updateTabButtonAt(tabPane.indexOfComponent(newTab));
		int index = indexOfComponent(newTab);
		tabPane.add(button, index);
		if (JVM.current().isOneDotFive())
			assureRectsCreated(tabPane.getTabCount());
	}

	protected void tabRemoved(Component removedTab) {
		TabButton button = (TabButton)tabToButton.get(removedTab);
		tabPane.remove(button);
		buttonToTab.remove(button);
		tabToButton.remove(removedTab);
	}

	protected void updateTabButtonAt(int index) {
		TabButton button = buttonForTab(index);
		button.setText(tabPane.getTitleAt(index));
		button.setIcon(tabPane.getIconAt(index));
		button.setDisabledIcon(tabPane.getDisabledIconAt(index));
		Color background = tabPane.getBackgroundAt(index);
		if (background == null)
			background = (Color)button.getClientProperty("TabButton/background");
		button.setBackground(background);
		Color foreground = tabPane.getForegroundAt(index);
		if (foreground == null)
			foreground = (Color)button.getClientProperty("TabButton/foreground");
		button.setForeground(foreground);
		button.setToolTipText(tabPane.getToolTipTextAt(index));
		button.setDisplayedMnemonicIndex(tabPane.getDisplayedMnemonicIndexAt(index));
		button.setMnemonic(tabPane.getMnemonicAt(index));
		button.setEnabled(tabPane.isEnabledAt(index));
		button.setHorizontalAlignment(((JOutlookBar)tabPane).getAlignmentAt(index));
	}

	protected TabButton buttonForTab(int index) {
		Component component = tabPane.getComponentAt(index);
		return (TabButton)tabToButton.get(component);
	}

	protected void updateTabLayoutOrientation() {
		TabLayout layout = (TabLayout)tabPane.getLayout();
		int placement = tabPane.getTabPlacement();
		if (placement == 1 || placement == 3)
			layout.setOrientation(0);
		else
			layout.setOrientation(1);
	}

	protected boolean requestFocusForVisibleComponent0() {
		Component visibleComponent = getVisibleComponent();
		if (visibleComponent.isFocusable()) {
			visibleComponent.requestFocus();
			return true;
		}
		return (visibleComponent instanceof JComponent) && ((JComponent)visibleComponent).requestDefaultFocus();
	}

	protected MouseListener createMouseListener() {
		return new MouseAdapter() {

		};
	}

	public void paint(Graphics g1, JComponent jcomponent) {
	}

	protected void paintContentBorder(Graphics g1, int i, int j) {
	}

	protected void paintContentBorderBottomEdge(Graphics g1, int i, int j, int k, int l, int i1, int j1) {
	}

	protected void paintContentBorderLeftEdge(Graphics g1, int i, int j, int k, int l, int i1, int j1) {
	}

	protected void paintContentBorderRightEdge(Graphics g1, int i, int j, int k, int l, int i1, int j1) {
	}

	protected void paintContentBorderTopEdge(Graphics g1, int i, int j, int k, int l, int i1, int j1) {
	}

	protected void paintFocusIndicator(Graphics g1, int i, Rectangle arectangle[], int j, Rectangle rectangle, Rectangle rectangle1, boolean flag) {
	}

	protected void paintIcon(Graphics g1, int i, int j, Icon icon1, Rectangle rectangle, boolean flag) {
	}

	protected void paintTab(Graphics g1, int i, Rectangle arectangle[], int j, Rectangle rectangle, Rectangle rectangle1) {
	}

	protected void paintTabArea(Graphics g1, int i, int j) {
	}

	protected void paintTabBackground(Graphics g1, int i, int j, int k, int l, int i1, int j1, 
			boolean flag) {
	}

	protected void paintTabBorder(Graphics g1, int i, int j, int k, int l, int i1, int j1, 
			boolean flag) {
	}

	protected void paintText(Graphics g1, int i, Font font1, FontMetrics fontmetrics, int j, String s, Rectangle rectangle, 
			boolean flag) {
	}


















}
