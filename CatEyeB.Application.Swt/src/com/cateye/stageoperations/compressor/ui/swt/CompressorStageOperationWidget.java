package com.cateye.stageoperations.compressor.ui.swt;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

import com.cateye.core.IPropertyChangedListener;
import com.cateye.stageoperations.compressor.CompressorStageOperation;

public class CompressorStageOperationWidget extends Composite
{
	private final double ln2 = Math.log(2);
	
	private CompressorStageOperation compressorStageOperation;
	private Scale curveScale, noiseGateScale, pressureScale, contrastScale;
	private boolean changingProperty = false;
	
	private final SelectionListener scaleSelectionListener = new SelectionListener()
	{
		
		@Override
		public void widgetSelected(SelectionEvent arg0)
		{
			if (!changingProperty)
			{
				try
				{
					changingProperty = true;
					double value = (double)(((Scale)arg0.widget).getSelection()) / 100;

					if (compressorStageOperation != null)
					if (arg0.widget == curveScale) compressorStageOperation.setCurve(value);
					if (arg0.widget == noiseGateScale) compressorStageOperation.setNoiseGate(value);
					if (arg0.widget == pressureScale) compressorStageOperation.setPressure(value * 4);
					if (arg0.widget == contrastScale) compressorStageOperation.setContrast(value * 0.95);
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
	private IPropertyChangedListener compressorPropertyChangedListener = new IPropertyChangedListener() 
	{
		
		@Override
		public void invoke(Object sender, String propertyName, Object newValue) 
		{
			if (!changingProperty)
			{
				try
				{
					changingProperty = true;
					if (propertyName.equals("curve"))
						curveScale.setSelection((int)(100 * (compressorStageOperation.getCurve())));
					if (propertyName.equals("noiseGate"))
						noiseGateScale.setSelection((int)(100 * (compressorStageOperation.getNoiseGate())));
					if (propertyName.equals("pressure"))
						pressureScale.setSelection((int)(100 * (compressorStageOperation.getPressure() / 4)));
					if (propertyName.equals("contrast"))
						contrastScale.setSelection((int)(100 * (compressorStageOperation.getContrast() / 0.95)));
				}
				finally
				{
					changingProperty = false;
				}
			}
			
		}
	};
			
	public CompressorStageOperationWidget(Composite parent, int style) 
	{
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		Label lblCurve = new Label(this, SWT.NONE);
		lblCurve.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCurve.setText("Curve:");
		
		curveScale = new Scale(this, SWT.NONE);
		curveScale.setMaximum(100);
		curveScale.setSelection(50);
		curveScale.setTouchEnabled(true);
		curveScale.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		curveScale.addSelectionListener(scaleSelectionListener);
		
		Label lblNoiseGate = new Label(this, SWT.NONE);
		lblNoiseGate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNoiseGate.setText("Noise gate:");
		
		noiseGateScale = new Scale(this, SWT.NONE);
		noiseGateScale.setMaximum(100);
		noiseGateScale.setSelection(50);
		noiseGateScale.setTouchEnabled(true);
		noiseGateScale.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		noiseGateScale.addSelectionListener(scaleSelectionListener);

		Label lblPressure = new Label(this, SWT.NONE);
		lblPressure.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPressure.setText("Pressure:");
		
		pressureScale = new Scale(this, SWT.NONE);
		pressureScale.setMaximum(100);
		pressureScale.setSelection(50);
		pressureScale.setTouchEnabled(true);
		pressureScale.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		pressureScale.addSelectionListener(scaleSelectionListener);

		Label lblContrast = new Label(this, SWT.NONE);
		lblContrast.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblContrast.setText("Contrast:");
		
		contrastScale = new Scale(this, SWT.NONE);
		contrastScale.setMaximum(100);
		contrastScale.setSelection(50);
		contrastScale.setTouchEnabled(true);
		contrastScale.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		contrastScale.addSelectionListener(scaleSelectionListener);
		
		// TODO Auto-generated constructor stub
	}

	protected void updateFromStageOperation()
	{
		if (compressorStageOperation != null)
		{		
			curveScale.setSelection((int)(100 * (compressorStageOperation.getCurve())));
			noiseGateScale.setSelection((int)(100 * (compressorStageOperation.getNoiseGate())));
			pressureScale.setSelection((int)(100 * (compressorStageOperation.getPressure() / 4)));
			contrastScale.setSelection((int)(100 * (compressorStageOperation.getContrast() / 0.95)));
		}
	}


	public CompressorStageOperation getCompressorStageOperation() 
	{
		return compressorStageOperation;
	}



	public void setCompressorStageOperation(CompressorStageOperation compressorStageOperation)
	{
		this.compressorStageOperation = compressorStageOperation;
		compressorStageOperation.addOnPropertyChangedListener(compressorPropertyChangedListener);
		updateFromStageOperation();
	}

}
