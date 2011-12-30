package com.cateye.tests.functional;

import org.junit.Test;

import com.cateye.core.IOnProgressListener;
import com.cateye.core.native_.ImageLoaderModule;
import com.cateye.core.native_.ImageSaverModule;
import com.cateye.core.stage.Stage;
import com.cateye.core.stage.StageFactory;
import com.cateye.core.stage.StageModule;
import com.cateye.stageoperations.brightness.BrightnessStageOperation;
import com.cateye.stageoperations.brightness.BrightnessStageOperationModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;

public class StageTests
{
	public Injector injector;
	
	public StageTests()
	{
		injector = Guice.createInjector(Modules.combine(
				new ImageLoaderModule(), new ImageSaverModule(),
				new StageModule(), new BrightnessStageOperationModule()));
	}
	
	@Test
	public void test_process_brightness_operation()
	{
		Stage stage = injector.getInstance(StageFactory.class).create();
		BrightnessStageOperation operation = new BrightnessStageOperation();
		operation.setBrightness(30d);
		
		stage.addOnProgressListener(new IOnProgressListener()
		{
			@Override
			public void invoke(Object sender, float progress)
			{
				System.out.println(progress);
			}
		});
		
		stage.addStageOperation(operation);
		stage.loadImage("..\\data\\test\\IMG_5196.CR2");
		stage.processImage();
		stage.saveImage("..\\data\\test\\IMG_5196.processed.ppm");
	}
	
}
