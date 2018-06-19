// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   BasicTipOfTheDayUI.java

package com.l2fprod.common.swing.plaf.basic;

import com.l2fprod.common.swing.ButtonAreaLayout;
import com.l2fprod.common.swing.JTipOfTheDay;
import com.l2fprod.common.swing.LookAndFeelTweaks;
import com.l2fprod.common.swing.TipModel;
import com.l2fprod.common.swing.plaf.TipOfTheDayUI;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ActionMapUIResource;
import javax.swing.plaf.ComponentUI;

public class BasicTipOfTheDayUI extends TipOfTheDayUI {
	class NextTipAction extends AbstractAction {

		public void actionPerformed(ActionEvent e) {
			tipPane.nextTip();
		}

		public boolean isEnabled() {
			return tipPane.isEnabled();
		}

		public NextTipAction() {
			super("nextTip");
		}
	}

	class PreviousTipAction extends AbstractAction {

		public void actionPerformed(ActionEvent e) {
			tipPane.previousTip();
		}

		public boolean isEnabled() {
			return tipPane.isEnabled();
		}

		public PreviousTipAction() {
			super("previousTip");
		}
	}

	class ChangeListener
		implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {
			if ("currentTip".equals(evt.getPropertyName()))
				showCurrentTip();
		}

