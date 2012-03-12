package com.cateye.stageoperations.downsample;

import com.cateye.core.ArgumentOutOfRangeException;
import com.cateye.core.stage.StageOperation;

public class DownsampleStageOperation extends StageOperation 
{
	private int rate;
	
	public double getRate()
	{
		return rate;
	}
	
	public void setRate(int value)
	{
		if (value < 2 || value > 10)
		{
			throw new ArgumentOutOfRangeException("rate");
		}
		
		if (value != this.rate)
		{
			this.rate = value;
			fireOnPropertyChanged("rate", value);
		}
	}

}
