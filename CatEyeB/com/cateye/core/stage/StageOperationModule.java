package com.cateye.core.stage;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public abstract class StageOperationModule extends AbstractModule {
	/**
	 * Binds a processor of stage operation
	 * @param stageOperationClass class of stage operation
	 * @param processorClass processor which will process operation of passed type
	 */
	protected <T extends StageOperation> void bindProcessor(Class<T> stageOperationClass, Class<? extends StageOperationProcessor<T>> processorClass)
    {
            bind(StageOperationProcessor.class).annotatedWith(Names.named(stageOperationClass.getName())).to(processorClass);
    }
}
