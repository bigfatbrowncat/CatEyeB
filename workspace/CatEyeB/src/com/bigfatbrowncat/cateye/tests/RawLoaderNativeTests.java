package com.bigfatbrowncat.cateye.tests;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.bigfatbrowncat.cateye.core.raw.ExtractedDescription;
import com.bigfatbrowncat.cateye.core.raw.ExtractingErrorReporter;
import com.bigfatbrowncat.cateye.core.raw.RawLoaderNative;


public class RawLoaderNativeTests {

	@Test
	public void test() {
		ExtractedDescription result = new ExtractedDescription();
		ExtractingErrorReporter callback = new ExtractingErrorReporter() {
			@Override
			public boolean invoke(int error) {
				return true;
			}
		};
		
		RawLoaderNative.INSTANCE.ExtractDescriptionFromFile("C:\\Photos to compare\\Part 3\\IMG_1848.CR2", result);		
		System.out.println(result.aperture);
		System.out.println(result.artist);
		System.out.println(result.camera_maker);
		System.out.println(result.camera_model);
		System.out.println(result.data_size);
		System.out.println(result.desc);
		System.out.println(result.flip);
		System.out.println(result.focal_len);
		System.out.println(result.iso_speed);
		System.out.println(result.shot_order);
		System.out.println(result.shutter);
		System.out.println(result.timestamp);
		System.out.println(result.is_jpeg);
		RawLoaderNative.INSTANCE.FreeExtractedDescription(result);
	}

}
