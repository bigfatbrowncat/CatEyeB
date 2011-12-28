package com.cateye.core.imageloader;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class $PreviewBitmap extends BaseStructure implements Structure.ByValue, Library {
	public int width;
	public int height;
	
	public Pointer r;
	public Pointer g;
	public Pointer b;
	
	public static native $PreviewBitmap PreviewBitmap_Create(int width, int height);
	public static native $PreviewBitmap PreviewBitmap_Copy($PreviewBitmap src);
	public static native void PreviewBitmap_Destroy($PreviewBitmap fb);
	
	static {
        Native.register(LibraryProvider.getBitmapsLibrary());
    }
}
