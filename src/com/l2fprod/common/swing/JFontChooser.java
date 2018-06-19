// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JFontChooser.java

package com.l2fprod.common.swing;

import com.l2fprod.common.swing.plaf.FontChooserUI;
import com.l2fprod.common.swing.plaf.JFontChooserAddon;
import com.l2fprod.common.swing.plaf.LookAndFeelAddons;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

// Referenced classes of package com.l2fprod.common.swing:
//			DefaultFontChooserModel, BaseDialog, BannerPanel, FontChooserModel

public class JFontChooser extends JComponent {

	public static final String SELECTED_FONT_CHANGED_KEY = "selectedFont";
	protected Font selectedFont;
	private FontChooserModel model;

	public JFontChooser() {
		this(((FontChooserModel) (new DefaultFontChooserModel())));
	}

	public JFontChooser(FontChooserModel model) {
		this.model = model;
		selectedFont = getFont();
		updateUI();
	}

	public void updateUI() {
		setUI((FontChooserUI)LookAndFeelAddons.getUI(this, com.l2fprod.common.swing.plaf.FontChooserUI.class));
	}

	public void setUI(FontChooserUI ui) {
		super.setUI(ui);
	}

	public String getUIClassID() {
		return "FontChooserUI";
	}

	public void setSelectedFont(Font f) {
		Font oldFont = selectedFont;
		selectedFont = f;
		firePropertyChange("selectedFont", oldFont, selectedFont);
	}

	public Font getSelectedFont() {
		return selectedFont;
	}

	public FontChooserModel getModel() {
		return model;
	}

	public Font showFontDialog(Component parent, String title) {
		BaseDialog dialog = createDialog(parent, title);
		if (dialog.ask())
			return getSelectedFont();
		else
			return null;
	}

	protected BaseDialog createDialog(Component parent, String title) {
		java.awt.Window window = ((java.awt.Window) (parent != null ? SwingUtilities.windowForComponent(parent) : ((java.awt.Window) (JOptionPane.getRootFrame()))));
		BaseDialog dialog;
		if (window instanceof Frame)
			dialog = new BaseDialog((Frame)window, title, true);
		else
			dialog = new BaseDialog((Dialog)window, title, true);
		dialog.setDialogMode(0);
		dialog.getBanner().setVisible(false);
		dialog.getContentPane().setLayout(new BorderLayout());
		dialog.getContentPane().add("Center", this);
		dialog.pack();
		dialog.setLocationRelativeTo(parent);
		return dialog;
	}

	public static Font showDialog(Component parent, String title, Font initialFont) {
		JFontChooser chooser = new JFontChooser();
		chooser.setSelectedFont(initialFont);
		return chooser.showFontDialog(parent, title);
	}

	static  {
		LookAndFeelAddons.contribute(new JFontChooserAddon());
	}
}
