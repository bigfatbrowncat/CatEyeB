package com.cateye.core;

public interface IPreciseBitmap
{
	/**
	 * @return the width
	 */
	int getWidth();
	
	/**
	 * @return the height
	 */
	int getHeight();
	
	/**
	 * Creates a clone of the bitmap
	 */
	IPreciseBitmap clone();
	
	/**
	 * Releases native resources
	 */
	void dispose();
}
