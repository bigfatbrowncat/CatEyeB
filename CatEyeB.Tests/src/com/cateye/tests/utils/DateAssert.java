package com.cateye.tests.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.Assert;

public class DateAssert
{
	public static void assertEquals(Calendar first, Calendar second)
	{
		Assert.assertEquals(first.get(Calendar.YEAR), second.get(Calendar.YEAR));
		Assert.assertEquals(first.get(Calendar.MONTH), second.get(Calendar.MONTH));
		Assert.assertEquals(first.get(Calendar.DAY_OF_MONTH), second.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(first.get(Calendar.HOUR), second.get(Calendar.HOUR));
		Assert.assertEquals(first.get(Calendar.MINUTE), second.get(Calendar.MINUTE));
		Assert.assertEquals(first.get(Calendar.SECOND), second.get(Calendar.SECOND));
	}

}
