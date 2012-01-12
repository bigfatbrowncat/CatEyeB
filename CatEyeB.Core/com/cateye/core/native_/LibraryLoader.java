package com.cateye.core.native_;

/**
 * Helper for library loading. It loads libraries from a directory specified in configuration.
 */
public class LibraryLoader
{
	static String getPathToLibrary(String libraryName)
	{
		return new java.io.File("..\\target\\Release\\bin\\" + libraryName).getAbsolutePath();
	}
	
	/**
	 * Loads a library and attach it to the native methods of the current class
	 */
	public static void attach(String libraryName)
	{
		System.load(getPathToLibrary(libraryName));
	}
}
