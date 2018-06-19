// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   Version.java

package com.l2fprod.common;

import java.util.Date;

public class Version {

	private static Date buildDate = new Date(0x10d8467127eL);
	private static String buildTimestamp = new String("09/06/2006 08:32 PM");
	private static String year = new String("2005-2006");
	private static String version = new String("6.9.1");
	private static String project = new String("l2fprod-common");

	public Version() {
	}

	public static final Date getBuildDate() {
		return buildDate;
	}

	public static final String getBuildTimestamp() {
		return buildTimestamp;
	}

	public static final String getYear() {
		return year;
	}

	public static final String getVersion() {
		return version;
	}

	public static final String getProject() {
		return project;
	}

}
