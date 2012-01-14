package com.cateye.core;

public class UnsupportedFormatException extends ImageLoadingException
{
	public UnsupportedFormatException()
	{
		super();
	}
	
	public UnsupportedFormatException(String message)
	{
		super(message);
	}

	private static final long serialVersionUID = 1L;
}
