package com.cateye.tests.utils;

import java.util.Date;

import junit.framework.Assert;

public class DateAssert
{
	public static void assertEquals(Date expected, Date actual)
	{
		Assert.assertTrue("Expected: " + expected + ", Actual: " + actual,
				Math.abs(actual.getTime() - expected.getTime()) < 1000);
	}

}
