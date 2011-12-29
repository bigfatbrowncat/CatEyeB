package com.cateye.tests.functional;

import org.junit.Test;

import com.cateye.core.native_.ImageLoaderModule;
import com.cateye.core.stage.Stage;
import com.cateye.core.stage.StageFactory;
import com.cateye.core.stage.StageModule;
import com.cateye.stageoperations.brightness.BrightnessStageOperation;
import com.cateye.stageoperations.brightness.BrightnessStageOperationModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;

public class StageTests {
	public Injector injector;

	public StageTests() {
		injector = Guice.createInjector(Modules.combine(
				new ImageLoaderModule(), new StageModule(),
				new BrightnessStageOperationModule()));
	}

	@Test
	public void test_process_brightness_operation() {
		Stage stage = injector.getInstance(StageFactory.class).create();
		BrightnessStageOperation operation = new BrightnessStageOperation();
		stage.addStageOperation(operation);
		stage.processImage();
		stage.removeStageOperation(operation);
	}

}
