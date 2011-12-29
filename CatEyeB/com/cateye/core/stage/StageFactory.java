package com.cateye.core.stage;

import com.cateye.core.IImageLoader;
import com.cateye.core.IImageSaver;
import com.google.inject.Inject;

public class StageFactory {
	private final IStageOperationProcessorsFactory processorsFactory;
	private final IImageLoader loader;
	private final IImageSaver saver;

	@Inject
	public StageFactory(IStageOperationProcessorsFactory processorsFactory, IImageLoader loader, IImageSaver saver) {
		this.processorsFactory = processorsFactory;
		this.loader = loader;
		this.saver = saver;
	}
	
	public Stage create() {
		return new Stage(processorsFactory, loader, saver);
	}
}
