package com.cateye.core.stage;

import java.util.ArrayList;
import java.util.List;

import com.cateye.core.IImageLoader;
import com.cateye.core.IImageSaver;
import com.cateye.core.IOnImageLoadedListener;
import com.cateye.core.Image;
import com.cateye.core.PreciseBitmap;
import com.google.inject.Inject;

public class Stage {
	private final IStageOperationProcessorsFactory operationProcessorsFactory;
	private final List<StageOperation> stageOperations = new ArrayList<StageOperation>();
	private final IImageLoader imageLoader;
	private final IImageSaver imageSaver;

	@Inject
	public Stage(IStageOperationProcessorsFactory operationProcessorsFactory,
			IImageLoader imageLoader, IImageSaver imageSaver) {
		this.operationProcessorsFactory = operationProcessorsFactory;
		this.imageLoader = imageLoader;
		this.imageSaver = imageSaver;
	}

	public void addStageOperation(StageOperation operation) {
		stageOperations.add(operation);
	}

	public void removeStageOperation(StageOperation operation) {
		stageOperations.remove(operation);
	}

	protected Image image;
	protected PreciseBitmap originalBitmap;
	protected PreciseBitmap processedBitmap;

	protected boolean isImageLoaded() {
		return image != null;
	}

	public void loadImage(String fileName) {
		imageLoader.addOnImageLoadedListener(new IOnImageLoadedListener() {
			@Override
			public void invoke(Object sender, Image img) {
				image = img;
				originalBitmap = img.getBitmap();
			}
		});

		try {
			imageLoader.load(fileName);
		} catch (Exception e) {
			throw new ImageLoadingException(e);
		}
	}

	public void saveImage(String fileName) {
		this.image.setBitmap(processedBitmap);
		imageSaver.save(fileName, this.image);
	}

	public void processImage() {
		if (!isImageLoaded())
			throw new ImageNotLoadedException();

		if (processedBitmap != null) {
			processedBitmap.dispose();
		}

		processedBitmap = image.getBitmap().clone();

		for (StageOperation stageOperation : stageOperations) {
			StageOperationProcessor<StageOperation> processor = operationProcessorsFactory
					.create(stageOperation);
			processor.process(stageOperation, processedBitmap);
		}
	}
}
