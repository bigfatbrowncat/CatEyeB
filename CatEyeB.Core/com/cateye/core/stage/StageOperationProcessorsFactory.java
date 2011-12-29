package com.cateye.core.stage;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

class StageOperationProcessorsFactory implements
		IStageOperationProcessorsFactory
{
	private Injector injector;
	
	@Inject
	public StageOperationProcessorsFactory(Injector injector)
	{
		this.injector = injector;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bigfatbrowncat.cateye.core.stage.IStageOperationProcessorsFactory
	 * #create(T)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends StageOperation> StageOperationProcessor<T> create(
			T operation)
	{
		return (StageOperationProcessor<T>) injector.getInstance(Key.get(
				StageOperationProcessor.class,
				Names.named(operation.getClass().getName())));
	}
}
