package com.cateye.core.native_;

import com.cateye.core.stage.StageOperationModule;
import com.cateye.stageoperations.brightness.BrightnessStageOperation;

public class BrightnessStageOperationModule extends StageOperationModule
{
	@Override
	protected void configure()
	{
		bindProcessor(BrightnessStageOperation.class,
				$BrightnessStageOperationProcessor.class);
	}
}
