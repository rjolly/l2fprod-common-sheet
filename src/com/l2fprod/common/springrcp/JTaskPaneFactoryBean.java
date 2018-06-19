// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JTaskPaneFactoryBean.java

package com.l2fprod.common.springrcp;

import org.springframework.richclient.command.CommandGroup;
import org.springframework.richclient.command.CommandGroupFactoryBean;
import org.springframework.richclient.command.CommandRegistry;

// Referenced classes of package com.l2fprod.common.springrcp:
//			JTaskPaneCommandGroup, JTaskPaneGroupCommandGroup

public class JTaskPaneFactoryBean extends CommandGroupFactoryBean {

	private String groupId;
	private Object encodedMembers[];
	private CommandRegistry registry;

	public JTaskPaneFactoryBean() {
	}

	public void setBeanName(String beanName) {
		groupId = beanName;
	}

	public void setMembers(Object members[]) {
		encodedMembers = members;
	}

	public void setCommandRegistry(CommandRegistry registry) {
		this.registry = registry;
	}

	protected CommandGroup createCommandGroup() {
		JTaskPaneCommandGroup group = new JTaskPaneCommandGroup(groupId, registry);
		if (encodedMembers != null) {
			for (int i = 0; i < encodedMembers.length; i++) {
				JTaskPaneGroupCommandGroup childGroup = (JTaskPaneGroupCommandGroup)encodedMembers[i];
				group.addTaskPaneGroup(childGroup);
			}

		}
		return group;
	}
}
