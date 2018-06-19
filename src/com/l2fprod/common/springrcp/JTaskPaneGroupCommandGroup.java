// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JTaskPaneGroupCommandGroup.java

package com.l2fprod.common.springrcp;

import com.l2fprod.common.swing.JTaskPaneGroup;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.richclient.command.ActionCommand;
import org.springframework.richclient.command.CommandGroup;
import org.springframework.richclient.command.CommandRegistry;
import org.springframework.richclient.command.SwingActionAdapter;
import org.springframework.richclient.command.config.CommandFaceDescriptor;

public class JTaskPaneGroupCommandGroup extends CommandGroup {

	private List actions;
	private boolean expanded;
	private boolean special;
	private boolean scrollOnExpand;
	private boolean animated;

	public JTaskPaneGroupCommandGroup(String groupId, CommandRegistry registry) {
		super(groupId, registry);
		actions = new ArrayList();
	}

	public void setExpanded(boolean expanded) {
		if (hasChanged(isExpanded(), expanded)) {
			this.expanded = expanded;
			firePropertyChange("expanded", !expanded, expanded);
		}
	}

	public boolean isExpanded() {
		return expanded;
	}

	public boolean isAnimated() {
		return animated;
	}

	public void setAnimated(boolean animated) {
		if (hasChanged(isAnimated(), animated)) {
			this.animated = animated;
			firePropertyChange("animated", !animated, animated);
		}
	}

	public boolean isScrollOnExpand() {
		return scrollOnExpand;
	}

	public void setScrollOnExpand(boolean scrollOnExpand) {
		if (hasChanged(isScrollOnExpand(), scrollOnExpand)) {
			this.scrollOnExpand = scrollOnExpand;
			firePropertyChange("scrollOnExpand", !scrollOnExpand, scrollOnExpand);
		}
	}

	public boolean isSpecial() {
		return special;
	}

	public void setSpecial(boolean special) {
		if (hasChanged(isSpecial(), special)) {
			this.special = special;
			firePropertyChange("special", !special, special);
		}
	}

	public JTaskPaneGroup createTaskPaneGroup() {
		JTaskPaneGroup taskpane = new JTaskPaneGroup();
		taskpane.setExpanded(isExpanded());
		taskpane.setTitle(getText());
		taskpane.setAnimated(isAnimated());
		taskpane.setScrollOnExpand(isScrollOnExpand());
		taskpane.setSpecial(isSpecial());
		if (isFaceConfigured())
			taskpane.setToolTipText(getFaceDescriptor().getCaption());
		SwingActionAdapter adapter;
		for (Iterator members = actions.iterator(); members.hasNext(); taskpane.add(adapter)) {
			ActionCommand member = (ActionCommand)members.next();
			adapter = new SwingActionAdapter(member);
		}

		return taskpane;
	}

	public void addActionCommand(ActionCommand command) {
		super.add(command);
		actions.add(command);
	}
}
