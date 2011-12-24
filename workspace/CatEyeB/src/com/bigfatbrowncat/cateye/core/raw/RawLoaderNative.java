package com.bigfatbrowncat.cateye.core.raw;


import com.sun.jna.Library;
import com.sun.jna.Native;

public interface RawLoaderNative extends Library {
	RawLoaderNative INSTANCE = (RawLoaderNative)Native.loadLibrary("I:\\Documents and Settings\\IL\\CatEyeB\\CatEyeB\\workspace\\ssrl\\bin\\Debug\\ssrl.dll", RawLoaderNative.class);

	int ExtractDescriptionFromFile(String fileName, ExtractedDescription result);
	void FreeExtractedDescription(ExtractedDescription description);
}