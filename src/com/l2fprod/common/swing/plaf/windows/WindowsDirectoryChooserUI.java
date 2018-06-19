// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   WindowsDirectoryChooserUI.java

package com.l2fprod.common.swing.plaf.windows;

import com.l2fprod.common.swing.JDirectoryChooser;
import com.l2fprod.common.swing.LookAndFeelTweaks;
import com.l2fprod.common.swing.plaf.DirectoryChooserUI;
import com.l2fprod.common.swing.tree.LazyMutableTreeNode;
import com.l2fprod.common.util.OS;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Stack;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileView;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicFileChooserUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class WindowsDirectoryChooserUI extends BasicFileChooserUI
	implements DirectoryChooserUI {
	private static final class QueueItem {

		FileTreeNode node;
		JTree tree;

		public QueueItem(FileTreeNode node, JTree tree) {
			this.node = node;
			this.tree = tree;
		}
	}

	private static final class Queue extends Thread {

		private volatile Stack nodes;
		private Object lock;
		private volatile boolean running;

		public void add(FileTreeNode node, JTree tree) {
			if (!isAlive())
				throw new IllegalArgumentException("Queue is no longer alive");
			synchronized (lock) {
				if (running) {
					nodes.addElement(new QueueItem(node, tree));
					lock.notifyAll();
				}
			}
		}

		public void run() {
			do {
				if (!running)
					break;
				while (nodes.size() > 0)  {
					QueueItem item = (QueueItem)nodes.pop();
					final FileTreeNode node = item.node;
					final JTree tree = item.tree;
					node.getChildCount();
					Runnable runnable = new Runnable() {

						public void run() {
							((DefaultTreeModel)tree.getModel()).nodeChanged(node);
							tree.repaint();
						}

					};
					try {
						SwingUtilities.invokeAndWait(runnable);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
					catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
				try {
					synchronized (lock) {
						lock.wait(5000L);
					}
					if (nodes.size() == 0)
						running = false;
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (true);
		}

		public Queue() {
			super("DirectoryChooser-BackgroundLoader");
			nodes = new Stack();
			lock = new Object();
			running = true;
			setDaemon(true);
		}
	}

	protected class WindowsFileView extends javax.swing.plaf.basic.BasicFileChooserUI.BasicFileView {

		public Icon getIcon(File f) {
			Icon icon = getCachedIcon(f);
			if (icon != null)
				return icon;
			if (f != null)
				icon = getFileChooser().getFileSystemView().getSystemIcon(f);
			if (icon == null)
				icon = super.getIcon(f);
			cacheIcon(f, icon);
			return icon;
		}

		protected WindowsFileView() {
		}
	}

	private class FileTreeNode extends LazyMutableTreeNode
		implements Comparable {

		public boolean canEnqueue() {
			return !isLoaded() && !chooser.getFileSystemView().isFloppyDrive(getFile()) && !chooser.getFileSystemView().isFileSystemRoot(getFile());
		}

		public boolean isLeaf() {
			if (!isLoaded())
				return false;
			else
				return super.isLeaf();
		}

		protected void loadChildren() {
			FileTreeNode nodes[] = getChildren();
			int i = 0;
			for (int c = nodes.length; i < c; i++)
				add(nodes[i]);

		}

		private FileTreeNode[] getChildren() {
			File files[] = chooser.getFileSystemView().getFiles(getFile(), chooser.isFileHidingEnabled());
			ArrayList nodes = new ArrayList();
			if (files != null) {
				int i = 0;
				for (int c = files.length; i < c; i++)
					if (files[i].isDirectory())
						nodes.add(new FileTreeNode(files[i]));

			}
			FileTreeNode result[] = (FileTreeNode[])nodes.toArray(new FileTreeNode[0]);
			Arrays.sort(result);
			return result;
		}

		public File getFile() {
			return (File)getUserObject();
		}

		public String toString() {
			return chooser.getFileSystemView().getSystemDisplayName((File)getUserObject());
		}

		public int compareTo(Object o) {
			if (!(o instanceof FileTreeNode))
				return 1;
			else
				return getFile().compareTo(((FileTreeNode)o).getFile());
		}

		public void clear() {
			super.clear();
			((DefaultTreeModel)tree.getModel()).nodeStructureChanged(this);
		}

		public FileTreeNode(File file) {
			super(file);
		}
	}

	private class MyComputerTreeNode extends LazyMutableTreeNode {

		protected void loadChildren() {
			FileSystemView fsv = (FileSystemView)getUserObject();
			File roots[] = fsv.getRoots();
			if (roots != null) {
				Arrays.sort(roots);
				int i = 0;
				for (int c = roots.length; i < c; i++)
					add(new FileTreeNode(roots[i]));

			}
		}

		public String toString() {
			return "/";
		}

		public MyComputerTreeNode(FileSystemView fsv) {
			super(fsv);
		}
	}

	private class FileSystemTreeModel extends DefaultTreeModel {

		public FileSystemTreeModel(FileSystemView fsv) {
			super(new MyComputerTreeNode(fsv), false);
		}
	}

	private class NewFolderAction extends AbstractAction {

		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = getFileChooser();
			File currentDirectory = fc.getCurrentDirectory();
			if (!currentDirectory.canWrite()) {
				JOptionPane.showMessageDialog(fc, UIManager.getString("DirectoryChooser.cantCreateFolderHere"), UIManager.getString("DirectoryChooser.cantCreateFolderHere.title"), 0);
				return;
			}
			String newFolderName = JOptionPane.showInputDialog(fc, UIManager.getString("DirectoryChooser.enterFolderName"), newFolderText, 3);
			if (newFolderName != null) {
				File newFolder = new File(currentDirectory, newFolderName);
				if (newFolder.mkdir()) {
					if (fc.isMultiSelectionEnabled())
						fc.setSelectedFiles(new File[] {
							newFolder
						});
					else
						fc.setSelectedFile(newFolder);
					fc.rescanCurrentDirectory();
				} else {
					JOptionPane.showMessageDialog(fc, UIManager.getString("DirectoryChooser.createFolderFailed"), UIManager.getString("DirectoryChooser.createFolderFailed.title"), 0);
				}
			}
		}

		private NewFolderAction() {
		}

	}

	private class FileSystemTreeRenderer extends DefaultTreeCellRenderer {

		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, sel, expanded, false, row, hasFocus);
			if (value instanceof FileTreeNode) {
				FileTreeNode node = (FileTreeNode)value;
				setText(getFileView(chooser).getName(node.getFile()));
				if (!OS.isMacOSX() || !UIManager.getLookAndFeel().isNativeLookAndFeel())
					setIcon(getFileView(chooser).getIcon(node.getFile()));
			}
			return this;
		}

		private FileSystemTreeRenderer() {
		}

	}

	private class TreeExpansion
		implements TreeExpansionListener {

		public void treeCollapsed(TreeExpansionEvent treeexpansionevent) {
		}

		public void treeExpanded(TreeExpansionEvent event) {
			if (event.getPath() != null) {
				Object lastElement = event.getPath().getLastPathComponent();
				if ((lastElement instanceof FileTreeNode) && useNodeQueue && ((FileTreeNode)lastElement).isLoaded())
					enqueueChildren((FileTreeNode)lastElement);
			}
		}

		private TreeExpansion() {
		}

	}

	private class ApproveSelectionAction extends AbstractAction {

		public void actionPerformed(ActionEvent e) {
			setSelectedFiles();
			chooser.approveSelection();
		}

		public ApproveSelectionAction() {
			setEnabled(false);
		}
	}

	private class SelectionListener
		implements TreeSelectionListener {

		public void valueChanged(TreeSelectionEvent e) {
			getApproveSelectionAction().setEnabled(tree.getSelectionCount() > 0);
			setSelectedFiles();
			TreePath currentDirectoryPath = tree.getSelectionPath();
			if (currentDirectoryPath != null) {
				File currentDirectory = ((FileTreeNode)currentDirectoryPath.getLastPathComponent()).getFile();
				chooser.setCurrentDirectory(currentDirectory);
			}
		}

		private SelectionListener() {
		}

	}

	private class ChangeListener
		implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {
			if ("ApproveButtonTextChangedProperty".equals(evt.getPropertyName()))
				updateView(chooser);
			if ("MultiSelectionEnabledChangedProperty".equals(evt.getPropertyName()))
				if (chooser.isMultiSelectionEnabled())
					tree.getSelectionModel().setSelectionMode(4);
				else
					tree.getSelectionModel().setSelectionMode(1);
			if ("directoryChanged".equals(evt.getPropertyName()))
				findFile(chooser.getCurrentDirectory(), false, false);
			if ("AccessoryChangedProperty".equals(evt.getPropertyName())) {
				Component oldValue = (Component)evt.getOldValue();
				Component newValue = (Component)evt.getNewValue();
				if (oldValue != null)
					chooser.remove(oldValue);
				if (newValue != null)
					chooser.add("North", newValue);
				chooser.revalidate();
				chooser.repaint();
			}
			if ("ControlButtonsAreShownChangedProperty".equals(evt.getPropertyName()))
				updateView(chooser);
			if ("showingCreateDirectory".equals(evt.getPropertyName()))
				updateView(chooser);
		}

		private ChangeListener() {
		}

	}

	private class UpdateAction extends AbstractAction {

		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = getFileChooser();
			fc.rescanCurrentDirectory();
		}

		private UpdateAction() {
		}

	}


	private static Queue nodeQueue;
	private JFileChooser chooser;
	private JTree tree;
	private JScrollPane treeScroll;
	private JButton approveButton;
	private JButton cancelButton;
	private JPanel buttonPanel;
	private javax.swing.plaf.basic.BasicFileChooserUI.BasicFileView fileView;
	private Action approveSelectionAction;
	private boolean useNodeQueue;
	private JButton newFolderButton;
	private Action newFolderAction;
	private String newFolderText;
	private String newFolderToolTipText;

	public static ComponentUI createUI(JComponent c) {
		return new WindowsDirectoryChooserUI((JFileChooser)c);
	}

	public WindowsDirectoryChooserUI(JFileChooser chooser) {
		super(chooser);
		fileView = new WindowsFileView();
		approveSelectionAction = new ApproveSelectionAction();
		newFolderAction = new NewFolderAction();
		newFolderText = null;
		newFolderToolTipText = null;
	}

	public void rescanCurrentDirectory(JFileChooser fc) {
		super.rescanCurrentDirectory(fc);
		findFile(chooser.getSelectedFile() != null ? chooser.getSelectedFile() : chooser.getCurrentDirectory(), true, true);
	}

	public void ensureFileIsVisible(JFileChooser fc, File f) {
		super.ensureFileIsVisible(fc, f);
		findFile(f, false, false);
	}

	protected String getToolTipText(MouseEvent event) {
		TreePath path = tree.getPathForLocation(event.getX(), event.getY());
		if (path != null && (path.getLastPathComponent() instanceof FileTreeNode)) {
			FileTreeNode node = (FileTreeNode)path.getLastPathComponent();
			String typeDescription = getFileView(chooser).getTypeDescription(node.getFile());
			if (typeDescription == null || typeDescription.length() == 0)
				return node.toString();
			else
				return node.toString() + " - " + typeDescription;
		} else {
			return null;
		}
	}

	public void installComponents(JFileChooser chooser) {
		this.chooser = chooser;
		chooser.setLayout(LookAndFeelTweaks.createBorderLayout());
		chooser.setFileSelectionMode(1);
		Component accessory = chooser.getAccessory();
		if (accessory != null)
			chooser.add("North", chooser.getAccessory());
		tree = new JTree() {

			public String getToolTipText(MouseEvent event) {
				String tip = WindowsDirectoryChooserUI.this.getToolTipText(event);
				if (tip == null)
					return super.getToolTipText(event);
				else
					return tip;
			}

		};
		tree.addTreeExpansionListener(new TreeExpansion());
		tree.setModel(new FileSystemTreeModel(chooser.getFileSystemView()));
		tree.setRootVisible(false);
		tree.setShowsRootHandles(false);
		tree.setCellRenderer(new FileSystemTreeRenderer());
		tree.setToolTipText("");
		chooser.add("Center", treeScroll = new JScrollPane(tree));
		treeScroll.setPreferredSize(new Dimension(300, 300));
		approveButton = new JButton();
		approveButton.setAction(getApproveSelectionAction());
		cancelButton = new JButton();
		cancelButton.setAction(getCancelSelectionAction());
		cancelButton.setDefaultCapable(true);
		newFolderButton = new JButton();
		newFolderButton.setAction(getNewFolderAction());
		buttonPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 0, 25);
		gridBagConstraints.anchor = 13;
		gridBagConstraints.weightx = 1.0D;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 0;
		buttonPanel.add(Box.createHorizontalStrut(0), gridBagConstraints);
		buttonPanel.add(newFolderButton, gridBagConstraints);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 0, 6);
		gridBagConstraints.weightx = 0.0D;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 1;
		buttonPanel.add(approveButton, gridBagConstraints);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.weightx = 0.0D;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 2;
		buttonPanel.add(cancelButton, gridBagConstraints);
		chooser.add("South", buttonPanel);
		updateView(chooser);
	}

	public Action getNewFolderAction() {
		return newFolderAction;
	}

	protected void installStrings(JFileChooser fc) {
		super.installStrings(fc);
		saveButtonToolTipText = UIManager.getString("DirectoryChooser.saveButtonToolTipText");
		openButtonToolTipText = UIManager.getString("DirectoryChooser.openButtonToolTipText");
		cancelButtonToolTipText = UIManager.getString("DirectoryChooser.cancelButtonToolTipText");
		newFolderText = UIManager.getString("DirectoryChooser.newFolderButtonText");
		newFolderToolTipText = UIManager.getString("DirectoryChooser.newFolderButtonToolTipText");
	}

	protected void uninstallStrings(JFileChooser fc) {
		super.uninstallStrings(fc);
		newFolderText = null;
		newFolderToolTipText = null;
	}

	public void uninstallComponents(JFileChooser chooser) {
		chooser.remove(treeScroll);
		chooser.remove(buttonPanel);
	}

	public FileView getFileView(JFileChooser fc) {
		return fileView;
	}

	protected void installListeners(JFileChooser fc) {
		super.installListeners(fc);
		tree.addTreeSelectionListener(new SelectionListener());
		fc.getActionMap().put("refreshTree", new UpdateAction());
		fc.getInputMap(1).put(KeyStroke.getKeyStroke("F5"), "refreshTree");
	}

	protected void uninstallListeners(JFileChooser fc) {
		super.uninstallListeners(fc);
	}

	public PropertyChangeListener createPropertyChangeListener(JFileChooser fc) {
		return new ChangeListener();
	}

	private void updateView(JFileChooser chooser) {
		if (chooser.getApproveButtonText() != null) {
			approveButton.setText(chooser.getApproveButtonText());
			approveButton.setMnemonic(chooser.getApproveButtonMnemonic());
		} else
		if (0 == chooser.getDialogType()) {
			approveButton.setText(openButtonText);
			approveButton.setToolTipText(openButtonToolTipText);
			approveButton.setMnemonic(openButtonMnemonic);
		} else {
			approveButton.setText(saveButtonText);
			approveButton.setToolTipText(saveButtonToolTipText);
			approveButton.setMnemonic(saveButtonMnemonic);
		}
		cancelButton.setText(cancelButtonText);
		cancelButton.setMnemonic(cancelButtonMnemonic);
		newFolderButton.setText(newFolderText);
		newFolderButton.setToolTipText(newFolderToolTipText);
		newFolderButton.setVisible(((JDirectoryChooser)chooser).isShowingCreateDirectory());
		buttonPanel.setVisible(chooser.getControlButtonsAreShown());
		approveButton.setPreferredSize(null);
		cancelButton.setPreferredSize(null);
		Dimension preferredSize = approveButton.getMinimumSize();
		preferredSize = new Dimension(Math.max(preferredSize.width, cancelButton.getPreferredSize().width), preferredSize.height);
		approveButton.setPreferredSize(preferredSize);
		cancelButton.setPreferredSize(preferredSize);
	}

	private void findFile(File fileToLocate, boolean selectFile, boolean reload) {
		File file;
		if (fileToLocate == null || !fileToLocate.isDirectory())
			return;
		file = null;
		try {
			file = fileToLocate.getCanonicalFile();
		}
		catch (Exception e) {
			return;
		}
		useNodeQueue = false;
		java.util.List files;
		java.util.List path;
		DefaultMutableTreeNode node;
		boolean found;
		files = new ArrayList();
		files.add(file);
		while ((file = chooser.getFileSystemView().getParentDirectory(file)) != null) 
			files.add(0, file);
		path = new ArrayList();
		node = (DefaultMutableTreeNode)tree.getModel().getRoot();
		path.add(node);
		found = true;
_L4:
		if (files.size() <= 0 || !found) goto _L2; else goto _L1
_L1:
		int i;
		int c;
		found = false;
		i = 0;
		c = node.getChildCount();
_L7:
		if (i >= c) goto _L4; else goto _L3
_L3:
		DefaultMutableTreeNode current;
		File f;
		current = (DefaultMutableTreeNode)node.getChildAt(i);
		f = ((FileTreeNode)current).getFile();
		if (!files.get(0).equals(f)) goto _L6; else goto _L5
_L5:
		path.add(current);
		files.remove(0);
		node = current;
		found = true;
		  goto _L4
_L6:
		i++;
		  goto _L7
_L2:
		TreePath pathToSelect;
		pathToSelect = new TreePath(path.toArray());
		if ((pathToSelect.getLastPathComponent() instanceof FileTreeNode) && reload) {
			((FileTreeNode)pathToSelect.getLastPathComponent()).clear();
			enqueueChildren((FileTreeNode)pathToSelect.getLastPathComponent());
		}
		useNodeQueue = true;
		break MISSING_BLOCK_LABEL_304;
		Exception exception;
		exception;
		useNodeQueue = true;
		throw exception;
		if (selectFile) {
			tree.expandPath(pathToSelect);
			tree.setSelectionPath(pathToSelect);
		}
		tree.makeVisible(pathToSelect);
		return;
		  goto _L4
	}

	public Action getApproveSelectionAction() {
		return approveSelectionAction;
	}

	private void setSelectedFiles() {
		TreePath selectedPaths[] = tree.getSelectionPaths();
		if (selectedPaths == null || selectedPaths.length == 0) {
			chooser.setSelectedFile(null);
			return;
		}
		java.util.List files = new ArrayList();
		int i = 0;
		for (int c = selectedPaths.length; i < c; i++) {
			LazyMutableTreeNode node = (LazyMutableTreeNode)selectedPaths[i].getLastPathComponent();
			if (node instanceof FileTreeNode) {
				File f = ((FileTreeNode)node).getFile();
				files.add(f);
			}
		}

		chooser.setSelectedFiles((File[])files.toArray(new File[0]));
	}

	private void enqueueChildren(FileTreeNode node) {
		for (Enumeration e = node.children(); e.hasMoreElements(); addToQueue((FileTreeNode)e.nextElement(), tree));
	}

	private static synchronized void addToQueue(FileTreeNode node, JTree tree) {
		if (nodeQueue == null || !nodeQueue.isAlive()) {
			nodeQueue = new Queue();
			nodeQueue.start();
		}
		if (node.canEnqueue())
			nodeQueue.add(node, tree);
	}








}
