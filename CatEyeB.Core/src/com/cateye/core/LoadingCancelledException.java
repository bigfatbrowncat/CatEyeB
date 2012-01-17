package com.cateye.core;

public class LoadingCancelledException extends ImageLoadingException
{
	private static final long serialVersionUID = 1L;
	public LoadingCancelledException(String message)
	{
		super(message);
	}
}
