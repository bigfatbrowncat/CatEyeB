package com.cateye.core.native_;

import junit.framework.Assert;

import org.junit.Test;

import com.cateye.core.ImageDescription;
import com.cateye.tests.utils.DateAssert;

public class RawImageLoaderTests 
{
	@Test
	public void test_load_description_from_raw_file()
	{
		RawImageDescription desc = new RawImageDescription();
		desc.loadFromFile("../../data/test/IMG_5697.CR2");
//		desc.loadFromFile("../../data/test/fake.CR2");
		System.out.printf("[Java] Flip: %1$d\n", desc.getFlip());
		System.out.printf("[Java] ISO speed: %1$f\n", desc.getIsoSpeed());
		System.out.printf("[Java] Shutter: %1$f\n", desc.getShutter());
		System.out.printf("[Java] Aperture: %1$f\n", desc.getAperture());
		System.out.printf("[Java] Focal length: %1$f\n", desc.getFocalLength());
		System.out.printf("[Java] Shot order: %1$d\n", desc.getShotOrder());
		
		//ImageDescription description = loader.loadDescription("..\\data\\test\\IMG_5697.CR2");
		assertImageDescription(desc);
		
		//description.dispose();
	}

	protected void assertImageDescription(ImageDescription description)
	{
		Assert.assertEquals(3.22f, description.getAperture(), 0.001);
		Assert.assertEquals("", description.getArtist());
		Assert.assertEquals("Canon", description.getCameraMaker());
		Assert.assertEquals("EOS 550D", description.getCameraModel());
		Assert.assertEquals("", description.getDescription());
		Assert.assertEquals(5, description.getFlip());
		Assert.assertEquals(50f, description.getFocalLength());
		Assert.assertEquals(1600f, description.getIsoSpeed());
		Assert.assertEquals(0, description.getShotOrder());
		Assert.assertEquals(0.03125, description.getShutter(), 0.00001f);
		DateAssert.assertEquals("10/19/11 6:51:56 PM MSK", description.getTimestamp());
	}
}
