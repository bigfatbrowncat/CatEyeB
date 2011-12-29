package com.cateye.stageoperations.brightness;

import com.cateye.core.stage.StageOperationModule;

public class BrightnessStageOperationModule extends StageOperationModule {
	@Override
	protected void configure() {
		bindProcessor(BrightnessStageOperation.class, BrightnessStageOperationProcessor.class);
	}
}
