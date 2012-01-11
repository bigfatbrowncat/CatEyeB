package com.cateye.core;

public interface IImageLoader
{
	void addProgressListener(IProgressListener listener);
	
	void removeProgressListener(IProgressListener listener);
	
	void addImageLoadedListener(IImageLoadedListener listener);
	
	void removeImageLoadedListener(IImageLoadedListener listener);
	
	/**
	 * Should return true if current loader can load a specified file
	 */
	Boolean canLoad(String fileName);
	
	/**
	 * Loads the image by a specified file name
	 */
	void load(String fileName);
	
	/**
	 * Loads the description of an image
	 */
	ImageDescription loadDescription(String fileName);
}