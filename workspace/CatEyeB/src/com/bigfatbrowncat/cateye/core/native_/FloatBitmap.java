package com.bigfatbrowncat.cateye.core.native_;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class FloatBitmap extends BaseStructure implements Structure.ByValue, Library {
	public int width;
	public int height;
	
	public Pointer r;
	public Pointer g;
	public Pointer b;
	
	public static native FloatBitmap FloatBitmap_Create(int width, int height);
	public static native FloatBitmap FloatBitmap_Copy(FloatBitmap src);
	public static native void FloatBitmap_Destroy(FloatBitmap fb);
	
	static {
        Native.register(LibraryProvider.getBitmapsLibrary());
    }
}