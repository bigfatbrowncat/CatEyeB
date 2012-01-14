package com.cateye.core.stage;

public interface IStageOperationProcessorsProvider
{
	/**
	 * Returns a new instance of the appropriate processor
	 */
	<T extends StageOperation> IStageOperationProcessor<T> create(T operation);
}