package com.cateye.core.native_;

import java.util.ArrayList;
import java.util.List;

import com.cateye.core.IImageLoader;
import com.cateye.core.IOnImageLoadedListener;
import com.cateye.core.IOnProgressListener;
import com.cateye.core.Image;

class RawImageLoader implements IImageLoader
{
	private final List<IOnProgressListener> progressListeners = new ArrayList<IOnProgressListener>();
	private final List<IOnImageLoadedListener> imageLoadedListeners = new ArrayList<IOnImageLoadedListener>();
	
	@Override
	public void addOnProgressListener(IOnProgressListener listener)
	{
		progressListeners.add(listener);
	}
	
	@Override
	public void removeOnProgressListener(IOnProgressListener listener)
	{
		progressListeners.remove(listener);
	}
	
	@Override
	public void addOnImageLoadedListener(IOnImageLoadedListener listener)
	{
		imageLoadedListeners.add(listener);
	}
	
	@Override
	public void removeOnImageLoadedListener(IOnImageLoadedListener listener)
	{
		imageLoadedListeners.remove(listener);
	}
	
	protected void invokeOnProgress(float progress)
	{
		for (IOnProgressListener listener : progressListeners)
		{
			listener.invoke(this, progress);
		}
	}
	
	protected void invokeOnImageLoaded(Image image)
	{
		for (IOnImageLoadedListener listener : imageLoadedListeners)
		{
			listener.invoke(this, image);
		}
	}
	
	@Override
	public Boolean canLoad(String fileName)
	{
		return true; // TODO: implement checking if loading is available
	}
	
	@Override
	public void load(String fileName)
	{
		RawImageDescription description = loadDescription(fileName);
		$PreciseBitmap bitmap = new $PreciseBitmap(); 
		$RawImage.ExtractedRawImage_LoadFromFile(fileName, true, bitmap, progressReporter);
		invokeOnImageLoaded(new Image(description, bitmap));
	}
	
	@Override
	public RawImageDescription loadDescription(String fileName)
	{
		String filePath = new java.io.File(fileName).getAbsolutePath();
		
		// load description to java structure
		$RawImageDescription description = new $RawImageDescription();
		$RawImageDescription.ExtractedDescription_LoadFromFile(filePath,
				description);
		
		RawImageDescription result = new RawImageDescription();
		result.loadFromNative(description);
		
		$RawImageDescription.ExtractedDescription_Free(description);
		
		return result;
	}
	
	ExtractingProgressReporter progressReporter = new ExtractingProgressReporter(this);
	
	/**
	 * Implementation of native extracting progress reporter
	 */
	static class ExtractingProgressReporter implements
			$IExtractingProgressReporter
	{
		private final RawImageLoader loader;
		
		public ExtractingProgressReporter(RawImageLoader loader)
		{
			this.loader = loader;
		}
		
		@Override
		public boolean invoke(float progress)
		{
			loader.invokeOnProgress(progress);
			return true;
		}
	}
}
