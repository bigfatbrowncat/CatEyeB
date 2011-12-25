package com.bigfatbrowncat.cateye.core.raw;
import java.util.Date;

import com.bigfatbrowncat.cateye.core.raw.IBitmapsLibraryNative.Int8Bitmap;
import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public interface IRawLoaderNative extends Library {
	//	DllDef ExtractedDescription* ExtractedDescription_Create();
	Pointer ExtractedDescription_Create();

	//	DllDef int ExtractedDescription_LoadFromFile(char* filename, ExtractedDescription* res);
	int ExtractedDescription_LoadFromFile(String filename, Pointer res);

	//	DllDef void ExtractedDescription_Destroy(ExtractedDescription* description);
	void ExtractedDescription_Destroy(Pointer description);
	
	public static class RawImageDescriptionNative extends BaseStructure
	{
		public Int8Bitmap thumbnail;

		public float       iso_speed;
		public float       shutter;
		public float       aperture;
		public float       focal_len;
		public NativeTimestamp      timestamp;
		public int    shot_order;
		public Pointer   gpsdata;
		public String       desc;
		public String       artist;
		public String       camera_maker;
		public String       camera_model;
		public int         flip;
		public Pointer libraw_image;
	}
	
	public static class NativeTimestamp extends Structure {
	    public int value;

	    public NativeTimestamp() {}

	    public NativeTimestamp(int value) {
	        this.value = value;
	    }
	    
	    public Date getDate() {
	    	return new Date(value * 1000L);
	    }
	}
	
	public static interface ExtractingErrorReporter extends Callback {
		boolean invoke(int error);
	}
}