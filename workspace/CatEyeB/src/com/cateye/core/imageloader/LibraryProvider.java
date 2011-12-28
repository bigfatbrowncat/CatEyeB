package com.cateye.core.imageloader;

import com.sun.jna.NativeLibrary;

class LibraryProvider {
	public static NativeLibrary getBitmapsLibrary() {
		return NativeLibrary.getInstance(new java.io.File("..\\target\\Release\\bin\\bitmaps.dll").getAbsolutePath());
	}
	
	public static NativeLibrary getSsrlLibrary() {
		return NativeLibrary.getInstance(new java.io.File("..\\target\\Release\\bin\\ssrl.dll").getAbsolutePath());
	}
}
