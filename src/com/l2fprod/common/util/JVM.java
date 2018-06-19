// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JVM.java

package com.l2fprod.common.util;


public class JVM {

	public static final int JDK1_0 = 10;
	public static final int JDK1_1 = 11;
	public static final int JDK1_2 = 12;
	public static final int JDK1_3 = 13;
	public static final int JDK1_4 = 14;
	public static final int JDK1_5 = 15;
	public static final int JDK1_6 = 16;
	private static JVM current = new JVM();
	private int jdkVersion;

	public static JVM current() {
		return current;
	}

	public JVM() {
		this(System.getProperty("java.version"));
	}

	public JVM(String p_JavaVersion) {
		if (p_JavaVersion.startsWith("1.6."))
			jdkVersion = 16;
		else
		if (p_JavaVersion.startsWith("1.5."))
			jdkVersion = 15;
		else
		if (p_JavaVersion.startsWith("1.4."))
			jdkVersion = 14;
		else
		if (p_JavaVersion.startsWith("1.3."))
			jdkVersion = 13;
		else
		if (p_JavaVersion.startsWith("1.2."))
			jdkVersion = 12;
		else
		if (p_JavaVersion.startsWith("1.1."))
			jdkVersion = 11;
		else
		if (p_JavaVersion.startsWith("1.0."))
			jdkVersion = 10;
		else
			jdkVersion = 13;
	}

	public boolean isOrLater(int p_Version) {
		return jdkVersion >= p_Version;
	}

	public boolean isOneDotOne() {
		return jdkVersion == 11;
	}

	public boolean isOneDotTwo() {
		return jdkVersion == 12;
	}

	public boolean isOneDotThree() {
		return jdkVersion == 13;
	}

	public boolean isOneDotFour() {
		return jdkVersion == 14;
	}

	public boolean isOneDotFive() {
		return jdkVersion == 15;
	}

	public boolean isOneDotSix() {
		return jdkVersion == 16;
	}

}
