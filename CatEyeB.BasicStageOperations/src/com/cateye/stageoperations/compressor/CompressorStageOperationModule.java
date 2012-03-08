package com.cateye.stageoperations.compressor;

import com.cateye.core.stage.StageOperationModule;

public class CompressorStageOperationModule extends StageOperationModule 
{
	@Override
	protected void configure() 
	{
		bindProcessor(CompressorStageOperation.class, 
				CompressorStageOperationProcessor.class);
	}

}
