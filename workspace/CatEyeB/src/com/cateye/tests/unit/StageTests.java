package com.cateye.tests.unit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.cateye.core.imageloader.ImageLoaderModule;
import com.cateye.core.stage.IStageOperationProcessorsFactory;
import com.cateye.core.stage.Stage;
import com.cateye.core.stage.StageModule;
import com.cateye.core.stage.StageOperation;
import com.cateye.core.stage.StageOperationProcessor;
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

	@SuppressWarnings("unchecked")
	@Test
	public void test_process_some_stage_operation() {
		StageOperation operation = mock(StageOperation.class);
		IStageOperationProcessorsFactory processorsFactory = mock(IStageOperationProcessorsFactory.class);
		StageOperationProcessor<StageOperation> processor = mock(StageOperationProcessor.class);
		Stage stage = new Stage(processorsFactory);
		
		when(processorsFactory.create(operation)).thenReturn(processor);
		
		stage.addStageOperation(operation);
		stage.processImage();
		
		verify(processor).process(operation);
		
		stage.removeStageOperation(operation);
	}

}
