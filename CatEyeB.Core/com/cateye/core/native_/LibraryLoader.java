package com.cateye.core.native_;

import com.sun.jna.NativeLibrary;

/**
 * Helper for library loading. It loads libraries from a directory specified in configuration.
 */
public class LibraryLoader
{
	public static NativeLibrary load(String libraryName)
	{
		return NativeLibrary.getInstance(new java.io.File(
				"..\\target\\Release\\bin\\" + libraryName).getAbsolutePath());
	}
}
