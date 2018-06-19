// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JCollapsiblePane.java

package com.l2fprod.common.swing;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

// Referenced classes of package com.l2fprod.common.swing:
//			PercentLayout

public class JCollapsiblePane extends JPanel {
	private final class WrapperContainer extends JPanel {

		private BufferedImage img;
		private Container c;
		float alpha;

		public void showImage() {
			makeImage();
			c.setVisible(false);
		}

		public void showContent() {
			currentHeight = -1;
			c.setVisible(true);
		}

		void makeImage() {
			if (getGraphicsConfiguration() != null && getWidth() > 0) {
				Dimension dim = c.getPreferredSize();
				if (dim.height > 0) {
					img = getGraphicsConfiguration().createCompatibleImage(getWidth(), dim.height);
					c.setSize(getWidth(), dim.height);
					c.paint(img.getGraphics());
				} else {
					img = null;
				}
			}
		}

		public void paintComponent(Graphics g) {
			if (!useAnimation || c.isVisible()) {
				super.paintComponent(g);
			} else {
				if (img == null)
					makeImage();
				if (g != null && img != null)
					g.drawImage(img, 0, getHeight() - img.getHeight(), null);
			}
		}

		public void paint(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;
			java.awt.Composite oldComp = g2d.getComposite();
			java.awt.Composite alphaComp = AlphaComposite.getInstance(3, alpha);
			g2d.setComposite(alphaComp);
			super.paint(g2d);
			g2d.setComposite(oldComp);
		}


		public WrapperContainer(Container c) {
			super(new BorderLayout());
			alpha = 1.0F;
			this.c = c;
			add(c, "Center");
			if ((c instanceof JComponent) && !((JComponent)c).isOpaque())
				((JComponent)c).setOpaque(true);
		}
	}

	private final class AnimationListener
		implements ActionListener {

		private final Object ANIMATION_MUTEX;
		private int startHeight;
		private int finalHeight;
		private float animateAlpha;

		public void actionPerformed(ActionEvent e) {
label0:
			{
				synchronized (ANIMATION_MUTEX) {
					if (startHeight != finalHeight)
						break label0;
					animateTimer.stop();
					animateAlpha = animationParams.alphaEnd;
					if (finalHeight <= 0)
						break label0;
					wrapper.showContent();
					validate();
					firePropertyChange("animationState", null, "expanded");
				}
				return;
			}
			boolean contracting = startHeight > finalHeight;
			int delta_y = contracting ? -1 * animationParams.deltaY : animationParams.deltaY;
			int newHeight = wrapper.getHeight() + delta_y;
			if (contracting) {
				if (newHeight < finalHeight)
					newHeight = finalHeight;
			} else
			if (newHeight > finalHeight)
				newHeight = finalHeight;
			animateAlpha = (float)newHeight / (float)wrapper.c.getPreferredSize().height;
			Rectangle bounds = wrapper.getBounds();
			int oldHeight = bounds.height;
			bounds.height = newHeight;
			wrapper.setBounds(bounds);
			bounds = getBounds();
			bounds.height = (bounds.height - oldHeight) + newHeight;
			currentHeight = bounds.height;
			setBounds(bounds);
			startHeight = newHeight;
			if (contracting) {
				if (animateAlpha < animationParams.alphaEnd)
					animateAlpha = animationParams.alphaEnd;
				if (animateAlpha > animationParams.alphaStart)
					animateAlpha = animationParams.alphaStart;
			} else {
				if (animateAlpha > animationParams.alphaEnd)
					animateAlpha = animationParams.alphaEnd;
				if (animateAlpha < animationParams.alphaStart)
					animateAlpha = animationParams.alphaStart;
			}
			wrapper.alpha = animateAlpha;
			validate();
			obj;
			JVM INSTR monitorexit ;
			  goto _L1
			exception;
			throw exception;
_L1:
		}

		void validate() {
			Container parent = SwingUtilities.getAncestorOfClass(JCollapsiblePane.class$com$l2fprod$common$swing$JCollapsiblePane$JCollapsiblePaneContainer != null ? JCollapsiblePane.class$com$l2fprod$common$swing$JCollapsiblePane$JCollapsiblePaneContainer : (JCollapsiblePane.class$com$l2fprod$common$swing$JCollapsiblePane$JCollapsiblePaneContainer = JCollapsiblePane._mthclass$("com.l2fprod.common.swing.JCollapsiblePane$JCollapsiblePaneContainer")), JCollapsiblePane.this);
			if (parent != null)
				parent = ((JCollapsiblePaneContainer)parent).getValidatingContainer();
			else
				parent = getParent();
			if (parent != null) {
				if (parent instanceof JComponent)
					((JComponent)parent).revalidate();
				else
					parent.invalidate();
				parent.doLayout();
				parent.repaint();
			}
		}

		public void reinit(int startHeight, int stopHeight) {
			synchronized (ANIMATION_MUTEX) {
				firePropertyChange("animationState", null, "reinit");
				this.startHeight = startHeight;
				finalHeight = stopHeight;
				animateAlpha = animationParams.alphaStart;
				currentHeight = -1;
				wrapper.showImage();
			}
		}

		private AnimationListener() {
			ANIMATION_MUTEX = "Animation Synchronization Mutex";
			startHeight = 0;
			finalHeight = 0;
			animateAlpha = 1.0F;
		}

	}

