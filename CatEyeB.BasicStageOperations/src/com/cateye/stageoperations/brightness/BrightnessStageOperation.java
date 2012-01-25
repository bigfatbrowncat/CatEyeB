package com.cateye.stageoperations.brightness;

import com.cateye.core.ArgumentOutOfRangeException;
import com.cateye.core.stage.StageOperation;

public class BrightnessStageOperation extends StageOperation
{
	private double brightness = 1;
	
	public double getBrightness()
	{
		return brightness;
	}
	
	public void setBrightness(double value)
	{
		if (value < 0.01d || value > 100d)
		{
			throw new ArgumentOutOfRangeException("brightness");
		}
		
		if ((value - this.brightness) >= Double.MIN_NORMAL)
		{
			this.brightness = value;
			fireOnPropertyChanged("brightness", value);
		}
	}
}
