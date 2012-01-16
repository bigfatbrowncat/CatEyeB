package com.cateye.core.stage;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.cateye.core.IImageLoader;
import com.cateye.core.IPreciseBitmap;
import com.cateye.core.IProgressListener;
import com.cateye.core.Image;

public class StageTests
{
	@SuppressWarnings("unchecked")
	@Test
	public void test_process_some_stage_operation()
	{
		IPreciseBitmap bitmap = mock(IPreciseBitmap.class);
		Image image = new Image(null, bitmap);
		when(bitmap.clone()).thenReturn(bitmap);
		
		IImageLoader imageLoader = mock(IImageLoader.class);
		when(imageLoader.load("test")).thenReturn(image);
		
		StageOperation operation = mock(StageOperation.class);
		IStageOperationProcessorsProvider processorsProvider = mock(IStageOperationProcessorsProvider.class);
		IStageOperationProcessor<StageOperation> processor = mock(IStageOperationProcessor.class);
		
		IStage stage = new Stage(processorsProvider, imageLoader, null);
		when(processorsProvider.create(operation)).thenReturn(processor);
		
		stage.loadImage("test");
		stage.addStageOperation(operation);
		stage.processImage();
		
		verify(processor).process(eq(operation), eq(image.getBitmap()), any(IProgressListener.class));
		
		stage.removeStageOperation(operation);
	}
	
}
