// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   BaseDialog.java

package com.l2fprod.common.swing;

import com.l2fprod.common.util.ResourceManager;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

// Referenced classes of package com.l2fprod.common.swing:
//			BannerPanel, UIUtilities, LookAndFeelTweaks

public class BaseDialog extends JDialog {

	public static final int OK_CANCEL_DIALOG = 0;
	public static final int CLOSE_DIALOG = 1;
	private BannerPanel banner;
	private JPanel contentPane;
	private JPanel buttonPane;
	private boolean cancelClicked;
	private JButton okButton;
	private JButton cancelOrCloseButton;
	private int mode;
	private Action okAction = new AbstractAction() {

		public void actionPerformed(ActionEvent e) {
			ok();
		}

	};
	private Action cancelOrCloseAction = new AbstractAction() {

		public void actionPerformed(ActionEvent e) {
			cancel();
		}

	};

	public BaseDialog() throws HeadlessException {
		buildUI();
	}

	public BaseDialog(Dialog owner) throws HeadlessException {
		super(owner);
		buildUI();
	}

	public static BaseDialog newBaseDialog(Component parent) {
		Window window = (Window)SwingUtilities.getAncestorOfClass(java.awt.Window.class, parent);
		if (window instanceof Frame)
			return new BaseDialog((Frame)window);
		if (window instanceof Dialog)
			return new BaseDialog((Dialog)window);
		else
			return new BaseDialog();
	}

	public BaseDialog(Dialog owner, boolean modal) throws HeadlessException {
		super(owner, modal);
		buildUI();
	}

	public BaseDialog(Frame owner) throws HeadlessException {
		super(owner);
		buildUI();
	}

	public BaseDialog(Frame owner, boolean modal) throws HeadlessException {
		super(owner, modal);
		buildUI();
	}

	public BaseDialog(Dialog owner, String title) throws HeadlessException {
		super(owner, title);
		buildUI();
	}

	public BaseDialog(Dialog owner, String title, boolean modal) throws HeadlessException {
		super(owner, title, modal);
		buildUI();
	}

	public BaseDialog(Frame owner, String title) throws HeadlessException {
		super(owner, title);
		buildUI();
	}

	public BaseDialog(Frame owner, String title, boolean modal) throws HeadlessException {
		super(owner, title, modal);
		buildUI();
	}

	public BaseDialog(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) throws HeadlessException {
		super(owner, title, modal, gc);
		buildUI();
	}

	public BaseDialog(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		buildUI();
	}

	public final BannerPanel getBanner() {
		return banner;
	}

	public final Container getContentPane() {
		return contentPane;
	}

	public final Container getButtonPane() {
		return buttonPane;
	}

	public boolean ask() {
		setVisible(true);
		dispose();
		return !cancelClicked;
	}

	public void ok() {
		cancelClicked = false;
		setVisible(false);
	}

	public void cancel() {
		cancelClicked = true;
		setVisible(false);
	}

	public void setDialogMode(int mode) {
		if (mode != 0 && mode != 1)
			throw new IllegalArgumentException("invalid dialog mode");
		if (okButton != null) {
			buttonPane.remove(okButton);
			okButton = null;
		}
		if (cancelOrCloseButton != null) {
			buttonPane.remove(cancelOrCloseButton);
			cancelOrCloseButton = null;
		}
		switch (mode) {
		case 0: // '\0'
			okButton = new JButton(ResourceManager.all(com.l2fprod.common.swing.BaseDialog.class).getString("ok"));
			okButton.addActionListener(okAction);
			buttonPane.add(okButton);
			cancelOrCloseButton = new JButton(ResourceManager.all(com.l2fprod.common.swing.BaseDialog.class).getString("cancel"));
			cancelOrCloseButton.addActionListener(cancelOrCloseAction);
			buttonPane.add(cancelOrCloseButton);
			getRootPane().setDefaultButton(okButton);
			break;

		case 1: // '\001'
			cancelOrCloseButton = new JButton(ResourceManager.all(com.l2fprod.common.swing.BaseDialog.class).getString("close"));
			cancelOrCloseButton.addActionListener(cancelOrCloseAction);
			buttonPane.add(cancelOrCloseButton);
			break;
		}
		this.mode = mode;
	}

	public int getDialogMode() {
		return mode;
	}

	public void centerOnScreen() {
		UIUtilities.centerOnScreen(this);
	}

	protected String paramString() {
		return super.paramString() + ",dialogMode=" + (mode != 0 ? "CLOSE_DIALOG" : "OK_CANCEL_DIALOG");
	}

	private void buildUI() {
		Container container = super.getContentPane();
		container.setLayout(new BorderLayout(0, 0));
		banner = new BannerPanel();
		container.add("North", banner);
		JPanel contentPaneAndButtons = new JPanel();
		contentPaneAndButtons.setLayout(LookAndFeelTweaks.createVerticalPercentLayout());
		contentPaneAndButtons.setBorder(LookAndFeelTweaks.WINDOW_BORDER);
		container.add("Center", contentPaneAndButtons);
		contentPane = new JPanel();
		LookAndFeelTweaks.setBorderLayout(contentPane);
		LookAndFeelTweaks.setBorder(contentPane);
		contentPaneAndButtons.add(contentPane, "*");
		buttonPane = new JPanel();
		buttonPane.setLayout(LookAndFeelTweaks.createButtonAreaLayout());
		LookAndFeelTweaks.setBorder(buttonPane);
		contentPaneAndButtons.add(buttonPane);
		setDefaultCloseOperation(0);
		((JComponent)container).registerKeyboardAction(cancelOrCloseAction, KeyStroke.getKeyStroke(27, 0), 2);
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				cancel();
			}

		});
		setDialogMode(0);
	}
}
