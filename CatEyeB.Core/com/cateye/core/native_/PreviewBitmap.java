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
		checkResult(Free(this));
	}
	
	@Override
	public IPreviewBitmap clone()
	{
		PreviewBitmap result = new PreviewBitmap();
		checkResult(Copy(this, result));
		
		return result;
	}
	
	/**
	 * Check result and if it is wrong throw the exception
	 */
	private void checkResult(int resultCode)
	{
		switch (resultCode)
		{
			case BITMAP_RESULT_OUT_OF_MEMORY:
				throw new OutOfMemoryError();
			case BITMAP_RESULT_INCORRECT_DATA:
				throw new IncorrectDataException();
		}
	}
	
	static final int BITMAP_RESULT_OK = 0;
	static final int BITMAP_RESULT_OUT_OF_MEMORY = 1;
	static final int BITMAP_RESULT_INCORRECT_DATA = 2;

	static native int Init(PreviewBitmap bmp, int width, int height);
	static native int Copy(PreviewBitmap src, PreviewBitmap res);
	static native int Free(PreviewBitmap fb);
	
	static
	{
		LibraryLoader.attach("bitmaps.dll");
	}
}
