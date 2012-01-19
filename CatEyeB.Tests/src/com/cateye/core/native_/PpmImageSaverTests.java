package com.cateye.core.native_;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.UUID;

import org.junit.Test;

import com.cateye.core.IImageSaver;
import com.cateye.core.IPreciseBitmap;
import com.cateye.core.Image;

public class PpmImageSaverTests
{
	@Test
	public void save_blank_image_to_file()
	{
		IPreciseBitmap pbmp = new PreciseBitmap();
		pbmp.alloc(100, 100);
		
		Image blankImage = new Image(null, pbmp);
		
		File file = new File("..\\data\\test\\" + UUID.randomUUID().toString());
		String fileName = file.getAbsolutePath();
		
		IImageSaver saver = new PpmImageSaver();
		saver.save(fileName, blankImage);

		assertTrue("Image is not saved", file.exists());
		
		pbmp.free();
	}
}
