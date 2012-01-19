package com.cateye.core.native_;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.cateye.core.IImageLoader;
import com.cateye.core.IPreciseBitmap;
import com.cateye.core.IProgressListener;
import com.cateye.core.Image;
import com.cateye.core.ImageDescription;
import com.cateye.core.IncorrectImageLoaderRelation;

class RawImageLoader implements IImageLoader
{
	// Progress listeners
	private final List<IProgressListener> progressListeners = new ArrayList<IProgressListener>();
	private final ExtractingProgressReporter progressReporter = new ExtractingProgressReporter(this);
	private boolean divideBy2 = true;
	
	private Hashtable<Image, String> imageFileNames = new Hashtable<Image, String>();
	
	public void setDivideBy2(boolean value)
	{
		divideBy2 = value;
	}
	
	public boolean getDivideBy2()
	{
		return divideBy2;
	}
	
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
	
	protected boolean raiseProgress(float progress)
	{
		boolean res = true;
		for (IProgressListener listener : progressListeners)
		{
			if (!listener.invoke(this, progress)) res = false;
		}
		return res;
	}
	
	//# Methods
	@Override
	public boolean canLoadFromFile(String fileName)
	{
		return true;
	}
	
	@Override
	public Image loadImageFromFile(String fileName)
	{
		Image img = new Image(this);
		imageFileNames.put(img, fileName);
		return img;
	}
	
	@Override
	public IPreciseBitmap loadPreciseBitmapForImage(Image img)
	{
		if (imageFileNames.containsKey(img))
		{
			return loadPreciseBitmapFromFile(imageFileNames.get(img));
		}
		else
		{
			throw new IncorrectImageLoaderRelation(img, this);
		}
	}

	@Override
	public ImageDescription loadDescriptionForImage(Image img)
	{
		if (imageFileNames.containsKey(img))
		{
			return loadImageDescriptionFromFile(imageFileNames.get(img));
		}
		else
		{
			throw new IncorrectImageLoaderRelation(img, this);
		}		
	}

	@Override
	public void forgetImage(Image img)
	{
		if (imageFileNames.containsKey(img))
		{
			imageFileNames.remove(img);
		}
		else
		{
			throw new IncorrectImageLoaderRelation(img, this);
		}				
	}
	
	/**
	 * Loads the bitmap from raw file
	 * @param filename The file name
	 */
	private native PreciseBitmap loadPreciseBitmapFromFile(String filename);	

	/**
	 * Loads the description from bitmap file
	 * @param filename The file name
	 */
	private native ImageDescription loadImageDescriptionFromFile(String filename);	
	

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

	static
	{
		LibraryLoader.attach("Raw.CatEyeImageLoader");
	}
	
}