	private static class AnimationParams {

		final int waitTime;
		final int deltaY;
		final float alphaStart;
		final float alphaEnd;

		public AnimationParams(int waitTime, int deltaY, float alphaStart, float alphaEnd) {
			this.waitTime = waitTime;
			this.deltaY = deltaY;
			this.alphaStart = alphaStart;
			this.alphaEnd = alphaEnd;
		}
	}

	public static interface JCollapsiblePaneContainer {

		public abstract Container getValidatingContainer();
	}

	private class ToggleAction extends AbstractAction
		implements PropertyChangeListener {

		public void putValue(String key, Object newValue) {
			super.putValue(key, newValue);
			if ("expandIcon".equals(key) || "collapseIcon".equals(key))
				updateIcon();
		}

		public void actionPerformed(ActionEvent e) {
			setCollapsed(!isCollapsed());
		}

		public void propertyChange(PropertyChangeEvent evt) {
			updateIcon();
		}

		void updateIcon() {
			if (isCollapsed())
				putValue("SmallIcon", getValue("expandIcon"));
			else
				putValue("SmallIcon", getValue("collapseIcon"));
		}

		public ToggleAction() {
			super("toggle");
			updateIcon();
			addPropertyChangeListener("collapsed", this);
		}
	}


	public static final String ANIMATION_STATE_KEY = "animationState";
	public static final String TOGGLE_ACTION = "toggle";
	public static final String COLLAPSE_ICON = "collapseIcon";
	public static final String EXPAND_ICON = "expandIcon";
	private boolean collapsed;
	private Timer animateTimer;
	private AnimationListener animator;
	private int currentHeight;
	private WrapperContainer wrapper;
	private boolean useAnimation;
	private AnimationParams animationParams;
	static Class class$com$l2fprod$common$swing$JCollapsiblePane$JCollapsiblePaneContainer; /* synthetic field */

	public JCollapsiblePane() {
		collapsed = false;
		currentHeight = -1;
		useAnimation = true;
		super.setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		panel.setLayout(new PercentLayout(1, 2));
		setContentPane(panel);
		animator = new AnimationListener();
		setAnimationParams(new AnimationParams(30, 8, 0.01F, 1.0F));
		getActionMap().put("toggle", new ToggleAction());
	}

	public void setContentPane(Container contentPanel) {
		if (contentPanel == null)
			throw new IllegalArgumentException("Content pane can't be null");
		if (wrapper != null)
			super.remove(wrapper);
		wrapper = new WrapperContainer(contentPanel);
		super.addImpl(wrapper, "Center", -1);
	}

	public Container getContentPane() {
		return wrapper.c;
	}

	public void setLayout(LayoutManager mgr) {
		if (wrapper != null)
			getContentPane().setLayout(mgr);
	}

	protected void addImpl(Component comp, Object constraints, int index) {
		getContentPane().add(comp, constraints, index);
	}

	public void remove(Component comp) {
		getContentPane().remove(comp);
	}

	public void remove(int index) {
		getContentPane().remove(index);
	}

	public void removeAll() {
		getContentPane().removeAll();
	}

	public void setAnimated(boolean animated) {
		if (animated != useAnimation) {
			useAnimation = animated;
			firePropertyChange("animated", !useAnimation, useAnimation);
		}
	}

	public boolean isAnimated() {
		return useAnimation;
	}

	public boolean isCollapsed() {
		return collapsed;
	}

	public void setCollapsed(boolean val) {
		if (collapsed != val) {
			collapsed = val;
			if (isAnimated()) {
				if (collapsed) {
					setAnimationParams(new AnimationParams(30, Math.max(8, wrapper.getHeight() / 10), 1.0F, 0.01F));
					animator.reinit(wrapper.getHeight(), 0);
					animateTimer.start();
				} else {
					setAnimationParams(new AnimationParams(30, Math.max(8, getContentPane().getPreferredSize().height / 10), 0.01F, 1.0F));
					animator.reinit(wrapper.getHeight(), getContentPane().getPreferredSize().height);
					animateTimer.start();
				}
			} else {
				wrapper.c.setVisible(!collapsed);
				invalidate();
				doLayout();
			}
			repaint();
			firePropertyChange("collapsed", !collapsed, collapsed);
		}
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	public Dimension getPreferredSize() {
		Dimension dim;
		if (!isAnimated()) {
			if (getContentPane().isVisible())
				dim = getContentPane().getPreferredSize();
			else
				dim = super.getPreferredSize();
		} else {
			dim = new Dimension(getContentPane().getPreferredSize());
			if (!getContentPane().isVisible() && currentHeight != -1)
				dim.height = currentHeight;
		}
		return dim;
	}

	private void setAnimationParams(AnimationParams params) {
		if (params == null)
			throw new IllegalArgumentException("params can't be null");
		if (animateTimer != null)
			animateTimer.stop();
		animationParams = params;
		animateTimer = new Timer(animationParams.waitTime, animator);
		animateTimer.setInitialDelay(0);
	}

	static Class _mthclass$(String x0) {
		return Class.forName(x0);
		ClassNotFoundException x1;
		x1;
		throw new NoClassDefFoundError(x1.getMessage());
	}







}
