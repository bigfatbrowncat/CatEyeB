package com.cateye.stageoperations.rgb;

import com.cateye.core.stage.StageOperationModule;

public class RGBStageOperationModule extends StageOperationModule
{
	@Override
	protected void configure()
	{
		bindProcessor(RGBStageOperation.class,
				RGBStageOperationProcessor.class);
	}
}
