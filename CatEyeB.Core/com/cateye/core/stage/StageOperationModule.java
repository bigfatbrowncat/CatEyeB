package com.cateye.core.stage;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

public abstract class StageOperationModule extends AbstractModule
{
	/**
	 * Binds a specified processor to a type of operation
	 * 
	 * @param stageOperationClass
	 *            class of stage operation
	 * @param processorClass
	 *            processor which will process operation of passed type
	 */
	protected <T extends StageOperation> void bindProcessor(
			Class<T> stageOperationClass,
			Class<? extends IStageOperationProcessor<T>> processorClass)
	{
		bind(IStageOperationProcessor.class).annotatedWith(
				Names.named(stageOperationClass.getName())).to(processorClass);
	}
	
	/**
	 * Returns an instance of processor from the IoC container
	 */
	@SuppressWarnings("unchecked")
	static <T extends StageOperation> IStageOperationProcessor<T> resolveProcessor(Injector injector, T operation)
	{
		return (IStageOperationProcessor<T>) injector.getInstance(Key.get(
				IStageOperationProcessor.class,
				Names.named(operation.getClass().getName())));
	}
}
