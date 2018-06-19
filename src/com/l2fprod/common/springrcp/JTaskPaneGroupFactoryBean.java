// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JTaskPaneGroupFactoryBean.java

package com.l2fprod.common.springrcp;

import org.springframework.richclient.command.CommandGroup;
import org.springframework.richclient.command.CommandGroupFactoryBean;
import org.springframework.richclient.command.CommandRegistry;

// Referenced classes of package com.l2fprod.common.springrcp:
//			JTaskPaneGroupCommandGroup

public class JTaskPaneGroupFactoryBean extends CommandGroupFactoryBean {

	private String groupId;
	private Object encodedMembers[];
	private boolean expanded;
	private boolean special;
	private boolean scrollOnExpand;
	private boolean animated;
	private CommandRegistry registry;

	public JTaskPaneGroupFactoryBean() {
		expanded = true;
		special = false;
		scrollOnExpand = false;
		animated = true;
	}

	public void setBeanName(String beanName) {
		groupId = beanName;
	}

	public void setMembers(Object members[]) {
		encodedMembers = members;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public void setAnimated(boolean animated) {
		this.animated = animated;
	}

	public void setScrollOnExpand(boolean scrollOnExpand) {
		this.scrollOnExpand = scrollOnExpand;
	}

	public void setSpecial(boolean special) {
		this.special = special;
	}

	public void setCommandRegistry(CommandRegistry registry) {
		this.registry = registry;
	}

	protected CommandGroup createCommandGroup() {
		JTaskPaneGroupCommandGroup group = new JTaskPaneGroupCommandGroup(groupId, registry);
		group.setExpanded(expanded);
		group.setAnimated(animated);
		group.setScrollOnExpand(scrollOnExpand);
		group.setSpecial(special);
		if (encodedMembers != null) {
			for (int i = 0; i < encodedMembers.length; i++) {
				String actionName = (String)encodedMembers[i];
				org.springframework.richclient.command.ActionCommand childAction = registry.getActionCommand(actionName);
				group.addActionCommand(childAction);
			}

		}
		return group;
	}
}
