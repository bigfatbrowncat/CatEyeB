package com.cateye.stageoperations.rgb.ui.swt;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

import com.cateye.core.IPropertyChangedListener;
import com.cateye.stageoperations.rgb.RGBStageOperation;

public class RGBStageOperationWidget extends Composite
{
	private final double ln2 = Math.log(2);
	
	private RGBStageOperation rgbStageOperation;
	private Scale rScale, gScale, bScale;
	private boolean changingProperty = false;
	
	private final SelectionListener rgbScaleSelectionListener = new SelectionListener()
	{
		
		@Override
		public void widgetSelected(SelectionEvent arg0)
		{
			if (!changingProperty)
			{
				try
				{
					changingProperty = true;
					double value = Math.pow(2, ((double)((Scale)arg0.widget).getSelection()) / 100 - 2);

					if (rgbStageOperation != null)
					if (arg0.widget == rScale) rgbStageOperation.setR(value);
					if (arg0.widget == gScale) rgbStageOperation.setG(value);
					if (arg0.widget == bScale) rgbStageOperation.setB(value);
				}
				finally
				{
					changingProperty = false;
				}
			}
		}
		
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	private IPropertyChangedListener rgbPropertyChangedListener = new IPropertyChangedListener() 
	{
		
		@Override
		public void invoke(Object sender, String propertyName, Object newValue) 
		{
			if (!changingProperty)
			{
				try
				{
					changingProperty = true;
					if (propertyName.equals("r"))
						rScale.setSelection((int)(100 * (Math.log(rgbStageOperation.getR()) / ln2 + 2)));
					if (propertyName.equals("g"))
						gScale.setSelection((int)(100 * (Math.log(rgbStageOperation.getG()) / ln2 + 2)));
					if (propertyName.equals("b"))
						bScale.setSelection((int)(100 * (Math.log(rgbStageOperation.getB()) / ln2 + 2)));
				}
				finally
				{
					changingProperty = false;
				}
			}
			
		}
	};
			
	public RGBStageOperationWidget(Composite parent, int style) 
	{
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		Label lblRed = new Label(this, SWT.NONE);
		lblRed.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblRed.setText("Red:");
		
		rScale = new Scale(this, SWT.NONE);
		rScale.setMaximum(400);
		rScale.setSelection(200);
		rScale.setTouchEnabled(true);
		rScale.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		rScale.addSelectionListener(rgbScaleSelectionListener);
		
		Label lblGreen = new Label(this, SWT.NONE);
		lblGreen.setText("Green:");
		
		gScale = new Scale(this, SWT.NONE);
		gScale.setMaximum(400);
		gScale.setSelection(200);
		gScale.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		gScale.addSelectionListener(rgbScaleSelectionListener);
		
		Label lblBlue = new Label(this, SWT.NONE);
		lblBlue.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblBlue.setText("Blue:");
		
		bScale = new Scale(this, SWT.NONE);
		bScale.setMaximum(400);
		bScale.setSelection(200);
		bScale.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		bScale.addSelectionListener(rgbScaleSelectionListener);

		// TODO Auto-generated constructor stub
	}

	protected void updateFromStageOperation()
	{
		if (rgbStageOperation != null)
		{		
			rScale.setSelection((int)(100 * (Math.log(rgbStageOperation.getR()) / ln2 + 2)));
			gScale.setSelection((int)(100 * (Math.log(rgbStageOperation.getG()) / ln2 + 2)));
			bScale.setSelection((int)(100 * (Math.log(rgbStageOperation.getB()) / ln2 + 2)));
		}
	}


	public RGBStageOperation getRgbStageOperation() 
	{
		return rgbStageOperation;
	}



	public void setRgbStageOperation(RGBStageOperation rgbStageOperation)
	{
		this.rgbStageOperation = rgbStageOperation;
		rgbStageOperation.addOnPropertyChangedListener(rgbPropertyChangedListener);
		updateFromStageOperation();
	}

}
