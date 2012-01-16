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
import com.cateye.core.native_.ImageLoaderModule;
import com.cateye.core.native_.ImageSaverModule;
import com.cateye.tests.utils.DateAssert;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;

public class LoadingAndSavingTests
{
	private Injector injector;


	/*
	
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
			public void invoke(Object sender, float progress)
			{
				System.out.println(progress);
				progressInvoked = true;
			}
		});
		
		Image image = loader.load("..\\data\\test\\IMG_5697.CR2");
		
		try
		{
			ImageDescription description = image.getDescription();
			assertImageDescription(description);
			
			Assert.assertEquals(1733, image.getWidth());
			Assert.assertEquals(2601, image.getHeight());
			
			imageLoaded = true;
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		finally
		{
			image.dispose();
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
		
		Image image = loader.load("..\\data\\test\\IMG_5697.CR2");
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
		loader.load(UUID.randomUUID().toString());
	}*/
}
