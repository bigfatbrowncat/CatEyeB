package com.cateye.core.native_;

import java.util.Date;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class $RawImageDescription extends BaseStructure implements Structure.ByReference
{
	public $PreviewBitmap thumbnail = new $PreviewBitmap();
	
	public float iso_speed;
	public float shutter;
	public float aperture;
	public float focal_len;
	public NativeTimestamp timestamp;
	public int shot_order;
	public Pointer gpsdata;
	public String desc;
	public String artist;
	public String camera_maker;
	public String camera_model;
	public int flip;
	
	public $RawImageDescription()
	{
		super();
	}
	
	public $RawImageDescription(Pointer p)
	{
		super(p);
	}
	
	// DllDef int ExtractedDescription_LoadFromFile(char* filename, ExtractedDescription* res);
	public static native int ExtractedDescription_LoadFromFile(String filename, $RawImageDescription res);
	
	// DllDef void ExtractedDescription_Destroy(ExtractedDescription*
	// description);
	public static native void ExtractedDescription_Free($RawImageDescription description);
	
	static
	{
		Native.register(LibraryLoader.load("raw.CatEyeLoader"));
	}
	
	public static class NativeTimestamp extends Structure implements Structure.ByValue
	{
		public int value;
		
		public NativeTimestamp() {}
		
		public NativeTimestamp(int value)
		{
			this.value = value;
		}
		
		public Date getDate()
		{
			return new Date(value * 1000L);
		}
	}
}