		ChangeListener() {
		}
	}


	protected JTipOfTheDay tipPane;
	protected JPanel tipArea;
	protected Component currentTipComponent;
	protected Font tipFont;
	protected PropertyChangeListener changeListener;

	public static ComponentUI createUI(JComponent c) {
		return new BasicTipOfTheDayUI((JTipOfTheDay)c);
	}

	public BasicTipOfTheDayUI(JTipOfTheDay tipPane) {
		this.tipPane = tipPane;
	}

	public JDialog createDialog(Component parentComponent, com.l2fprod.common.swing.JTipOfTheDay.ShowOnStartupChoice choice) {
		return createDialog(parentComponent, choice, true);
	}

	protected JDialog createDialog(Component parentComponent, final com.l2fprod.common.swing.JTipOfTheDay.ShowOnStartupChoice choice, boolean showPreviousButton) {
		String title = UIManager.getString("TipOfTheDay.dialogTitle");
		Window window;
		if (parentComponent == null)
			window = JOptionPane.getRootFrame();
		else
			window = (parentComponent instanceof Window) ? (Window)parentComponent : SwingUtilities.getWindowAncestor(parentComponent);
		final JDialog dialog;
		if (window instanceof Frame)
			dialog = new JDialog((Frame)window, title, true);
		else
			dialog = new JDialog((Dialog)window, title, true);
		dialog.getContentPane().setLayout(new BorderLayout(10, 10));
		dialog.getContentPane().add("Center", tipPane);
		((JComponent)dialog.getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JPanel controls = new JPanel(new BorderLayout());
		dialog.getContentPane().add("South", controls);
		final JCheckBox showOnStartupBox;
		if (choice != null) {
			showOnStartupBox = new JCheckBox(UIManager.getString("TipOfTheDay.showOnStartupText"), choice.isShowingOnStartup());
			controls.add("Center", showOnStartupBox);
		} else {
			showOnStartupBox = null;
		}
		JPanel buttons = new JPanel(new ButtonAreaLayout(9));
		controls.add("East", buttons);
		if (showPreviousButton) {
			JButton previousTipButton = new JButton(UIManager.getString("TipOfTheDay.previousTipText"));
			buttons.add(previousTipButton);
			previousTipButton.addActionListener(getActionMap().get("previousTip"));
		}
		JButton nextTipButton = new JButton(UIManager.getString("TipOfTheDay.nextTipText"));
		buttons.add(nextTipButton);
		nextTipButton.addActionListener(getActionMap().get("nextTip"));
		JButton closeButton = new JButton(UIManager.getString("TipOfTheDay.closeText"));
		buttons.add(closeButton);
		final ActionListener saveChoice = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (choice != null)
					choice.setShowingOnStartup(showOnStartupBox.isSelected());
				dialog.setVisible(false);
			}

		};
		closeButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
				saveChoice.actionPerformed(null);
			}

		});
		dialog.getRootPane().setDefaultButton(closeButton);
		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				saveChoice.actionPerformed(null);
			}

		});
		((JComponent)dialog.getContentPane()).registerKeyboardAction(saveChoice, KeyStroke.getKeyStroke(27, 0), 2);
		dialog.pack();
		dialog.setLocationRelativeTo(parentComponent);
		return dialog;
	}

	public void installUI(JComponent c) {
		super.installUI(c);
		installDefaults();
		installKeyboardActions();
		installComponents();
		installListeners();
		showCurrentTip();
	}

	protected void installKeyboardActions() {
		ActionMap map = getActionMap();
		if (map != null)
			SwingUtilities.replaceUIActionMap(tipPane, map);
	}

	ActionMap getActionMap() {
		ActionMap map = new ActionMapUIResource();
		map.put("previousTip", new PreviousTipAction());
		map.put("nextTip", new NextTipAction());
		return map;
	}

	protected void installListeners() {
		changeListener = createChangeListener();
		tipPane.addPropertyChangeListener(changeListener);
	}

	protected PropertyChangeListener createChangeListener() {
		return new ChangeListener();
	}

	protected void installDefaults() {
		LookAndFeel.installColorsAndFont(tipPane, "TipOfTheDay.background", "TipOfTheDay.foreground", "TipOfTheDay.font");
		LookAndFeel.installBorder(tipPane, "TipOfTheDay.border");
		tipFont = UIManager.getFont("TipOfTheDay.tipFont");
		tipPane.setOpaque(true);
	}

	protected void installComponents() {
		tipPane.setLayout(new BorderLayout());
		JLabel tipIcon = new JLabel(UIManager.getString("TipOfTheDay.didYouKnowText"));
		tipIcon.setIcon(UIManager.getIcon("TipOfTheDay.icon"));
		tipIcon.setBorder(BorderFactory.createEmptyBorder(22, 15, 22, 15));
		tipPane.add("North", tipIcon);
		tipArea = new JPanel(new BorderLayout(2, 2));
		tipArea.setOpaque(false);
		tipArea.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		tipPane.add("Center", tipArea);
	}

	public Dimension getPreferredSize(JComponent c) {
		return new Dimension(420, 175);
	}

	protected void showCurrentTip() {
		if (currentTipComponent != null)
			tipArea.remove(currentTipComponent);
		int currentTip = tipPane.getCurrentTip();
		if (currentTip == -1) {
			JLabel label = new JLabel();
			label.setOpaque(true);
			label.setBackground(UIManager.getColor("TextArea.background"));
			currentTipComponent = label;
			tipArea.add("Center", currentTipComponent);
			return;
		}
		if (tipPane.getModel().getTipCount() == 0 || currentTip < 0 && currentTip >= tipPane.getModel().getTipCount()) {
			currentTipComponent = new JLabel();
		} else {
			com.l2fprod.common.swing.TipModel.Tip tip = tipPane.getModel().getTipAt(currentTip);
			Object tipObject = tip.getTip();
			if (tipObject instanceof Component)
				currentTipComponent = (Component)tipObject;
			else
			if (tipObject instanceof Icon) {
				currentTipComponent = new JLabel((Icon)tipObject);
			} else {
				JScrollPane tipScroll = new JScrollPane();
				tipScroll.setBorder(null);
				tipScroll.setOpaque(false);
				tipScroll.getViewport().setOpaque(false);
				tipScroll.setBorder(null);
				String text = tipObject != null ? tipObject.toString() : "";
				if (text.toLowerCase().startsWith("<html>")) {
					JEditorPane editor = new JEditorPane("text/html", text);
					LookAndFeelTweaks.htmlize(editor, tipPane.getFont());
					editor.setEditable(false);
					editor.setBorder(null);
					editor.setMargin(null);
					editor.setOpaque(false);
					tipScroll.getViewport().setView(editor);
				} else {
					JTextArea area = new JTextArea(text);
					area.setFont(tipPane.getFont());
					area.setEditable(false);
					area.setLineWrap(true);
					area.setWrapStyleWord(true);
					area.setBorder(null);
					area.setMargin(null);
					area.setOpaque(false);
					tipScroll.getViewport().setView(area);
				}
				currentTipComponent = tipScroll;
			}
		}
		tipArea.add("Center", currentTipComponent);
		tipArea.revalidate();
		tipArea.repaint();
	}

	public void uninstallUI(JComponent c) {
		uninstallListeners();
		uninstallComponents();
		uninstallDefaults();
		super.uninstallUI(c);
	}

	protected void uninstallListeners() {
		tipPane.removePropertyChangeListener(changeListener);
	}

	protected void uninstallComponents() {
	}

	protected void uninstallDefaults() {
	}
}
