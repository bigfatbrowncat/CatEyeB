package com.cateye.stageoperations.limiter;

import com.cateye.core.ArgumentOutOfRangeException;
import com.cateye.core.stage.StageOperation;

public class LimiterStageOperation extends StageOperation
{
	private double power = 0.1;
	
	public double getPower()
	{
		return power;
	}
	
	public void setPower(double value)
	{
		if (power < 0.1d || power > 2d)
		{
			throw new ArgumentOutOfRangeException("power should be in the range from 0.1 to 2");
		}
		
		if ((power - this.power) >= Double.MIN_NORMAL)
		{
			this.power = value;
			fireOnPropertyChanged("power", power);
		}
	}
}
