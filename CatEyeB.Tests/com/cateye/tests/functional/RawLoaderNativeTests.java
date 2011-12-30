package com.cateye.tests.functional;

import java.awt.List;
import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import junit.framework.Assert;

import org.junit.Test;

import com.cateye.core.IImageLoader;
import com.cateye.core.IImageSaver;
import com.cateye.core.IOnImageLoadedListener;
import com.cateye.core.IOnProgressListener;
import com.cateye.core.Image;
import com.cateye.core.ImageDescription;
import com.cateye.core.native_.$PreciseBitmap;
import com.cateye.core.native_.ImageLoaderModule;
import com.cateye.core.native_.ImageSaverModule;
import com.cateye.tests.utils.DateAssert;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;

public class RawLoaderNativeTests
{
	public Injector injector;
	
	public RawLoaderNativeTests()
	{
		injector = Guice.createInjector(Modules.combine(
				new ImageLoaderModule(), new ImageSaverModule()));
	}
	
	protected void assertImageDescription(ImageDescription description) {
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
		DateAssert.assertEquals("10/19/11 5:51:56 PM MSK",
				description.getTimestamp());
	}
	
	@Test
	public void test_load_description_from_raw_file()
	{
		IImageLoader loader = injector.getInstance(IImageLoader.class);
		
		ImageDescription description = loader
				.loadDescription("..\\data\\test\\IMG_5196.CR2");
		assertImageDescription(description);
		
		description.dispose();
	}
	
	private volatile boolean progressInvoked;
	private volatile boolean imageLoaded;
	
	@Test
	public void test_load_image_from_raw_file() throws InterruptedException
	{
		// final CountDownLatch lock = new CountDownLatch(2);
		IImageLoader loader = injector.getInstance(IImageLoader.class);
		progressInvoked = false;
		imageLoaded = false;
		
		loader.addOnProgressListener(new IOnProgressListener()
		{
			@Override
			public void invoke(Object sender, float progress)
			{
				System.out.println(progress);
				progressInvoked = true;
			}
		});
		
		loader.addOnImageLoadedListener(new IOnImageLoadedListener()
		{
			@Override
			public void invoke(Object sender, Image image)
			{
				try
				{
					ImageDescription description = image.getDescription();
					assertImageDescription(description);
					
					Assert.assertEquals(1733, image.getWidth());
					Assert.assertEquals(2601, image.getHeight());
					
					imageLoaded = true;
				} catch (RuntimeException e)
				{
					throw e;
				} finally
				{
					image.dispose();
				}
			}
		});
		
		loader.load("..\\data\\test\\IMG_5196.CR2");
		
		Assert.assertTrue("Image should be loaded", imageLoaded);
		Assert.assertTrue("Progress callback should be invoked",
				progressInvoked);
	}
	
	private boolean imageSaved;
	
	@Test
	public void test_load_and_save() throws InterruptedException
	{
		final IImageLoader loader = injector.getInstance(IImageLoader.class);
		final IImageSaver saver = injector.getInstance(IImageSaver.class);
		imageSaved = false;
		
		loader.addOnImageLoadedListener(new IOnImageLoadedListener()
		{
			@Override
			public void invoke(Object sender, Image image)
			{
				File file = new File("..\\data\\test\\"
						+ UUID.randomUUID().toString());
				String fileName = file.getAbsolutePath();
				saver.save(fileName, image);
				imageSaved = file.exists();
				//file.delete();
			}
		});
		
		loader.load("..\\data\\test\\IMG_5697.CR2");
		
		Assert.assertTrue("Image should be saved", imageSaved);
	}
	
	@Test
	public void test_load_non_existing_image() throws InterruptedException
	{
		final IImageLoader loader = injector.getInstance(IImageLoader.class);
		
		loader.load(UUID.randomUUID().toString());
	}
	
	@Test
	public void test_precisebitmap_big_leak()
	{
		for (int i = 0; i < 1000; i++)
		{
			$PreciseBitmap pbmp = new $PreciseBitmap();
			$PreciseBitmap.PreciseBitmap_Init(pbmp, 3000, 3000);
			$PreciseBitmap pbmp2 = new $PreciseBitmap();
			$PreciseBitmap.PreciseBitmap_Copy(pbmp, pbmp2);
			$PreciseBitmap.PreciseBitmap_Free(pbmp);
			$PreciseBitmap.PreciseBitmap_Free(pbmp2);
		}
	}
	
	@Test
	public void test_precisebitmap_small_leak()
	{
		try
		{
			final int CYCLES = 10000; 
			for (int i = 0; i < CYCLES; i++)
			{
				$PreciseBitmap pbmp = new $PreciseBitmap();
				$PreciseBitmap.PreciseBitmap_Init(pbmp, 30, 30);
				$PreciseBitmap pbmp2 = new $PreciseBitmap();
				$PreciseBitmap.PreciseBitmap_Copy(pbmp, pbmp2);
				$PreciseBitmap.PreciseBitmap_Free(pbmp);
				$PreciseBitmap.PreciseBitmap_Free(pbmp2);
				if (i % (CYCLES / 100) == 0) System.out.println((i / (CYCLES / 100)) + "%");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Test
	public void test_precisebitmap_outofmemory()
	{
		final int W = 300, H = 300;
		
		ArrayList<$PreciseBitmap> pbmps = new ArrayList<$PreciseBitmap>(); 

		boolean outofmem = false;
		do
		{
			$PreciseBitmap pbmp = new $PreciseBitmap();
			if ($PreciseBitmap.PreciseBitmap_Init(pbmp, W, H) == $PreciseBitmap.BITMAP_RESULT_OUT_OF_MEMORY)
			{
				outofmem = true;
			}
			else
			{
				pbmps.add(pbmp);
			}
		} while (!outofmem);
		System.out.printf("%1$d images %2$dx%3$d allocated before out of memory. Freeing", pbmps.size(), W, H);
		for (int i = 0; i < pbmps.size(); i++)
		{
			$PreciseBitmap.PreciseBitmap_Free(pbmps.get(i));
		}
	}
}
