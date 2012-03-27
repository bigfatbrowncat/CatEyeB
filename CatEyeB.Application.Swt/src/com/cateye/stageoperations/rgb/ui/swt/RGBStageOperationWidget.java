package com.cateye.stageoperations.rgb.ui.swt;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.xml.sax.SAXException;

import com.cateye.core.IPropertyChangedListener;
import com.cateye.stageoperations.rgb.RGBStageOperation;
import com.cateye.ui.swt.Scale;

public class RGBStageOperationWidget extends Composite
{
	private final double ln2 = Math.log(2);
	
	private RGBStageOperation rgbStageOperation;
	private Scale rScale, gScale, bScale;
	private boolean changingProperty = false;
	private com.cateye.ui.swt.Scale testScale;
	
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
					double value = Math.pow(2, ((Scale)arg0.widget).getValue() * 4 - 2);

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
						rScale.setValue((Math.log(rgbStageOperation.getR()) / ln2 + 2) / 4);
					if (propertyName.equals("g"))
						gScale.setValue((Math.log(rgbStageOperation.getG()) / ln2 + 2) / 4);
					if (propertyName.equals("b"))
						bScale.setValue((Math.log(rgbStageOperation.getB()) / ln2 + 2) / 4);
				}
				finally
				{
					changingProperty = false;
				}
			}
			
		}
	};
			
	public RGBStageOperationWidget(Composite parent, int style) throws ParserConfigurationException, SAXException, IOException 
	{
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		// Red
		Label lblRed = new Label(this, SWT.NONE);
		lblRed.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblRed.setText("Red:");
		rScale = new Scale(this);
		GridData rGridData = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		rGridData.heightHint = rScale.getNaturalHeight();
		rScale.setLayoutData(rGridData);
		rScale.setBackColor(new Color(rScale.getDisplay(), new RGB(255, 200, 200)));
		
		// Green
		Label lblGreen = new Label(this, SWT.NONE);
		lblGreen.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblGreen.setText("Green:");
		gScale = new Scale(this);
		GridData gGridData = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gGridData.heightHint = gScale.getNaturalHeight();
		gScale.setLayoutData(gGridData);
		gScale.setBackColor(new Color(rScale.getDisplay(), new RGB(200, 255, 200)));

		// Blue
		Label lblBlue = new Label(this, SWT.NONE);
		lblBlue.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblBlue.setText("Blue:");
		bScale = new Scale(this);
		GridData bGridData = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		bGridData.heightHint = bScale.getNaturalHeight();
		bScale.setLayoutData(gGridData);
		bScale.setBackColor(new Color(rScale.getDisplay(), new RGB(200, 200, 255)));

	}

	protected void updateFromStageOperation()
	{
		if (rgbStageOperation != null)
		{		
			rScale.setValue((Math.log(rgbStageOperation.getR()) / ln2 + 2) / 4);
			gScale.setValue((Math.log(rgbStageOperation.getG()) / ln2 + 2) / 4);
			bScale.setValue((Math.log(rgbStageOperation.getB()) / ln2 + 2) / 4);
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
