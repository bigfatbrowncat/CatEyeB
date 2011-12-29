package com.cateye.core.native_;

import com.sun.jna.Native;

public class $RawImage {
	public static native void ExtractedRawImage_LoadFromFile(String fileName, boolean divideBy2, $IExtractingProgressReporter progressReporter, $IExtractingResultReporter resultReporter);
	
	static {
        Native.register(RawImageLoaderLibraryProvider.getRawLoaderLibrary());
	}
}
