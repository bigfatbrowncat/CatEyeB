package com.cateye.stageoperations.brightness;

import com.cateye.core.ArgumentOutOfRangeException;
import com.cateye.core.stage.StageOperation;

public class BrightnessStageOperation extends StageOperation {
	private float brightness;

	public float getBrightness() {
		return brightness;
	}

	public void setBrightness(float brightness) {
		if (brightness < 0.01f || brightness > 100f) {
			throw new ArgumentOutOfRangeException("brightness");
		}
		
		if ((brightness - this.brightness) >= Float.MIN_NORMAL) {
			this.brightness = brightness;
			fireOnPropertyChanged("brightness", brightness);
		}
	}
}
