package com.cateye.stageoperations.limiter;

import com.cateye.core.stage.StageOperationModule;
import com.cateye.stageoperations.brightness.BrightnessStageOperation;

public class LimiterStageOperationModule extends StageOperationModule
{
	@Override
	protected void configure()
	{
		bindProcessor(LimiterStageOperation.class,
				LimiterStageOperationProcessor.class);
	}
}
