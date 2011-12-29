package com.cateye.core.native_;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class $BitmapsLibrary implements Library {
	public static native $PreciseBitmap PreciseBitmap_Create(int width, int height);
	public static native $PreciseBitmap PreciseBitmap_Copy($PreciseBitmap src);
	public static native void PreciseBitmap_Destroy($PreciseBitmap fb);
	
	public static native $PreviewBitmap PreviewBitmap_Create(int width, int height);
	public static native $PreviewBitmap PreviewBitmap_Copy($PreviewBitmap src);
	public static native void PreviewBitmap_Destroy($PreviewBitmap fb);
	
	static {
        Native.register(BimapsLibraryProvider.getBitmapsLibrary());
    }
}
