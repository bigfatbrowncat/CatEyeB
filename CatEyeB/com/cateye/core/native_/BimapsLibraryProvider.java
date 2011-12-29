package com.cateye.core.native_;

import com.sun.jna.NativeLibrary;

class BimapsLibraryProvider {
	public static NativeLibrary getBitmapsLibrary() {
		return NativeLibrary.getInstance(new java.io.File("..\\target\\Release\\bin\\bitmaps.dll").getAbsolutePath());
	}
}
