// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   BooleanAsCheckBoxPropertyEditor.java

package com.l2fprod.common.beans.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;

// Referenced classes of package com.l2fprod.common.beans.editor:
//			AbstractPropertyEditor

public class BooleanAsCheckBoxPropertyEditor extends AbstractPropertyEditor {

	public BooleanAsCheckBoxPropertyEditor() {
		editor = new JCheckBox();
		((JCheckBox)editor).setOpaque(false);
		((JCheckBox)editor).addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				firePropertyChange(((JCheckBox)editor).isSelected() ? ((Object) (Boolean.FALSE)) : ((Object) (Boolean.TRUE)), ((JCheckBox)editor).isSelected() ? ((Object) (Boolean.TRUE)) : ((Object) (Boolean.FALSE)));
				((JCheckBox)editor).transferFocus();
			}

		});
	}

	public Object getValue() {
		return ((JCheckBox)editor).isSelected() ? Boolean.TRUE : Boolean.FALSE;
	}

	public void setValue(Object value) {
		((JCheckBox)editor).setSelected(Boolean.TRUE.equals(value));
	}
}
