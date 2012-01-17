package com.cateye.core;

public class BadCropException extends ImageLoadingException
{
	public BadCropException(String message)
	{
		super(message);
	}
	private static final long serialVersionUID = 1L;
}
