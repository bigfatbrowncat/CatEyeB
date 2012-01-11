package com.cateye.core;

public class ImageLoadingException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public ImageLoadingException()
	{
		super();
	}
	
	public ImageLoadingException(String message)
	{
		super(message);
	}
}
