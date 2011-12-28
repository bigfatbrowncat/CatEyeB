package com.cateye.core.imageloader;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class $PreciousBitmap extends BaseStructure implements Structure.ByValue, Library {
	public int width;
	public int height;
	
	public Pointer r;
	public Pointer g;
	public Pointer b;
	
	public static native $PreciousBitmap PreciousBitmap_Create(int width, int height);
	public static native $PreciousBitmap PreciousBitmap_Copy($PreciousBitmap src);
	public static native void PreciousBitmap_Destroy($PreciousBitmap fb);
	
	static {
        Native.register(LibraryProvider.getBitmapsLibrary());
    }
}