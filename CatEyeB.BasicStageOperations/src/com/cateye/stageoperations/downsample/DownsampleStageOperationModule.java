package com.cateye.stageoperations.downsample;

import com.cateye.core.stage.StageOperationModule;

public class DownsampleStageOperationModule extends StageOperationModule 
{
	@Override
	protected void configure() 
	{
		bindProcessor(DownsampleStageOperation.class, 
				DownsampleStageOperationProcessor.class);
	}

}
