package com.cateye.core.stage;

import com.google.inject.AbstractModule;

public class StageModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(IStageOperationProcessorsFactory.class).to(StageOperationProcessorsFactory.class).asEagerSingleton();
		bind(StageFactory.class);
	}
}
