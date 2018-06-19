// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JDirectoryChooserBinder.java

package com.l2fprod.common.springrcp;

import com.l2fprod.common.swing.JDirectoryChooser;
import java.util.Map;
import javax.swing.JComponent;
import org.springframework.binding.form.FormModel;
import org.springframework.richclient.form.binding.Binding;
import org.springframework.richclient.form.binding.support.AbstractBinder;

// Referenced classes of package com.l2fprod.common.springrcp:
//			JDirectoryChooserBinding

public class JDirectoryChooserBinder extends AbstractBinder {

	protected JDirectoryChooserBinder() {
		super(java.io.File.class);
	}

	protected JComponent createControl(Map context) {
		return new JDirectoryChooser();
	}

	protected Binding doBind(JComponent control, FormModel formModel, String formPropertyPath, Map context) {
		JDirectoryChooser directoryChooser = (JDirectoryChooser)control;
		return new JDirectoryChooserBinding(formModel, formPropertyPath, directoryChooser);
	}
}
