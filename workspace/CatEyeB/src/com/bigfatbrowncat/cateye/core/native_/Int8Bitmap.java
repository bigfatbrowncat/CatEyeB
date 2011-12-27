package com.bigfatbrowncat.cateye.core.native_;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class Int8Bitmap extends BaseStructure implements Structure.ByValue, Library {
	public int width;
	public int height;
	
	public Pointer r;
	public Pointer g;
	public Pointer b;
	
	public static native Int8Bitmap Int8Bitmap_Create(int width, int height);
	public static native Int8Bitmap Int8Bitmap_Copy(Int8Bitmap src);
	public static native void Int8Bitmap_Destroy(Int8Bitmap fb);
	
	static {
        Native.register(LibraryProvider.getBitmapsLibrary());
    }
}
