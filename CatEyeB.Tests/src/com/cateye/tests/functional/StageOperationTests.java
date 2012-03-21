package com.cateye.tests.functional;

import org.junit.Test;

import com.cateye.core.jni.ImageLoaderModule;
import com.cateye.core.jni.ImageSaverModule;
import com.cateye.core.stage.StageModule;
import com.cateye.stageoperations.hsb.HSBStageOperationModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;

public class StageOperationTests
{
	public Injector injector;
	
	public StageOperationTests()
	{
		/*
		injector = Guice.createInjector(Modules.combine(
				new ImageLoaderModule(), new ImageSaverModule(),
				new StageModule(), new BrightnessStageOperationModule()));*/
	}
	
	@Test
	public void test_process_brightness_operation()
	{
		/*IStage stage = injector.getInstance(StageFactory.class).create();
		BrightnessStageOperation operation = new BrightnessStageOperation();
		operation.setBrightness(30d);
		
		stage.addOnProgressListener(new IProgressListener()
		{
			@Override
			public void invoke(Object sender, float progress)
			{
				System.out.println(progress);
			}
		});
		
		stage.addStageOperation(operation);
		stage.loadImage("..\\data\\test\\IMG_5697.CR2");
		stage.processImage();
		stage.saveImage("..\\data\\test\\IMG_5697.processed.ppm");*/
	}
}
