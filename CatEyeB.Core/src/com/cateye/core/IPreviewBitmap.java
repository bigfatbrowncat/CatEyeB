package com.cateye.core;

public interface IPreviewBitmap
{
	/**
	 * @return the width
	 */
	int getWidth();
	
	/**
	 * @return the height
	 */
	int getHeight();
	
	IPreviewBitmap clone();
	
	void dispose();
}
