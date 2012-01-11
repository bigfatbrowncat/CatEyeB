package com.cateye.core.native_;

import com.cateye.core.IPreviewBitmap;

class PreviewBitmap implements IPreviewBitmap
{
	/**
	 * Width of the image
	 */
	int width;
	
	/**
	 * Height of the image
	 */
	int height;
	
	/**
	 * Pointer to the red channel
	 */
	long r;
	
	/**
	 * Pointer to the green channel
	 */
	long g;
	
	/**
	 * Pointer to the blue channel
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
	public IPreviewBitmap clone()
	{
		PreviewBitmap result = new PreviewBitmap();
		Copy(this, result);
		
		return result;
	}

	static native void Init(PreviewBitmap bmp, int width, int height);
	static native void Copy(PreviewBitmap src, PreviewBitmap res);
	static native void Free(PreviewBitmap fb);
	
	static
	{
		LibraryLoader.attach("bitmaps.dll");
	}
}
