package com.cateye.stageoperations.hsb.ui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;

import com.cateye.core.IPropertyChangedListener;
import com.cateye.stageoperations.hsb.HSBStageOperation;

public class HSBStageOperationWidget extends Composite
{
	private final double ln2 = Math.log(2);
	private final double ln5 = Math.log(5);
	private final double ln10 = Math.log(10);
	
	private HSBStageOperation hsbStageOperation;
	private Scale brightnessScale, hueScale, saturationScale, saturationCompressionPowerScale;
	private boolean changingProperty = false;
	
	private final SelectionListener hsbScaleSelectionListener = new SelectionListener()
	{
		
		@Override
		public void widgetSelected(SelectionEvent arg0)
		{
			if (!changingProperty)
			{
				try
				{
					changingProperty = true;

					if (hsbStageOperation != null)
					{
						if (arg0.widget == brightnessScale)
						{
							double value = Math.pow(10, ((double)((Scale)arg0.widget).getSelection()) / 100 - 2);
							hsbStageOperation.setBrightness(value);
						}
						if (arg0.widget == hueScale)
						{
							double value = ((double)((Scale)arg0.widget).getSelection()) / 100 - 0.5;
							hsbStageOperation.setHue(value);
						}
						if (arg0.widget == saturationScale)
						{
							double value = Math.pow(5, ((double)((Scale)arg0.widget).getSelection()) / 100 - 2);
							hsbStageOperation.setSaturation(value);
						}
						if (arg0.widget == saturationCompressionPowerScale)
						{
							double value = Math.pow(2, ((double)((Scale)arg0.widget).getSelection()) / 100 - 2);
							hsbStageOperation.setSaturationCompressionPower(value);
						}
					}
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
	private IPropertyChangedListener hsbPropertyChangedListener = new IPropertyChangedListener() 
	{
		
		@Override
		public void invoke(Object sender, String propertyName, Object newValue) 
		{
			if (!changingProperty)
			{
				try
				{
					changingProperty = true;
					if (propertyName.equals("brightness"))
						brightnessScale.setSelection((int)(100 * (Math.log(hsbStageOperation.getBrightness()) / ln10 + 2)));
					if (propertyName.equals("hue"))
						hueScale.setSelection((int)(100 * (Math.log(hsbStageOperation.getHue()) + 0.5)));
					if (propertyName.equals("saturation"))
						saturationScale.setSelection((int)(100 * (Math.log(hsbStageOperation.getSaturation()) / ln5 + 2)));
					if (propertyName.equals("saturationCompressionPower"))
						saturationCompressionPowerScale.setSelection((int)(100 * (Math.log(hsbStageOperation.getSaturationCompressionPower()) / ln2 + 2)));
				}
				finally
				{
					changingProperty = false;
				}
			}
			
		}
	};
			
	public HSBStageOperationWidget(Composite parent, int style) 
	{
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		Label lblBrightness = new Label(this, SWT.NONE);
		lblBrightness.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblBrightness.setText("Brightness:");
		
		brightnessScale = new Scale(this, SWT.NONE);
		brightnessScale.setMaximum(400);
		brightnessScale.setSelection(200);
		brightnessScale.setTouchEnabled(true);
		brightnessScale.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		brightnessScale.addSelectionListener(hsbScaleSelectionListener);
		
		Label lblHue = new Label(this, SWT.NONE);
		lblHue.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblHue.setText("Hue:");
		
		hueScale = new Scale(this, SWT.NONE);
		hueScale.setMaximum(100);
		hueScale.setSelection(50);
		hueScale.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		hueScale.addSelectionListener(hsbScaleSelectionListener);
		
		Label lblSaturation = new Label(this, SWT.NONE);
		lblSaturation.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSaturation.setText("Saturation:");
		
		saturationScale = new Scale(this, SWT.NONE);
		saturationScale.setMaximum(400);
		saturationScale.setSelection(200);
		saturationScale.setTouchEnabled(true);
		saturationScale.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		saturationScale.addSelectionListener(hsbScaleSelectionListener);

		Label lblSaturationCompressionPowerScale = new Label(this, SWT.NONE);
		lblSaturationCompressionPowerScale.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSaturationCompressionPowerScale.setText("Saturation comp.:");
		
		saturationCompressionPowerScale = new Scale(this, SWT.NONE);
		saturationCompressionPowerScale.setMaximum(400);
		saturationCompressionPowerScale.setSelection(200);
		saturationCompressionPowerScale.setTouchEnabled(true);
		saturationCompressionPowerScale.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		saturationCompressionPowerScale.addSelectionListener(hsbScaleSelectionListener);
	}

	protected void updateFromStageOperation()
	{
		if (hsbStageOperation != null)
		{		
			brightnessScale.setSelection((int)(100 * (Math.log(hsbStageOperation.getBrightness()) / ln10 + 2)));
			hueScale.setSelection((int)(100 * (hsbStageOperation.getBrightness() + 0.5)));
			saturationScale.setSelection((int)(100 * (Math.log(hsbStageOperation.getSaturation()) / ln5 + 2)));
			saturationCompressionPowerScale.setSelection((int)(100 * (Math.log(hsbStageOperation.getSaturation()) / ln2 + 2)));
		}
	}


	public HSBStageOperation getHsbStageOperation() 
	{
		return hsbStageOperation;
	}



	public void setHsbStageOperation(HSBStageOperation hsbStageOperation)
	{
		this.hsbStageOperation = hsbStageOperation;
		hsbStageOperation.addOnPropertyChangedListener(hsbPropertyChangedListener);
		updateFromStageOperation();
	}

}
