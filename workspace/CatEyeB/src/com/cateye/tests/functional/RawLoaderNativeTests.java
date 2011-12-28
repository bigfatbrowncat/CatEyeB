package com.cateye.tests.functional;
import junit.framework.Assert;

import org.junit.Test;

import com.cateye.core.IImageLoader;
import com.cateye.core.Image;
import com.cateye.core.ImageDescription;
import com.cateye.core.imageloader.ImageLoaderModule;
import com.cateye.tests.utils.DateAssert;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class RawLoaderNativeTests {
	public Injector injector;
	
	public RawLoaderNativeTests() {
		injector = Guice.createInjector(new ImageLoaderModule());
	}
	
	@Test
	public void test_load_description_from_raw_file() {
		IImageLoader loader = injector.getInstance(IImageLoader.class);
		
		ImageDescription description = loader.loadDescription("..\\data\\test\\IMG_5196.CR2");
		Assert.assertEquals(2f, description.getAperture());
		Assert.assertEquals("", description.getArtist());
		Assert.assertEquals("Canon", description.getCameraMaker());
		Assert.assertEquals("EOS 550D", description.getCameraModel());
		Assert.assertEquals("", description.getDescription());
		Assert.assertEquals(5, description.getFlip());
		Assert.assertEquals(50f, description.getFocalLength());
		Assert.assertEquals(800f, description.getIsoSpeed());
		Assert.assertEquals(0, description.getShotOrder());
		Assert.assertEquals(0.012048522f, description.getShutter(), 0.00001f);
		DateAssert.assertEquals("10/2/11 7:02:18 PM MSK", description.getTimestamp());
		
		description.dispose();
	}
	
	@Test
	public void test_load_image_from_raw_file() {
		IImageLoader loader = injector.getInstance(IImageLoader.class);
		
		Image image = loader.load("..\\data\\test\\IMG_5196.CR2");
		ImageDescription description = image.getDescription();
		Assert.assertEquals(2f, description.getAperture());
		Assert.assertEquals("", description.getArtist());
		Assert.assertEquals("Canon", description.getCameraMaker());
		Assert.assertEquals("EOS 550D", description.getCameraModel());
		Assert.assertEquals("", description.getDescription());
		Assert.assertEquals(5, description.getFlip());
		Assert.assertEquals(50f, description.getFocalLength());
		Assert.assertEquals(800f, description.getIsoSpeed());
		Assert.assertEquals(0, description.getShotOrder());
		Assert.assertEquals(0.012048522f, description.getShutter(), 0.00001f);
		DateAssert.assertEquals("10/2/11 7:02:18 PM MSK", description.getTimestamp());
		
		image.dispose();
	}
}
