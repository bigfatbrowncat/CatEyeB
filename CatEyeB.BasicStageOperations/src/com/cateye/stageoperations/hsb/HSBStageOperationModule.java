package com.cateye.stageoperations.hsb;

import com.cateye.core.stage.StageOperationModule;
import com.cateye.stageoperations.hsb.HSBStageOperation;
import com.cateye.stageoperations.hsb.HSBStageOperationProcessor;

public class HSBStageOperationModule extends StageOperationModule
{
	@Override
	protected void configure()
	{
		bindProcessor(HSBStageOperation.class,
				HSBStageOperationProcessor.class);
	}
}
