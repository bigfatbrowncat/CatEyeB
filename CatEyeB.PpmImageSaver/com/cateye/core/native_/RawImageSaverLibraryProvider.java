package com.cateye.core.native_;

import com.sun.jna.NativeLibrary;

class RawImageSaverLibraryProvider {
	public static NativeLibrary getPpmSaverLibrary() {
		return NativeLibrary.getInstance(new java.io.File("..\\target\\Release\\bin\\ppm.CatEyeSaver").getAbsolutePath());
	}

}
