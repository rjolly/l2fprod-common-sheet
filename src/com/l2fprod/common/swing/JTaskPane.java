// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JTaskPane.java

package com.l2fprod.common.swing;

import com.l2fprod.common.swing.plaf.JTaskPaneAddon;
import com.l2fprod.common.swing.plaf.LookAndFeelAddons;
import com.l2fprod.common.swing.plaf.TaskPaneUI;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JViewport;
import javax.swing.Scrollable;

// Referenced classes of package com.l2fprod.common.swing:
//			JTaskPaneGroup

public class JTaskPane extends JComponent
	implements Scrollable {

	public static final String UI_CLASS_ID = "TaskPaneUI";

	public JTaskPane() {
		updateUI();
	}

	public void updateUI() {
		setUI((TaskPaneUI)LookAndFeelAddons.getUI(this, com.l2fprod.common.swing.plaf.TaskPaneUI.class));
	}

	public void setUI(TaskPaneUI ui) {
		super.setUI(ui);
	}

	public String getUIClassID() {
		return "TaskPaneUI";
	}

	public void add(JTaskPaneGroup group) {
		super.add(group);
	}

	public void remove(JTaskPaneGroup group) {
		super.remove(group);
	}

	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}

	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		return 10;
	}

	public boolean getScrollableTracksViewportHeight() {
		if (getParent() instanceof JViewport)
			return ((JViewport)getParent()).getHeight() > getPreferredSize().height;
		else
			return false;
	}

	public boolean getScrollableTracksViewportWidth() {
		return true;
	}

	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		return 10;
	}

	static  {
		LookAndFeelAddons.contribute(new JTaskPaneAddon());
	}
}
