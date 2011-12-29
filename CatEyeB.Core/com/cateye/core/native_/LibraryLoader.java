package com.cateye.core.native_;

import com.sun.jna.NativeLibrary;

public class LibraryLoader
{
	public static NativeLibrary load(String libraryName)
	{
		return NativeLibrary.getInstance(new java.io.File(
				"..\\target\\Release\\bin\\" + libraryName).getAbsolutePath());
	}
}
