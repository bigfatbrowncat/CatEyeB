package com.cateye.core.stage;

import com.google.inject.Inject;

public class StageFactory {
	private final IStageOperationProcessorsFactory processorsFactory;

	@Inject
	public StageFactory(IStageOperationProcessorsFactory processorsFactory) {
		this.processorsFactory = processorsFactory;
	}
	
	public Stage create() {
		return new Stage(processorsFactory);
	}
}
