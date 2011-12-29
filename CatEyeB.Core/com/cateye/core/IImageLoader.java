package com.cateye.core;

public interface IImageLoader
{
	void addOnProgressListener(IOnProgressListener listener);
	
	void removeOnProgressListener(IOnProgressListener listener);
	
	void addOnImageLoadedListener(IOnImageLoadedListener listener);
	
	void removeOnImageLoadedListener(IOnImageLoadedListener listener);
	
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