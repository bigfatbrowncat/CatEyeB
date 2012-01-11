package com.cateye.core.native_;

import com.cateye.core.IPreciseBitmap;

class PreciseBitmap implements IPreciseBitmap
{
	/**
	 * Width of bitmap
	 */
	int width;
	
	/**
	 * Height of bitmap
	 */
	int height;
	
	/**
	 * Pointer to the red channel in memory
	 */
	long r;
	
	/**
	 * Pointer to the green channel in memory
	 */
	long g;
	
	/**
	 * Pointer to the blue channel in memory
	 */
	long b;
	
	@Override
	public int getWidth()
	{
		return width;
	}
	
	@Override
	public int getHeight()
	{
		return height;
	}
	
	@Override
	public void dispose()
	{
		Free(this);
	}
	
	@Override
	public IPreciseBitmap clone()
	{
		PreciseBitmap result = new PreciseBitmap();
		Copy(this, result);
		
		return result;
	}
	
	static final int BITMAP_RESULT_OK = 0;
	static final int BITMAP_RESULT_OUT_OF_MEMORY = 1;
	static final int BITMAP_RESULT_INCORRECT_DATA = 2;
	
	static native int Init(PreciseBitmap bmp, int width, int height);
	static native int Copy(PreciseBitmap src, PreciseBitmap res);
	static native int Free(PreciseBitmap fb);
	
	static
	{
		LibraryLoader.attach("bitmaps.dll");
	}
}