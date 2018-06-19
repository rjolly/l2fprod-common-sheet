// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   BasicTaskPaneGroupUI.java

package com.l2fprod.common.swing.plaf.basic;

import com.l2fprod.common.swing.JCollapsiblePane;
import com.l2fprod.common.swing.JLinkButton;
import com.l2fprod.common.swing.JTaskPaneGroup;
import com.l2fprod.common.swing.icons.EmptyIcon;
import com.l2fprod.common.swing.plaf.TaskPaneGroupUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.ActionMapUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;

public class BasicTaskPaneGroupUI extends TaskPaneGroupUI {
	protected class PaneBorder
		implements Border {

		protected Color borderColor;
		protected Color titleForeground;
		protected Color specialTitleBackground;
		protected Color specialTitleForeground;
		protected Color titleBackgroundGradientStart;
		protected Color titleBackgroundGradientEnd;
		protected Color titleOver;
		protected Color specialTitleOver;
		protected JLabel label;

		public Insets getBorderInsets(Component c) {
			return new Insets(getTitleHeight(), 0, 0, 0);
		}

		public boolean isBorderOpaque() {
			return true;
		}

		public Dimension getPreferredSize(JTaskPaneGroup group) {
			configureLabel(group);
			Dimension dim = label.getPreferredSize();
			dim.width += 3;
			dim.width += BasicTaskPaneGroupUI.TITLE_HEIGHT;
			dim.width += 3;
			dim.height = getTitleHeight();
			return dim;
		}

		protected void paintTitleBackground(JTaskPaneGroup group, Graphics g) {
			if (group.isSpecial())
				g.setColor(specialTitleBackground);
			else
				g.setColor(titleBackgroundGradientStart);
			g.fillRect(0, 0, group.getWidth(), getTitleHeight() - 1);
		}

		protected void paintTitle(JTaskPaneGroup group, Graphics g, Color textColor, int x, int y, int width, int height) {
			configureLabel(group);
			label.setForeground(textColor);
			g.translate(x, y);
			label.setBounds(0, 0, width, height);
			label.paint(g);
			g.translate(-x, -y);
		}

		protected void configureLabel(JTaskPaneGroup group) {
			label.applyComponentOrientation(group.getComponentOrientation());
			label.setFont(group.getFont());
			label.setText(group.getTitle());
			label.setIcon(((Icon) (group.getIcon() != null ? group.getIcon() : ((Icon) (new EmptyIcon())))));
		}

		protected void paintExpandedControls(JTaskPaneGroup jtaskpanegroup, Graphics g1, int i, int j, int k, int l) {
		}

		protected Color getPaintColor(JTaskPaneGroup group) {
			Color paintColor;
			if (isMouseOverBorder()) {
				if (mouseOver) {
					if (group.isSpecial())
						paintColor = specialTitleOver;
					else
						paintColor = titleOver;
				} else
				if (group.isSpecial())
					paintColor = specialTitleForeground;
				else
					paintColor = titleForeground;
			} else
			if (group.isSpecial())
				paintColor = specialTitleForeground;
			else
				paintColor = titleForeground;
			return paintColor;
		}

		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			JTaskPaneGroup group = (JTaskPaneGroup)c;
			int controlWidth = BasicTaskPaneGroupUI.TITLE_HEIGHT - 2 * BasicTaskPaneGroupUI.ROUND_HEIGHT;
			int controlX = group.getWidth() - BasicTaskPaneGroupUI.TITLE_HEIGHT;
			int controlY = BasicTaskPaneGroupUI.ROUND_HEIGHT - 1;
			int titleX = 3;
			int titleY = 0;
			int titleWidth = group.getWidth() - getTitleHeight() - 3;
			int titleHeight = getTitleHeight();
			if (!group.getComponentOrientation().isLeftToRight()) {
				controlX = group.getWidth() - controlX - controlWidth;
				titleX = group.getWidth() - titleX - titleWidth;
			}
			paintTitleBackground(group, g);
			paintExpandedControls(group, g, controlX, controlY, controlWidth, controlWidth);
			Color paintColor = getPaintColor(group);
			if (group.hasFocus()) {
				g.setColor(paintColor);
				BasicGraphicsUtils.drawDashedRect(g, 3, 3, width - 6, getTitleHeight() - 6);
			}
			paintTitle(group, g, paintColor, titleX, titleY, titleWidth, titleHeight);
		}

		protected void paintRectAroundControls(JTaskPaneGroup group, Graphics g, int x, int y, int width, int height, Color highColor, 
				Color lowColor) {
			if (mouseOver) {
				int x2 = x + width;
				int y2 = y + height;
				g.setColor(highColor);
				g.drawLine(x, y, x2, y);
				g.drawLine(x, y, x, y2);
				g.setColor(lowColor);
				g.drawLine(x2, y, x2, y2);
				g.drawLine(x, y2, x2, y2);
			}
		}

