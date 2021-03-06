package com.cateye.stageoperations.limiter;

import com.cateye.core.ArgumentOutOfRangeException;
import com.cateye.core.stage.StageOperation;

public class LimiterStageOperation extends StageOperation
{
	private double power = 5;
	
	public double getPower()
	{
		return power;
	}
	
	public void setPower(double value)
	{
		if (power < 0.1d || power > 100d)
		{
			throw new ArgumentOutOfRangeException("power should be in the range from 0.1 to 100");
		}
		
		if (Math.abs(power - this.power) >= Double.MIN_NORMAL)
		{
			this.power = value;
			fireOnPropertyChanged("power", power);
		}
	}
}
