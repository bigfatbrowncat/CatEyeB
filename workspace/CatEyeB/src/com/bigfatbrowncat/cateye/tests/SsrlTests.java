package com.bigfatbrowncat.cateye.tests;
import static org.junit.Assert.*;

import org.junit.Test;

import com.bigfatbrowncat.cateye.core.raw.ExtractedDescription;
import com.bigfatbrowncat.cateye.core.raw.Ssrl;


public class SsrlTests {

	@Test
	public void test() {
//		Ssrl.INSTANCE.ExtractDescriptionFromFile("e:\\DropBox\\IMG_5196.CR2", result);
		ExtractedDescription result = Ssrl.INSTANCE.Test("e:\\DropBox\\IMG_5196.CR2");
		assertEquals(10, result.flip);
	}

}
