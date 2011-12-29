package com.cateye.core.native_;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class $BrightnessStageOperation implements Library
{
	public static native void Process($PreciseBitmap bmp, double brightness,
			$IBrightnessOperationProgressReporter progress_reporter);
	
	static
	{
		Native.register(LibraryLoader.load("brightness.CatEyeOperation"));
	}
}