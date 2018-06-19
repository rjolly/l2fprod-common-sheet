// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JTipOfTheDay.java

package com.l2fprod.common.swing;

import com.l2fprod.common.swing.plaf.JTipOfTheDayAddon;
import com.l2fprod.common.swing.plaf.LookAndFeelAddons;
import com.l2fprod.common.swing.plaf.TipOfTheDayUI;
import com.l2fprod.common.swing.tips.DefaultTipModel;
import java.awt.Component;
import java.awt.HeadlessException;
import java.util.prefs.Preferences;
import javax.swing.JComponent;
import javax.swing.JDialog;

// Referenced classes of package com.l2fprod.common.swing:
//			TipModel

public class JTipOfTheDay extends JComponent {
	public static interface ShowOnStartupChoice {

		public abstract void setShowingOnStartup(boolean flag);

		public abstract boolean isShowingOnStartup();
	}


	public static final String uiClassID = "l2fprod/TipOfTheDayUI";
	public static final String PREFERENCE_KEY = "ShowTipOnStartup";
	public static final String CURRENT_TIP_CHANGED_KEY = "currentTip";
	private TipModel model;
	private int currentTip;

	public JTipOfTheDay() {
		this(((TipModel) (new DefaultTipModel(new TipModel.Tip[0]))));
	}

	public JTipOfTheDay(TipModel model) {
		currentTip = 0;
		this.model = model;
		updateUI();
	}

	public void updateUI() {
		setUI((TipOfTheDayUI)LookAndFeelAddons.getUI(this, com.l2fprod.common.swing.plaf.TipOfTheDayUI.class));
	}

	public void setUI(TipOfTheDayUI ui) {
		super.setUI(ui);
	}

	public TipOfTheDayUI getUI() {
		return (TipOfTheDayUI)ui;
	}

	public String getUIClassID() {
		return "l2fprod/TipOfTheDayUI";
	}

	public TipModel getModel() {
		return model;
	}

	public void setModel(TipModel model) {
		TipModel old = this.model;
		this.model = model;
		firePropertyChange("model", old, model);
	}

	public int getCurrentTip() {
		return currentTip;
	}

	public void setCurrentTip(int currentTip) {
		if (currentTip < 0 || currentTip >= getModel().getTipCount()) {
			throw new IllegalArgumentException("Current tip must be within the bounds [0, " + getModel().getTipCount() + "[");
		} else {
			int oldTip = this.currentTip;
			this.currentTip = currentTip;
			firePropertyChange("currentTip", oldTip, currentTip);
			return;
		}
	}

	public void nextTip() {
		int count = getModel().getTipCount();
		if (count == 0)
			return;
		int nextTip = currentTip + 1;
		if (nextTip >= count)
			nextTip = 0;
		setCurrentTip(nextTip);
	}

	public void previousTip() {
		int count = getModel().getTipCount();
		if (count == 0)
			return;
		int previousTip = currentTip - 1;
		if (previousTip < 0)
			previousTip = count - 1;
		setCurrentTip(previousTip);
	}

	public void showDialog(Component parentComponent) throws HeadlessException {
		showDialog(parentComponent, (ShowOnStartupChoice)null);
	}

	public boolean showDialog(Component parentComponent, Preferences showOnStartupPref) throws HeadlessException {
		return showDialog(parentComponent, showOnStartupPref, false);
	}

	public boolean showDialog(Component parentComponent, final Preferences showOnStartupPref, boolean force) throws HeadlessException {
		if (showOnStartupPref == null) {
			throw new IllegalArgumentException("Preferences can not be null");
		} else {
			ShowOnStartupChoice store = new ShowOnStartupChoice() {

				public boolean isShowingOnStartup() {
					return showOnStartupPref.getBoolean("ShowTipOnStartup", true);
				}

				public void setShowingOnStartup(boolean showOnStartup) {
					if (!showOnStartup)
						showOnStartupPref.putBoolean("ShowTipOnStartup", showOnStartup);
				}

			};
			return showDialog(parentComponent, store, force);
		}
	}

	public boolean showDialog(Component parentComponent, ShowOnStartupChoice choice) {
		return showDialog(parentComponent, choice, false);
	}

	public boolean showDialog(Component parentComponent, ShowOnStartupChoice choice, boolean force) {
		if (choice == null) {
			JDialog dialog = createDialog(parentComponent, choice);
			dialog.setVisible(true);
			dialog.dispose();
			return true;
		}
		if (force || choice.isShowingOnStartup()) {
			JDialog dialog = createDialog(parentComponent, choice);
			dialog.setVisible(true);
			dialog.dispose();
			return choice.isShowingOnStartup();
		} else {
			return false;
		}
	}

	public static boolean isShowingOnStartup(Preferences showOnStartupPref) {
		return showOnStartupPref.getBoolean("ShowTipOnStartup", true);
	}

	public static void forceShowOnStartup(Preferences showOnStartupPref) {
		showOnStartupPref.remove("ShowTipOnStartup");
	}

	protected JDialog createDialog(Component parentComponent, ShowOnStartupChoice choice) {
		return getUI().createDialog(parentComponent, choice);
	}

	static  {
		LookAndFeelAddons.contribute(new JTipOfTheDayAddon());
	}
}
