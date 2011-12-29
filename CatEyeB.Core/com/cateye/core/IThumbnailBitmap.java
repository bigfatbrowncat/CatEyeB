package com.cateye.core;

public interface IThumbnailBitmap
{
	/**
	 * @return the width
	 */
	int getWidth();
	
	/**
	 * @return the height
	 */
	int getHeight();
	
	IThumbnailBitmap clone();
	
	void dispose();
}
