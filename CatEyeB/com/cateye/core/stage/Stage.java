package com.cateye.core.stage;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

public class Stage {
	private final IStageOperationProcessorsFactory operationProcessorsFactory;
	private final List<StageOperation> stageOperations = new ArrayList<StageOperation>();
	
	@Inject
	public Stage(IStageOperationProcessorsFactory operationProcessorsFactory) {
		this.operationProcessorsFactory = operationProcessorsFactory;
	}
	
	public void addStageOperation(StageOperation operation) {
		stageOperations.add(operation);
	}
	
	public void removeStageOperation(StageOperation operation) {
		stageOperations.remove(operation);
	}
	
	public void processImage() {
		for (StageOperation stageOperation : stageOperations) {
			StageOperationProcessor<StageOperation> processor = operationProcessorsFactory.create(stageOperation);
			processor.process(stageOperation);
		}
	}
}
