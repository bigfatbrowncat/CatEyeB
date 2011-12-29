package com.cateye.core.native_;

import com.sun.jna.Callback;

public interface $IExtractingProgressReporter extends Callback
{
	public boolean invoke(float progress);
}
