package com.cateye.core.native_;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class $PreviewBitmap extends BaseStructure implements Structure.ByValue {
	public int width;
	public int height;
	
	public Pointer r;
	public Pointer g;
	public Pointer b;
}
