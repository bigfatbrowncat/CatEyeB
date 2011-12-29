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
	
	public static native void PreciseBitmap_Init($PreciseBitmap bmp, int width, int height);
	public static native void PreciseBitmap_Copy($PreciseBitmap src, $PreciseBitmap res);
	public static native void PreciseBitmap_Free($PreciseBitmap fb);
	
	static
	{
		Native.register(LibraryLoader.load("bitmaps.dll"));
	}
}