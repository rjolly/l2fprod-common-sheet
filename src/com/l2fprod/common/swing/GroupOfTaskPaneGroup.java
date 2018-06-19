// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   GroupOfTaskPaneGroup.java

package com.l2fprod.common.swing;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

// Referenced classes of package com.l2fprod.common.swing:
//			JTaskPaneGroup

public class GroupOfTaskPaneGroup
	implements PropertyChangeListener {

	private JTaskPaneGroup selection;

	public GroupOfTaskPaneGroup() {
	}

	public void add(JTaskPaneGroup taskpaneGroup) {
		register(taskpaneGroup);
		if (selection == null) {
			if (taskpaneGroup.isExpanded())
				selection = taskpaneGroup;
		} else {
			taskpaneGroup.setExpanded(false);
		}
		maybeUpdateSelection(taskpaneGroup);
	}

	public void remove(JTaskPaneGroup taskpaneGroup) {
		unregister(taskpaneGroup);
		if (selection == taskpaneGroup)
			selection = null;
	}

	public void propertyChange(PropertyChangeEvent event) {
		JTaskPaneGroup taskpaneGroup = (JTaskPaneGroup)event.getSource();
		maybeUpdateSelection(taskpaneGroup);
	}

	private void maybeUpdateSelection(JTaskPaneGroup taskpaneGroup) {
		if (selection != taskpaneGroup && taskpaneGroup.isExpanded()) {
			if (selection != null)
				selection.setExpanded(false);
			selection = taskpaneGroup;
		}
	}

	private void register(JTaskPaneGroup taskpaneGroup) {
		taskpaneGroup.addPropertyChangeListener("expanded", this);
	}

	private void unregister(JTaskPaneGroup taskpaneGroup) {
		taskpaneGroup.removePropertyChangeListener("expanded", this);
	}
}
