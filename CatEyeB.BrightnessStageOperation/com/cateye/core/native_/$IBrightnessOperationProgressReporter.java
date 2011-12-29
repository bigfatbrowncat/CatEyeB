package com.cateye.core.native_;

import com.sun.jna.Callback;

public interface $IBrightnessOperationProgressReporter extends Callback
{
	// typedef bool BrightnessOperationProgressReporter(float progress);
	public boolean invoke(float progress);
}
