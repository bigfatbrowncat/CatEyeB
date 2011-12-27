package com.bigfatbrowncat.cateye.core.native_;
import java.util.Date;

import com.sun.jna.Callback;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class RawImageDescriptionNative extends BaseStructure
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
	
	//	DllDef ExtractedDescription* ExtractedDescription_Create();
	public static native Pointer ExtractedDescription_Create();

	//	DllDef int ExtractedDescription_LoadFromFile(char* filename, ExtractedDescription* res);
	public static native int ExtractedDescription_LoadFromFile(String filename, Pointer res);

	//	DllDef void ExtractedDescription_Destroy(ExtractedDescription* description);
	public static native void ExtractedDescription_Destroy(Pointer description);

	static {
        Native.register(LibraryProvider.getSsrlLibrary());
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

