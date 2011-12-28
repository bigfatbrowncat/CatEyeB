package com.cateye.core;

public class ArgumentOutOfRangeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ArgumentOutOfRangeException(String argumentName) {
		super(String.format("Argument %1$ is out of range", argumentName));
	}
}
