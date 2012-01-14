package com.cateye.core.stage;

public class ImageLoadingException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public ImageLoadingException(Exception innerException)
	{
		super(innerException);
	}
}
