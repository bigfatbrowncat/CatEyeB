package com.cateye.core.native_;

import com.cateye.core.IPreciseBitmap;
import com.cateye.core.InvalidDataException;
import com.cateye.core.NativeHeapAllocationException;

class PreciseBitmap implements IPreciseBitmap
{
	/**
	 * Width of bitmap. 
	 * Never change this form Java code.
	 */
	int width;
	
	/**
	 * Height of bitmap.
	 * Never change this form Java code.
	 */
	int height;
	
	/**
	 * Pointer to the red channel in memory.
	 * Never change this form Java code.
	 */
	long r;
	
	/**
	 * Pointer to the green channel in memory.
	 * Never change this form Java code.
	 */
	long g;
	
	/**
	 * Pointer to the blue channel in memory.
	 * Never change this form Java code.
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
	public native void alloc(int width, int height) throws NativeHeapAllocationException;

	@Override
	public native void free() throws InvalidDataException;
	
	@Override
	public native IPreciseBitmap clone() throws NativeHeapAllocationException, InvalidDataException;

	@Override
	protected void finalize() throws Throwable {
		free();
		super.finalize();
	}
	
	static
	{
		LibraryLoader.attach("CatEyeB.Core.Native");
	}
}