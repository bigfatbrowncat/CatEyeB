package com.cateye.core.stage;

import com.google.inject.Inject;
import com.google.inject.Injector;

class StageOperationProcessorsProvider implements IStageOperationProcessorsProvider
{
	private Injector injector;
	
	@Inject
	public StageOperationProcessorsProvider(Injector injector)
	{
		this.injector = injector;
	}
	
	/**
	 * Returns a new instance of the appropriate processor
	 */
	@Override
	public <T extends StageOperation> IStageOperationProcessor<T> create(T operation)
	{
		return StageOperationModule.resolveProcessor(injector, operation);
	}
}
