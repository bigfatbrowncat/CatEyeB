package com.cateye.core;

/**
 * Data cannot be read or be written to/from the file
 */
public class BadFileException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	public BadFileException(String message)
	{
		super(message);
	}
}

// TODO: Remove that IOException would be enough