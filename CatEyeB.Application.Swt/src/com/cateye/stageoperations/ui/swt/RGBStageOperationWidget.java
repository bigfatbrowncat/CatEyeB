package com.cateye.stageoperations.ui.swt;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;

public class RGBStageOperationWidget extends Composite {

	public RGBStageOperationWidget(Composite parent, int style) {
		super(parent, style);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		rowLayout.justify = true;
		setLayout(rowLayout);
		
		Label label = new Label(this, SWT.NONE);
		label.setText("Red:");
		
		Scale scale = new Scale(this, SWT.NONE);
		scale.setSelection(50);
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setLayoutData(new RowData(34, SWT.DEFAULT));
		label_1.setText("Green:");
		
		Scale scale_1 = new Scale(this, SWT.NONE);
		scale_1.setLayoutData(new RowData(171, SWT.DEFAULT));
		scale_1.setSelection(50);
		
		Label label_2 = new Label(this, SWT.NONE);
		label_2.setLayoutData(new RowData(28, SWT.DEFAULT));
		label_2.setText("Blue:");
		
		Scale scale_2 = new Scale(this, SWT.NONE);
		scale_2.setSelection(50);
		// TODO Auto-generated constructor stub
	}

}
