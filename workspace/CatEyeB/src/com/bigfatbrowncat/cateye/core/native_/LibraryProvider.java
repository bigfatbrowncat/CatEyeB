package com.bigfatbrowncat.cateye.core.native_;

import com.sun.jna.NativeLibrary;

class LibraryProvider {
	public static NativeLibrary getBitmapsLibrary() {
		return NativeLibrary.getInstance(new java.io.File("..\\bitmaps\\bin\\Debug\\bitmaps.dll").getAbsolutePath());
	}
	
	public static NativeLibrary getSsrlLibrary() {
		return NativeLibrary.getInstance(new java.io.File("..\\ssrl\\bin\\Debug\\ssrl.dll").getAbsolutePath());
	}
}
