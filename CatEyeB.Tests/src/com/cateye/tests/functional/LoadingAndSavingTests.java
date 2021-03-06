package com.cateye.tests.functional;

import java.io.File;
import java.util.UUID;

import junit.framework.Assert;

import org.junit.Test;

import com.cateye.core.BadFileException;
import com.cateye.core.IImageLoader;
import com.cateye.core.IImageSaver;
import com.cateye.core.IProgressListener;
import com.cateye.core.Image;
import com.cateye.core.ImageDescription;
import com.cateye.core.jni.ImageLoaderModule;
import com.cateye.core.jni.ImageSaverModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;

public class LoadingAndSavingTests
{
	private Injector injector;


	public LoadingAndSavingTests()
	{
		injector = Guice.createInjector(Modules.combine(new ImageLoaderModule(), new ImageSaverModule()));
	}
	
	private volatile boolean progressInvoked;
	private volatile boolean imageLoaded;
	
	@Test
	public void test_load_image_from_raw_file() throws InterruptedException
	{
		IImageLoader loader = injector.getInstance(IImageLoader.class);
		progressInvoked = false;
		imageLoaded = false;
		
		loader.addProgressListener(new IProgressListener()
		{
			@Override
			public boolean invoke(Object sender, float progress)
			{
				System.out.println(progress);
				progressInvoked = true;
				return true;
			}
		});
		
		Image image = loader.loadImageFromFile("..\\..\\data\\test\\IMG_5697.CR2");
		
		try
		{
			Assert.assertEquals(1733, image.getBitmap().getWidth());
			Assert.assertEquals(2601, image.getBitmap().getHeight());
			
			ImageDescription description = image.getDescription();
			assertImageDescription(description);
			
			imageLoaded = true;
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		finally
		{
			image.free();
		}
		
		Assert.assertTrue("Image should be loaded", imageLoaded);
		Assert.assertTrue("Progress callback should be invoked", progressInvoked);
	}
	
	private boolean imageSaved;
	
	@Test
	public void test_load_and_save() throws InterruptedException
	{
		IImageLoader loader = injector.getInstance(IImageLoader.class);
		IImageSaver saver = injector.getInstance(IImageSaver.class);
		imageSaved = false;
		
		Image image = loader.loadImageFromFile("..\\data\\test\\IMG_5697.CR2");
		File file = new File("..\\data\\test\\" + UUID.randomUUID().toString());
		String fileName = file.getAbsolutePath();
		
		saver.save(fileName, image);
		imageSaved = file.exists();
		file.delete();
		
		Assert.assertTrue("Image should be saved", imageSaved);
	}
	
	@Test(expected = BadFileException.class)
	public void test_load_non_existing_image() throws InterruptedException
	{
		IImageLoader loader = injector.getInstance(IImageLoader.class);
		loader.loadImageFromFile(UUID.randomUUID().toString());
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
		//DateAssert.assertEquals("10/19/11 6:51:56 PM MSK", description.getTimestamp());
	}
}
