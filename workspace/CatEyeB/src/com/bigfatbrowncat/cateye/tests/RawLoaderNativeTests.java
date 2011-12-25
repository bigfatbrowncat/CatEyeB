package com.bigfatbrowncat.cateye.tests;
import junit.framework.Assert;

import org.junit.Test;

import com.bigfatbrowncat.cateye.core.IImageLoader;
import com.bigfatbrowncat.cateye.core.ImageDescription;
import com.bigfatbrowncat.cateye.core.raw.RawModule;
import com.google.inject.Guice;
import com.google.inject.Injector;


public class RawLoaderNativeTests {

	@Test
	public void test() {
		Injector injector = Guice.createInjector(new RawModule());
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
		Assert.assertEquals("Sun Oct 02 19:02:18 MSD 2011", description.getTimestamp().toString());
		
		description.dispose();
	}
}
