package com.cateye.core.jni;

import java.io.File;

import com.cateye.core.IImageSaver;
import com.cateye.core.Image;
import com.cateye.core.jni.LibraryLoader;
import com.cateye.core.jni.PreciseBitmap;

class PpmImageSaver implements IImageSaver
{
	@Override
	public void save(String fileName, Image image)
	{
		fileName = new File(fileName).getAbsolutePath();
		PreciseBitmap bitmap = (PreciseBitmap) image.getBitmap();
		SaveImage(fileName, bitmap/*, 3*/);
	}
	
	static native void SaveImage(String fileName, PreciseBitmap bitmap/*, double limit_a*/);
	
	static
	{
		LibraryLoader.attach("Ppm.CatEyeImageSaver");
	}
}
