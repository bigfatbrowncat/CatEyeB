package com.cateye.core.native_;

import java.io.File;

import com.cateye.core.IImageSaver;
import com.cateye.core.Image;

class PpmImageSaver implements IImageSaver
{
	@Override
	public void save(String fileName, Image image)
	{
		$PreciseBitmap bitmap = ($PreciseBitmap) image.getBitmap();
		fileName = new File(fileName).getAbsolutePath();
		$PpmSaverLibrary.SaveImage(fileName, bitmap, 3);
	}
}
