package com.cateye.stageoperations.compressor;

import com.cateye.core.ArgumentOutOfRangeException;
import com.cateye.core.stage.StageOperation;

public class CompressorStageOperation extends StageOperation 
{
	private double curve = 0.5;
	private double noiseGate = 0.1;
	private double pressure = 1;
	private double contrast = 0.85;
	
	public double getCurve()
	{
		return curve;
	}
	
	public void setCurve(double value)
	{
		if (value < 0d || value > 1d)
		{
			throw new ArgumentOutOfRangeException("curve");
		}
		
		if (Math.abs(value - this.curve) >= Double.MIN_NORMAL)
		{
			this.curve = value;
			fireOnPropertyChanged("curve", value);
		}
	}

	public double getNoiseGate()
	{
		return noiseGate;
	}
	
	public void setNoiseGate(double value)
	{
		if (value < 0.0d || value > 1d)
		{
			throw new ArgumentOutOfRangeException("noiseGate");
		}
		
		if (Math.abs(value - this.noiseGate) >= Double.MIN_NORMAL)
		{
			this.noiseGate = value;
			fireOnPropertyChanged("noiseGate", value);
		}
	}
	
	public double getPressure()
	{
		return pressure;
	}
	
	public void setPressure(double value)
	{
		if (value < 0.0d || value > 10d)
		{
			throw new ArgumentOutOfRangeException("pressure");
		}
		
		if (Math.abs(value - this.pressure) >= Double.MIN_NORMAL)
		{
			this.pressure = value;
			fireOnPropertyChanged("pressure", value);
		}
	}
	
	public double getContrast()
	{
		return contrast;
	}
	
	public void setContrast(double value)
	{
		if (value < 0.00d || value > 0.95d)
		{
			throw new ArgumentOutOfRangeException("contrast");
		}
		
		if (Math.abs(value - this.contrast) >= Double.MIN_NORMAL)
		{
			this.contrast = value;
			fireOnPropertyChanged("contrast", value);
		}
	}
}
