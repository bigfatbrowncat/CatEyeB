package com.cateye.core.native_;

import com.cateye.core.IImageSaver;
import com.cateye.core.Image;

class PpmImageSaver implements IImageSaver {
	@Override
	public void save(String fileName, Image image) {
		RawPreciseBitmap bitmap = (RawPreciseBitmap)image.getBitmap();
		$PpmSaverLibrary.SaveImage(fileName, bitmap.getNativeBitmap());
	}
}
