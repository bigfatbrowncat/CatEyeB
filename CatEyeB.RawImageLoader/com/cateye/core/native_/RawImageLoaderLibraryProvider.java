package com.cateye.core.native_;

import com.sun.jna.NativeLibrary;

class RawImageLoaderLibraryProvider {
	public static NativeLibrary getRawLoaderLibrary() {
		return NativeLibrary.getInstance(new java.io.File("..\\target\\Release\\bin\\raw.CatEyeLoader").getAbsolutePath());
	}
}
