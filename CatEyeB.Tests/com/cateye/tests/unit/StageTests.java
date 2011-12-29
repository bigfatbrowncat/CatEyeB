package com.cateye.tests.unit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.cateye.core.IImageLoader;
import com.cateye.core.stage.IStageOperationProcessorsFactory;
import com.cateye.core.stage.Stage;
import com.cateye.core.stage.StageOperation;
import com.cateye.core.stage.StageOperationProcessor;

public class StageTests
{
	@SuppressWarnings("unchecked")
	@Test
	public void test_process_some_stage_operation()
	{
		StageOperation operation = mock(StageOperation.class);
		IStageOperationProcessorsFactory processorsFactory = mock(IStageOperationProcessorsFactory.class);
		StageOperationProcessor<StageOperation> processor = mock(StageOperationProcessor.class);
		IImageLoader imageLoader = mock(IImageLoader.class);
		Stage stage = new Stage(processorsFactory, imageLoader, null);
		
		when(processorsFactory.create(operation)).thenReturn(processor);
		
		stage.addStageOperation(operation);
		stage.processImage();
		
		verify(processor).process(operation, null);
		
		stage.removeStageOperation(operation);
	}
	
}
