package com.bigfatbrowncat.cateye.core.raw;


import com.sun.jna.Library;
import com.sun.jna.Native;

public interface Ssrl extends Library {
	Ssrl INSTANCE = (Ssrl)Native.loadLibrary("f:\\Projects\\CatEyeB\\workspace\\bin\\Debug\\ssrl.dll", Ssrl.class);
	
	public int ExtractDescriptionFromFile(String fileName, ExtractedDescription result);
	public ExtractedDescription Test(String fileName);
}