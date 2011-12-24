package com.bigfatbrowncat.cateye.tests;
import org.junit.Test;

import com.bigfatbrowncat.cateye.core.raw.ExtractedDescription;
import com.bigfatbrowncat.cateye.core.raw.RawLoaderNative;
import com.sun.jna.Pointer;


public class RawLoaderNativeTests {

	@Test
	public void test() {
		Pointer result = RawLoaderNative.INSTANCE.ExtractedDescription_Create();
		RawLoaderNative.INSTANCE.ExtractedDescription_LoadFromFile("C:\\Photos to compare\\Part 3\\IMG_1848.CR2", result);		
		ExtractedDescription ed = new ExtractedDescription(result);
		
		System.out.println(ed.aperture);
		System.out.println(ed.artist);
		System.out.println(ed);
		System.out.println(ed.camera_model);
		System.out.println(ed.data_size);
		System.out.println(ed.desc);
		System.out.println(ed.flip);
		System.out.println(ed.focal_len);
		System.out.println(ed.iso_speed);
		System.out.println(ed.shot_order);
		System.out.println(ed.shutter);
		System.out.println(ed.timestamp);
		System.out.println(ed.is_jpeg);
		
		RawLoaderNative.INSTANCE.ExtractedDescription_Destroy(result);
	}

}