		protected void paintOvalAroundControls(JTaskPaneGroup group, Graphics g, int x, int y, int width, int height) {
			if (group.isSpecial()) {
				g.setColor(specialTitleBackground.brighter());
				g.drawOval(x, y, width, height);
			} else {
				g.setColor(titleBackgroundGradientStart);
				g.fillOval(x, y, width, height);
				g.setColor(titleBackgroundGradientEnd.darker());
				g.drawOval(x, y, width, height);
			}
		}

		protected void paintChevronControls(JTaskPaneGroup group, Graphics g, int x, int y, int width, int height) {
			ChevronIcon chevron;
			if (group.isExpanded())
				chevron = new ChevronIcon(true);
			else
				chevron = new ChevronIcon(false);
			int chevronX = (x + width / 2) - chevron.getIconWidth() / 2;
			int chevronY = y + (height / 2 - chevron.getIconHeight());
			chevron.paintIcon(group, g, chevronX, chevronY);
			chevron.paintIcon(group, g, chevronX, chevronY + chevron.getIconHeight() + 1);
		}

		protected boolean isMouseOverBorder() {
			return false;
		}

		public PaneBorder() {
			borderColor = UIManager.getColor("TaskPaneGroup.borderColor");
			titleForeground = UIManager.getColor("TaskPaneGroup.titleForeground");
			specialTitleBackground = UIManager.getColor("TaskPaneGroup.specialTitleBackground");
			specialTitleForeground = UIManager.getColor("TaskPaneGroup.specialTitleForeground");
			titleBackgroundGradientStart = UIManager.getColor("TaskPaneGroup.titleBackgroundGradientStart");
			titleBackgroundGradientEnd = UIManager.getColor("TaskPaneGroup.titleBackgroundGradientEnd");
			titleOver = UIManager.getColor("TaskPaneGroup.titleOver");
			if (titleOver == null)
				titleOver = specialTitleBackground.brighter();
			specialTitleOver = UIManager.getColor("TaskPaneGroup.specialTitleOver");
			if (specialTitleOver == null)
				specialTitleOver = specialTitleBackground.brighter();
			label = new JLabel();
			label.setOpaque(false);
			label.setIconTextGap(8);
		}
	}

	protected static class ContentPaneBorder
		implements Border {

		Color color;

		public Insets getBorderInsets(Component c) {
			return new Insets(0, 1, 1, 1);
		}

		public boolean isBorderOpaque() {
			return true;
		}

		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			g.setColor(color);
			g.drawLine(x, y, x, (y + height) - 1);
			g.drawLine(x, (y + height) - 1, (x + width) - 1, (y + height) - 1);
			g.drawLine((x + width) - 1, y, (x + width) - 1, (y + height) - 1);
		}

		public ContentPaneBorder(Color color) {
			this.color = color;
		}
	}

	protected static class ChevronIcon
		implements Icon {

		boolean up;

		public int getIconHeight() {
			return 3;
		}

		public int getIconWidth() {
			return 6;
		}

		public void paintIcon(Component c, Graphics g, int x, int y) {
			if (up) {
				g.drawLine(x + 3, y, x, y + 3);
				g.drawLine(x + 3, y, x + 6, y + 3);
			} else {
				g.drawLine(x, y, x + 3, y + 3);
				g.drawLine(x + 3, y + 3, x + 6, y);
			}
		}

		public ChevronIcon(boolean up) {
			this.up = true;
			this.up = up;
		}
	}

	class ToggleExpandedAction extends AbstractAction {

		public void actionPerformed(ActionEvent e) {
			group.setExpanded(!group.isExpanded());
		}

		public boolean isEnabled() {
			return group.isVisible();
		}

		public ToggleExpandedAction() {
			super("toggleExpanded");
		}
	}

	class ToggleListener extends MouseInputAdapter {

		public void mouseEntered(MouseEvent e) {
			if (isInBorder(e)) {
				e.getComponent().setCursor(Cursor.getPredefinedCursor(12));
			} else {
				mouseOver = false;
				group.repaint();
			}
		}

		public void mouseExited(MouseEvent e) {
			e.getComponent().setCursor(Cursor.getDefaultCursor());
			mouseOver = false;
			group.repaint();
		}

		public void mouseMoved(MouseEvent e) {
			if (isInBorder(e)) {
				e.getComponent().setCursor(Cursor.getPredefinedCursor(12));
				mouseOver = true;
				group.repaint();
			} else {
				e.getComponent().setCursor(Cursor.getDefaultCursor());
				mouseOver = false;
				group.repaint();
			}
		}

		public void mouseReleased(MouseEvent e) {
			if (isInBorder(e))
				group.setExpanded(!group.isExpanded());
		}

		ToggleListener() {
		}
	}

	class ChangeListener
		implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {
			if ("expanded".equals(evt.getPropertyName()) && Boolean.TRUE.equals(evt.getNewValue()) && !group.isAnimated() || "animationState".equals(evt.getPropertyName()) && "expanded".equals(evt.getNewValue())) {
				if (group.isScrollOnExpand())
					ensureVisible();
			} else
			if ("icon".equals(evt.getPropertyName()) || "title".equals(evt.getPropertyName()) || "special".equals(evt.getPropertyName()))
				group.repaint();
		}

		ChangeListener() {
		}
	}

	static class RepaintOnFocus
		implements FocusListener {

		public void focusGained(FocusEvent e) {
			e.getComponent().repaint();
		}

		public void focusLost(FocusEvent e) {
			e.getComponent().repaint();
		}

		RepaintOnFocus() {
		}
	}


	private static FocusListener focusListener = new RepaintOnFocus();
	protected static int TITLE_HEIGHT = 25;
	protected static int ROUND_HEIGHT = 5;
	protected JTaskPaneGroup group;
	protected boolean mouseOver;
	protected MouseInputListener mouseListener;
	protected PropertyChangeListener propertyListener;

	public BasicTaskPaneGroupUI() {
	}

	public static ComponentUI createUI(JComponent c) {
		return new BasicTaskPaneGroupUI();
	}

	public void installUI(JComponent c) {
		super.installUI(c);
		group = (JTaskPaneGroup)c;
		installDefaults();
		installListeners();
		installKeyboardActions();
	}

	protected void installDefaults() {
		group.setOpaque(true);
		group.setBorder(createPaneBorder());
		((JComponent)group.getContentPane()).setBorder(createContentPaneBorder());
		LookAndFeel.installColorsAndFont(group, "TaskPaneGroup.background", "TaskPaneGroup.foreground", "TaskPaneGroup.font");
		LookAndFeel.installColorsAndFont((JComponent)group.getContentPane(), "TaskPaneGroup.background", "TaskPaneGroup.foreground", "TaskPaneGroup.font");
	}

	protected void installListeners() {
		mouseListener = createMouseInputListener();
		group.addMouseMotionListener(mouseListener);
		group.addMouseListener(mouseListener);
		group.addFocusListener(focusListener);
		propertyListener = createPropertyListener();
		group.addPropertyChangeListener(propertyListener);
	}

	protected void installKeyboardActions() {
		InputMap inputMap = (InputMap)UIManager.get("TaskPaneGroup.focusInputMap");
		if (inputMap != null)
			SwingUtilities.replaceUIInputMap(group, 0, inputMap);
		ActionMap map = getActionMap();
		if (map != null)
			SwingUtilities.replaceUIActionMap(group, map);
	}

	ActionMap getActionMap() {
		ActionMap map = new ActionMapUIResource();
		map.put("toggleExpanded", new ToggleExpandedAction());
		return map;
	}

	public void uninstallUI(JComponent c) {
		uninstallListeners();
		super.uninstallUI(c);
	}

	protected void uninstallListeners() {
		group.removeMouseListener(mouseListener);
		group.removeMouseMotionListener(mouseListener);
		group.removeFocusListener(focusListener);
		group.removePropertyChangeListener(propertyListener);
	}

	protected MouseInputListener createMouseInputListener() {
		return new ToggleListener();
	}

	protected PropertyChangeListener createPropertyListener() {
		return new ChangeListener();
	}

	protected boolean isInBorder(MouseEvent event) {
		return event.getY() < getTitleHeight();
	}

	protected final int getTitleHeight() {
		return TITLE_HEIGHT;
	}

	protected Border createPaneBorder() {
		return new PaneBorder();
	}

	public Dimension getPreferredSize(JComponent c) {
		Component component = group.getComponent(0);
		if (!(component instanceof JCollapsiblePane))
			return super.getPreferredSize(c);
		JCollapsiblePane collapsible = (JCollapsiblePane)component;
		Dimension dim = collapsible.getPreferredSize();
		Border groupBorder = group.getBorder();
		if (groupBorder instanceof PaneBorder) {
			Dimension border = ((PaneBorder)groupBorder).getPreferredSize(group);
			dim.width = Math.max(dim.width, border.width);
			dim.height += border.height;
		} else {
			dim.height += getTitleHeight();
		}
		return dim;
	}

	protected Border createContentPaneBorder() {
		Color borderColor = UIManager.getColor("TaskPaneGroup.borderColor");
		return new CompoundBorder(new ContentPaneBorder(borderColor), BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

	public Component createAction(Action action) {
		JLinkButton button = new JLinkButton(action);
		button.setOpaque(false);
		button.setBorder(null);
		button.setBorderPainted(false);
		button.setFocusPainted(true);
		button.setForeground(UIManager.getColor("TaskPaneGroup.titleForeground"));
		return button;
	}

	protected void ensureVisible() {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				group.scrollRectToVisible(new Rectangle(group.getWidth(), group.getHeight()));
			}

		});
	}

}
