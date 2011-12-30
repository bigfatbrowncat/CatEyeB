package com.cateye.core.native_;

import com.cateye.core.native_.$PreciseBitmap;
import com.sun.jna.Library;
import com.sun.jna.Native;

public class $PpmSaverLibrary implements Library
{
	// PPM_SAVER void SaveImage(char* filename, PreciseBitmap bitmap);
	public static native void SaveImage(String fileName, $PreciseBitmap bitmap, double limit_a);
	
	static
	{
		Native.register(LibraryLoader.load("ppm.CatEyeSaver"));
	}
	
}
