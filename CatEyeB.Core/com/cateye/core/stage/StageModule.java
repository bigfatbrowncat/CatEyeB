package com.cateye.core.stage;

import com.google.inject.AbstractModule;

public class StageModule extends AbstractModule
{
	@Override
	protected void configure()
	{
		bind(IStageOperationProcessorsProvider.class).to(
				StageOperationProcessorsProvider.class).asEagerSingleton();
		bind(StageFactory.class);
	}
}
