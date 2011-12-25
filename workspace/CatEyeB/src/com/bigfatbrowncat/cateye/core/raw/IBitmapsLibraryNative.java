package com.bigfatbrowncat.cateye.core.raw;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public interface IBitmapsLibraryNative extends Library{
	// FloatBitmap management
	//DllDef FloatBitmap FloatBitmap_Create(int width, int height);
	//DllDef FloatBitmap FloatBitmap_Copy(FloatBitmap src);
	//DllDef void FloatBitmap_Destroy(FloatBitmap fb);

	// Int16Bitmap management
	//DllDef Int16Bitmap Int16Bitmap_Create(int width, int height);
	//DllDef Int16Bitmap Int16Bitmap_Copy(Int16Bitmap src);
	//DllDef void Int16Bitmap_Destroy(Int16Bitmap fb);

	// Int8Bitmap management
	public Int8Bitmap Int8Bitmap_Create(int width, int height);
	public Int8Bitmap Int8Bitmap_Copy(Int8Bitmap src);
	public void Int8Bitmap_Destroy(Int8Bitmap fb);

	// Converters
	//DllDef FloatBitmap FloatBitmap_FromInt16Bitmap(FloatBitmap src);
	//DllDef Int16Bitmap Int16Bitmap_FromFloatBitmap(Int16Bitmap src);
	//DllDef FloatBitmap FloatBitmap_FromInt8Bitmap(FloatBitmap src);
	//DllDef Int16Bitmap Int8Bitmap_FromFloatBitmap(Int16Bitmap src);
	
	public static class Int8Bitmap extends BaseStructure implements Structure.ByValue {
		public int width;
		public int height;
		
		public Pointer r;
		public Pointer g;
		public Pointer b;
	}
}
