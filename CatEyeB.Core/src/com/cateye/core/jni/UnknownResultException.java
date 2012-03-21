package com.cateye.core.jni;

/**
 * Native function returns unknown result
 */
public class UnknownResultException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	private final int resultCode;
	
	public int getResultCode()
	{
		return this.resultCode;
	}
	
	public UnknownResultException(int resultCode)
	{
		this.resultCode = resultCode;
	}
}
