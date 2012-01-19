package com.cateye.core.stage;

import java.util.ArrayList;
import java.util.List;

import com.cateye.core.IImageLoader;
import com.cateye.core.IImageSaver;
import com.cateye.core.IPreciseBitmap;
import com.cateye.core.IProgressListener;
import com.cateye.core.Image;
import com.google.inject.Inject;

class Stage implements IStage
{
	private final IStageOperationProcessorsProvider operationProcessorsProvider;
	private final IImageLoader imageLoader;
	private final IImageSaver imageSaver;
	
	private final List<StageOperation> stageOperations = new ArrayList<StageOperation>();
	private final List<IProgressListener> progressListeners = new ArrayList<IProgressListener>();
	
	private final IProgressListener imageLoadingProgressListener;
	protected Image image;
	
	protected IPreciseBitmap originalBitmap;
	protected IPreciseBitmap processedBitmap;
	
	@Inject
	public Stage(IStageOperationProcessorsProvider operationProcessorsProvider,
			IImageLoader imageLoader, IImageSaver imageSaver)
	{
		this.operationProcessorsProvider = operationProcessorsProvider;
		this.imageLoader = imageLoader;
		this.imageSaver = imageSaver;
		
		imageLoadingProgressListener = new IProgressListener()
		{
			@Override
			public void invoke(Object sender, float progress)
			{
				invokeOnProgress(progress);
			}
		};
	}
	
	@Override
	public void addOnProgressListener(IProgressListener listener)
	{
		progressListeners.add(listener);
	}
	
	@Override
	public void addStageOperation(StageOperation operation)
	{
		stageOperations.add(operation);
	}
	
	/**
	 * Returns summary efforts of the passed stage operations
	 */
	private int calculateEfforts(List<StageOperation> stageOperations)
	{
		int summaryEfforts = 0;
		
		for (StageOperation stageOperation : stageOperations)
		{
			IStageOperationProcessor<StageOperation> processor = operationProcessorsProvider.create(stageOperation);
			summaryEfforts += processor.calculateEffort();
		}
		
		return summaryEfforts;
	}
	
	@Override
	public void dispose()
	{
		disposeLoadedImage();
	}
	
	protected void disposeLoadedImage()
	{
		if (isImageLoaded())
			this.image.free();
	}
	
	protected void invokeOnProgress(float progress)
	{
		for (IProgressListener listener : progressListeners)
			listener.invoke(this, progress);
	}
	
	/**
	 * Returns true if image is loaded
	 */
	public boolean isImageLoaded()
	{
		return image != null && image.getBitmap() != null;
	}
	
	@Override
	public void loadImage(String fileName)
	{
		disposeLoadedImage();
		imageLoader.addProgressListener(imageLoadingProgressListener);
		
		try
		{
			image = imageLoader.loadImageFromFile(fileName);
			originalBitmap = image.getBitmap();
		}
		catch (Exception e)
		{
			throw new ImageLoadingException(e);
		}
		finally
		{
			imageLoader.removeProgressListener(imageLoadingProgressListener);
		}
	}
	
	@Override
	public void processImage()
	{
		if (!isImageLoaded())
			throw new ImageNotLoadedException();
		
		if (processedBitmap != null)
			processedBitmap.free();
		
		processedBitmap = image.getBitmap().clone();
		int summaryEfforts = calculateEfforts(stageOperations);
		
		for (StageOperation stageOperation : stageOperations)
		{
			IStageOperationProcessor<StageOperation> processor = operationProcessorsProvider.create(stageOperation);
			int processorEffort = processor.calculateEffort();
			
			IProgressListener progressListener = new StageOperationProgressListener(this, processorEffort, summaryEfforts);
			processor.process(stageOperation, processedBitmap, progressListener);
		}
	}
	
	@Override
	public void removeOnProgressListener(IProgressListener listener)
	{
		progressListeners.remove(listener);
	}
	
	@Override
	public void removeStageOperation(StageOperation operation)
	{
		stageOperations.remove(operation);
	}
	
	@Override
	public void saveImage(String fileName)
	{
		throw new RuntimeException();
		//this.image.setBitmap(processedBitmap == null ? originalBitmap : processedBitmap);
		//imageSaver.save(fileName, this.image);
	}
}
