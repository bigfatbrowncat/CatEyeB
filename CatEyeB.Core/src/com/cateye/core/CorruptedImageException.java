package com.cateye.core;

/**
 * File with bitmap cannot be loaded because it is corrupted
 */
public class CorruptedImageException extends ImageLoadingException
{
	private static final long serialVersionUID = 1L;
	public CorruptedImageException(String message)
	{
		super(message);
	}
}
