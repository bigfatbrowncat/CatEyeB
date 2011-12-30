package com.cateye.core.native_;

import com.cateye.core.IPreciseBitmap;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class $PreciseBitmap extends BaseStructure implements IPreciseBitmap,
		Structure.ByReference
{
	public int width;
	public int height;
	
	public Pointer r;
	public Pointer g;
	public Pointer b;
	
	public $PreciseBitmap()
	{
		super();
	}
	
	public $PreciseBitmap(Pointer bitmap)
	{
		super(bitmap);
	}
	
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
		PreciseBitmap_Free(this);
	}
	
	@Override
	public IPreciseBitmap clone()
	{
		$PreciseBitmap result = new $PreciseBitmap();
		PreciseBitmap_Copy(this, result);
		
		return result;
	}
	
	public static final int BITMAP_RESULT_OK = 0;
	public static final int BITMAP_RESULT_OUT_OF_MEMORY = 1;
	public static final int BITMAP_RESULT_INCORRECT_DATA = 2;
	
	public static native int PreciseBitmap_Init($PreciseBitmap bmp, int width, int height);
	public static native int PreciseBitmap_Copy($PreciseBitmap src, $PreciseBitmap res);
	public static native int PreciseBitmap_Free($PreciseBitmap fb);
	
	static
	{
		Native.register(LibraryLoader.load("bitmaps.dll"));
	}
}