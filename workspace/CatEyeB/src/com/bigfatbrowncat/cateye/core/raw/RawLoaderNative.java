package com.bigfatbrowncat.cateye.core.raw;


import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface RawLoaderNative extends Library {
	RawLoaderNative INSTANCE = (RawLoaderNative)Native.loadLibrary("I:\\Documents and Settings\\IL\\CatEyeB\\CatEyeB\\workspace\\ssrl\\bin\\Debug\\ssrl.dll", RawLoaderNative.class);

//	DllDef ExtractedDescription* ExtractedDescription_Create();
//	DllDef int ExtractedDescription_LoadFromFile(char* filename, ExtractedDescription* res);
//	DllDef void ExtractedDescription_Destroy(ExtractedDescription* description);

	Pointer ExtractedDescription_Create();
	int ExtractedDescription_LoadFromFile(String filename, Pointer res);
	void ExtractedDescription_Destroy(Pointer description);
}