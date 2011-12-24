package com.bigfatbrowncat.cateye.core.raw;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class ExtractedDescription extends Structure
{
	public ExtractedDescription(Pointer p)
	{
		super();
		useMemory(p);
		read();
	}
	
	public Pointer data;
	public int data_size;	// in bytes
	public Boolean is_jpeg;

	public float       iso_speed;
	public float       shutter;
	public float       aperture;
	public float       focal_len;
	public int      timestamp;
	public int    shot_order;
	public Pointer   gpsdata;
	public String       desc;
	public String       artist;
	public String       camera_maker;
	public String       camera_model;
	public int         flip;
	public Pointer libraw_image;
}