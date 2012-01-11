package com.cateye.core.native_;

import java.util.ArrayList;
import java.util.List;

import com.cateye.core.IImageLoadedListener;
import com.cateye.core.IImageLoader;
import com.cateye.core.IProgressListener;
import com.cateye.core.Image;

class RawImageLoader implements IImageLoader
{
	private final List<IProgressListener> progressListeners = new ArrayList<IProgressListener>();
	private final List<IImageLoadedListener> imageLoadedListeners = new ArrayList<IImageLoadedListener>();
	private final ExtractingProgressReporter progressReporter = new ExtractingProgressReporter(this);
	
	@Override
	public void addProgressListener(IProgressListener listener)
	{
		progressListeners.add(listener);
	}
	
	@Override
	public void removeProgressListener(IProgressListener listener)
	{
		progressListeners.remove(listener);
	}
	
	@Override
	public void addImageLoadedListener(IImageLoadedListener listener)
	{
		imageLoadedListeners.add(listener);
	}
	
	@Override
	public void removeImageLoadedListener(IImageLoadedListener listener)
	{
		imageLoadedListeners.remove(listener);
	}
	
	protected void raiseProgress(float progress)
	{
		for (IProgressListener listener : progressListeners)
			listener.invoke(this, progress);
	}
	
	protected void raiseImageLoaded(Image image)
	{
		for (IImageLoadedListener listener : imageLoadedListeners)
			listener.invoke(this, image);
	}
	
	@Override
	public Boolean canLoad(String fileName)
	{
		return true; // TODO: implement checking if loading is available
	}
	
	/**
	 * Returns a path to the file
	 */
	protected String getPathToFile(String fileName)
	{
		return new java.io.File(fileName).getAbsolutePath();
	}
	
	@Override
	public void load(String fileName)
	{
		// load description
		RawImageDescription description = loadDescription(fileName);
		
		// load bitmap
		PreciseBitmap bitmap = new PreciseBitmap(); 
		LoadFromFile(getPathToFile(fileName), true, bitmap, progressReporter);
		
		// raise an event
		raiseImageLoaded(new Image(description, bitmap));
	}
	
	@Override
	public RawImageDescription loadDescription(String fileName)
	{
		RawImageDescription description = new RawImageDescription();
		LoadDescriptionFromFile(getPathToFile(fileName), description);
		
		return description;
	}
	
	static native int LoadFromFile(String fileName, boolean divideBy2, PreciseBitmap res, ExtractingProgressReporter progressReporter);
	static native int LoadDescriptionFromFile(String filename, RawImageDescription res);
	static native void FreeDescription(RawImageDescription description); // TODO: Do we need it?
	
	static
	{
		LibraryLoader.attach("raw.CatEyeLoader");
	}
	
	/**
	 * Implementation of native extracting progress reporter
	 */
	static class ExtractingProgressReporter
	{
		private final RawImageLoader loader;
		
		public ExtractingProgressReporter(RawImageLoader loader)
		{
			this.loader = loader;
		}
		
		public boolean invoke(float progress)
		{
			loader.raiseProgress(progress);
			return true;
		}
	}
}
