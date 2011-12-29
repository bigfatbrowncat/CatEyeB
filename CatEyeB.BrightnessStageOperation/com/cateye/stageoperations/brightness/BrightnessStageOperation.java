package com.cateye.stageoperations.brightness;

import com.cateye.core.ArgumentOutOfRangeException;
import com.cateye.core.stage.StageOperation;

public class BrightnessStageOperation extends StageOperation
{
	private double brightness;
	
	public double getBrightness()
	{
		return brightness;
	}
	
	public void setBrightness(double brightness)
	{
		if (brightness < 0.01d || brightness > 100d)
		{
			throw new ArgumentOutOfRangeException("brightness");
		}
		
		if ((brightness - this.brightness) >= Double.MIN_NORMAL)
		{
			this.brightness = brightness;
			fireOnPropertyChanged("brightness", brightness);
		}
	}
}
