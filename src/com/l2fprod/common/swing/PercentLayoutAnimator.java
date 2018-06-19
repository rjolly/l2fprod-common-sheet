// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   PercentLayoutAnimator.java

package com.l2fprod.common.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.Timer;

// Referenced classes of package com.l2fprod.common.swing:
//			PercentLayout

public class PercentLayoutAnimator
	implements ActionListener {
	class PercentTask {

		Component component;
		float targetPercent;
		float currentPercent;
		boolean completed;
		boolean incrementing;
		float delta;

		public void execute() {
			currentPercent += delta;
			if (incrementing) {
				if (currentPercent > targetPercent) {
					currentPercent = targetPercent;
					completed = true;
				}
			} else
			if (currentPercent < targetPercent) {
				currentPercent = targetPercent;
				completed = true;
			}
			layout.setConstraint(component, new PercentLayout.PercentConstraint(currentPercent));
		}

		public boolean isCompleted() {
			return completed;
		}

		public PercentTask(Component component, float currentPercent, float targetPercent) {
			this.component = component;
			this.currentPercent = currentPercent;
			this.targetPercent = targetPercent;
			float diff = targetPercent - currentPercent;
			incrementing = diff > 0.0F;
			delta = diff / 10F;
		}
	}


	private Timer animatorTimer;
	private java.util.List tasks;
	private PercentLayout layout;
	private Container container;

	public PercentLayoutAnimator(Container container, PercentLayout layout) {
		tasks = new ArrayList();
		this.container = container;
		this.layout = layout;
	}

	public void setTargetPercent(Component component, float percent) {
		PercentLayout.Constraint oldConstraint = layout.getConstraint(component);
		if (oldConstraint instanceof PercentLayout.PercentConstraint)
			setTargetPercent(component, ((PercentLayout.PercentConstraint)oldConstraint).floatValue(), percent);
	}

	public void setTargetPercent(Component component, float startPercent, float endPercent) {
		tasks.add(new PercentTask(component, startPercent, endPercent));
	}

	public void start() {
		animatorTimer = new Timer(15, this);
		animatorTimer.start();
	}

	public void stop() {
		animatorTimer.stop();
	}

	protected void complete() {
		animatorTimer.stop();
	}

	public void actionPerformed(ActionEvent e) {
		boolean allCompleted = true;
		Iterator iter = tasks.iterator();
		do {
			if (!iter.hasNext())
				break;
			PercentTask element = (PercentTask)iter.next();
			if (!element.isCompleted()) {
				allCompleted = false;
				element.execute();
			}
		} while (true);
		container.invalidate();
		container.doLayout();
		container.repaint();
		if (allCompleted)
			complete();
	}

}
