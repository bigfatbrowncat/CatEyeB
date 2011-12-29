package com.cateye.core.stage;

import java.util.ArrayList;
import java.util.List;

import com.cateye.core.IImageLoader;
import com.cateye.core.IImageSaver;
import com.cateye.core.IOnImageLoadedListener;
import com.cateye.core.IOnProgressListener;
import com.cateye.core.Image;
import com.cateye.core.IPreciseBitmap;
import com.google.inject.Inject;

public class Stage
{
	private final IStageOperationProcessorsFactory operationProcessorsFactory;
	private final List<StageOperation> stageOperations = new ArrayList<StageOperation>();
	private final IImageLoader imageLoader;
	private final IImageSaver imageSaver;
	
	private final List<IOnProgressListener> progressListeners = new ArrayList<IOnProgressListener>();

	public void addOnProgressListener(IOnProgressListener listener)
	{
		progressListeners.add(listener);
	}
	
	public void removeOnProgressListener(IOnProgressListener listener)
	{
		progressListeners.remove(listener);
	}
	
	protected void invokeOnProgress(float progress)
	{
		for (IOnProgressListener listener : progressListeners)
			listener.invoke(this, progress);
	}
	
	@Inject
	public Stage(IStageOperationProcessorsFactory operationProcessorsFactory,
			IImageLoader imageLoader, IImageSaver imageSaver)
	{
		this.operationProcessorsFactory = operationProcessorsFactory;
		this.imageLoader = imageLoader;
		this.imageSaver = imageSaver;
	}
	
	public void addStageOperation(StageOperation operation)
	{
		stageOperations.add(operation);
	}
	
	public void removeStageOperation(StageOperation operation)
	{
		stageOperations.remove(operation);
	}
	
	protected Image image;
	protected IPreciseBitmap originalBitmap;
	protected IPreciseBitmap processedBitmap;
	
	protected boolean isImageLoaded()
	{
		return image != null;
	}
	
	public void loadImage(String fileName)
	{
		disposeLoadedImage();
		
		imageLoader.addOnProgressListener(new IOnProgressListener()
		{
			@Override
			public void invoke(Object sender, float progress)
			{
				invokeOnProgress(progress);
			}
		});
		
		imageLoader.addOnImageLoadedListener(new IOnImageLoadedListener()
		{
			@Override
			public void invoke(Object sender, Image img)
			{
				image = img;
				originalBitmap = img.getBitmap();
			}
		});
		
		try
		{
			imageLoader.load(fileName);
		} catch (Exception e)
		{
			throw new ImageLoadingException(e);
		}
	}
	
	public void saveImage(String fileName)
	{
		this.image.setBitmap(processedBitmap == null ? originalBitmap : processedBitmap);
		imageSaver.save(fileName, this.image);
	}
	
	public void processImage()
	{
		if (!isImageLoaded())
			throw new ImageNotLoadedException();
		
		if (processedBitmap != null)
			processedBitmap.dispose();
		
		processedBitmap = image.getBitmap().clone();
		
		for (StageOperation stageOperation : stageOperations)
		{
			StageOperationProcessor<StageOperation> processor = operationProcessorsFactory
					.create(stageOperation);
			
			// TODO: we should create unified method of working with on progress listeners
			processor.addOnProgressListener(new IOnProgressListener()
			{
				@Override
				public void invoke(Object sender, float progress)
				{
					invokeOnProgress(progress);
				}
			});
			processor.addOnImageProcessedListener(stageOperationProcessedListener);
			processor.process(stageOperation, processedBitmap);
		}
	}
	
	public void dispose()
	{
		disposeLoadedImage();
	}
	
	protected void disposeLoadedImage()
	{
		if (isImageLoaded())
			this.image.dispose();
	}
	
	protected StageOperationProcessedListener stageOperationProcessedListener = new StageOperationProcessedListener(this);
	
	/**
	 * Private class for the operation processed listener
	 */
	static class StageOperationProcessedListener implements IOnImageProcessedListener
	{
		private final Stage stage;

		public StageOperationProcessedListener(Stage stage)
		{
			this.stage = stage;
		}
		
		@Override
		public void invoke(Object sender, int code, IPreciseBitmap bitmap)
		{
			stage.processedBitmap = bitmap;
		}
	}
}
