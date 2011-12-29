package com.cateye.tests.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import junit.framework.Assert;

public class DateAssert
{
	public static void assertEquals(Date expected, Date actual)
	{
		Assert.assertTrue("Expected: " + expected + ", Actual: " + actual,
				Math.abs(actual.getTime() - expected.getTime()) < 1000);
	}
	
	/**
	 * Asserts that two dates are equal
	 * 
	 * @param expected
	 *            date should be present as "M/d/y h:mm:ss PM MSK"
	 * @param actual
	 */
	public static void assertEquals(String expected, Date actual)
	{
		DateFormat format = DateFormat.getDateTimeInstance(DateFormat.SHORT,
				DateFormat.LONG, Locale.US);
		try
		{
			assertEquals(format.parse(expected), actual);
		} catch (ParseException e)
		{
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}
	}
}
