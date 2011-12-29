package com.cateye.core.stage;

public interface IStageOperationProcessorsFactory {
	public abstract <T extends StageOperation> StageOperationProcessor<T> create(T operation);
}