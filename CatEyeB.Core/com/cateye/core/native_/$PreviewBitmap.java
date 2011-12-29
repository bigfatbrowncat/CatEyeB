package com.cateye.core.native_;

import com.cateye.core.IThumbnailBitmap;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class $PreviewBitmap extends BaseStructure implements IThumbnailBitmap,
		Structure.ByReference
{
	public int width;
	public int height;
	
	public Pointer r;
	public Pointer g;
	public Pointer b;
	
	public $PreviewBitmap()
	{
		super();
	}
	
	public $PreviewBitmap(Pointer p)
	{
		super(p);
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
		PreviewBitmap_Free(this);
	}
	
	@Override
	public IThumbnailBitmap clone()
	{
		$PreviewBitmap result = new $PreviewBitmap();
		PreviewBitmap_Copy(this, result);
		
		return result;
	}

	public static native void PreviewBitmap_Init($PreviewBitmap bmp, int width, int height);
	public static native void PreviewBitmap_Copy($PreviewBitmap src, $PreviewBitmap res);
	public static native void PreviewBitmap_Free($PreviewBitmap fb);
	
	static
	{
		Native.register(LibraryLoader.load("bitmaps.dll"));
	}
}
