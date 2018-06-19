// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   FakeFileSystemView.java

package com.l2fprod.common.demo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

public class FakeFileSystemView extends FileSystemView {
	static class FakeFile extends File {

		public boolean isDirectory() {
			return true;
		}

		public FakeFile(String s) {
			super(s);
		}
	}


	private Map files;

	public FakeFileSystemView() {
		files = new HashMap();
		files.put("desktop", new FakeFile("Desktop"));
		files.put("computer", new FakeFile("My Computer"));
		files.put("A", new FakeFile("A"));
		files.put("C", new FakeFile("C"));
		files.put("D", new FakeFile("D"));
		files.put("getFiles(My Computer)", new File[] {
			(File)files.get("A"), (File)files.get("C"), (File)files.get("D")
		});
		files.put("network", new FakeFile("My Network Places"));
		files.put("getRoots", new File[] {
			(File)files.get("desktop")
		});
		files.put("getFiles(Desktop)", new File[] {
			(File)files.get("computer"), (File)files.get("network")
		});
		FakeFile folders[] = {
			new FakeFile("Folder 1"), new FakeFile("Folder 2"), new FakeFile("Folder 3")
		};
		files.put("getFiles(C)", folders);
		files.put("getFiles(D)", folders);
	}

	public File createNewFolder(File containingDir) {
		return null;
	}

	public File createFileObject(File dir, String filename) {
		return super.createFileObject(dir, filename);
	}

	public File createFileObject(String path) {
		return super.createFileObject(path);
	}

	protected File createFileSystemRoot(File f) {
		return super.createFileSystemRoot(f);
	}

	public File getChild(File parent, String fileName) {
		return super.getChild(parent, fileName);
	}

	public File getDefaultDirectory() {
		return new FakeFile("Default");
	}

	public File[] getFiles(File dir, boolean useFileHiding) {
		if (dir.getName().startsWith("Folder"))
			return (new FakeFile[] {
				new FakeFile(dir.getName() + ".1"), new FakeFile(dir.getName() + ".2"), new FakeFile(dir.getName() + ".3")
			});
		File children[] = (File[])files.get("getFiles(" + dir.getName() + ")");
		if (children == null)
			return new File[0];
		else
			return children;
	}

	public File getHomeDirectory() {
		return new FakeFile("Home");
	}

	public File getParentDirectory(File dir) {
		return null;
	}

	public File[] getRoots() {
		return (File[])files.get("getRoots");
	}

	public String getSystemDisplayName(File f) {
		return f.getName();
	}

	public Icon getSystemIcon(File f) {
		return null;
	}

	public String getSystemTypeDescription(File f) {
		return "Description";
	}

	public boolean isComputerNode(File dir) {
		return files.get("computer") == dir;
	}

	public boolean isDrive(File dir) {
		return "C".equals(dir.getName()) || "D".equals(dir.getName());
	}

	public boolean isFileSystem(File f) {
		return false;
	}

	public boolean isFileSystemRoot(File dir) {
		return false;
	}

	public boolean isFloppyDrive(File dir) {
		return "A".equals(dir.getName());
	}

	public boolean isHiddenFile(File f) {
		return false;
	}

	public boolean isParent(File folder, File file) {
		return false;
	}

	public boolean isRoot(File f) {
		return files.get("desktop") == f;
	}

	public Boolean isTraversable(File f) {
		return Boolean.TRUE;
	}
}
