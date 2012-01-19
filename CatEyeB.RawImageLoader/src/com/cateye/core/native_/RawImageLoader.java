package com.cateye.core.native_;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashSet;
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
	
	private Hashtable<Image, String> imageFileNames = new Hashtable<Image, String>();
	
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
	
	protected void raiseProgress(float progress)
	{
		for (IProgressListener listener : progressListeners)
			listener.invoke(this, progress);
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
	private static native PreciseBitmap loadPreciseBitmapFromFile(String filename);	

	/**
	 * Loads the description from bitmap file
	 * @param filename The file name
	 */
	private static native ImageDescription loadImageDescriptionFromFile(String filename);	
	
/*	protected void checkLoadingResult(int resultCode)
	{
		switch (resultCode)
		{
			case EXTRACTING_RESULT_OK:
				return;
			case EXTRACTING_RESULT_UNSUFFICIENT_MEMORY:
				throw new OutOfMemoryError();
			case EXTRACTING_RESULT_DATA_ERROR:
				throw new CorruptedImageException();
			case EXTRACTING_RESULT_IO_ERROR:
				throw new BadFileException();
			case EXTRACTING_RESULT_CANCELLED_BY_CALLBACK:
				throw new LoadingCancelledException();
			case EXTRACTING_RESULT_BAD_CROP:
				throw new BadCropException();
			case EXTRACTING_RESULT_UNSUPPORTED_FORMAT:
				throw new UnsupportedFormatException();
			default:
				throw new UnknownResultException(resultCode);
		}
	}
	
	//# Native
	static final int EXTRACTING_RESULT_OK = 0;
	static final int EXTRACTING_RESULT_UNSUFFICIENT_MEMORY = 1;
	static final int EXTRACTING_RESULT_DATA_ERROR = 2;
	static final int EXTRACTING_RESULT_IO_ERROR = 3;
	static final int EXTRACTING_RESULT_CANCELLED_BY_CALLBACK = 4;
	static final int EXTRACTING_RESULT_BAD_CROP = 5;
	static final int EXTRACTING_RESULT_UNSUPPORTED_FORMAT = 6;
	static final int EXTRACTING_RESULT_UNKNOWN = 100;
	
	static native int LoadFromFile(String fileName, boolean divideBy2, PreciseBitmap res, ExtractingProgressReporter progressReporter);
	static native int LoadDescriptionFromFile(String filename, RawImageDescription res);
	static native void FreeDescription(RawImageDescription description); // TODO: Do we need it?
*/	
	static
	{
		LibraryLoader.attach("Raw.CatEyeImageLoader");
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
