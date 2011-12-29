package com.cateye.core.native_;

import com.sun.jna.Native;

public class $RawImage
{
	public static native int ExtractedRawImage_LoadFromFile(String fileName,
			boolean divideBy2, $PreciseBitmap res, $IExtractingProgressReporter progressReporter);
	
	static
	{
		Native.register(LibraryLoader.load("raw.CatEyeLoader"));
	}
}
