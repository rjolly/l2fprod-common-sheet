// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JTaskPaneCommandGroup.java

package com.l2fprod.common.springrcp;

import com.l2fprod.common.swing.JTaskPane;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.richclient.command.CommandGroup;
import org.springframework.richclient.command.CommandRegistry;

// Referenced classes of package com.l2fprod.common.springrcp:
//			JTaskPaneGroupCommandGroup

public class JTaskPaneCommandGroup extends CommandGroup {

	private List groups;

	public JTaskPaneCommandGroup(String groupId, CommandRegistry registry) {
		super(groupId, registry);
		groups = new ArrayList();
	}

	public JTaskPane createTaskPane() {
		JTaskPane taskPane = new JTaskPane();
		JTaskPaneGroupCommandGroup member;
		for (Iterator it = groups.iterator(); it.hasNext(); taskPane.add(member.createTaskPaneGroup()))
			member = (JTaskPaneGroupCommandGroup)it.next();

		return taskPane;
	}

	public void addTaskPaneGroup(JTaskPaneGroupCommandGroup childGroup) {
		super.add(childGroup);
		groups.add(childGroup);
	}
}
