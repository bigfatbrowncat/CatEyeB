package com.bigfatbrowncat.cateye.core;

import com.sun.jna.Pointer;

public class Image {
	/**
	 * Pointer to the image data in memory
	 */
	private Pointer pointer;

	/**
	 * @return the pointer to the image data in memory
	 */
	protected Pointer getPointer() {
		return pointer;
	}
	
	/**
	 * Releases the native object handler
	 */
	public void dispose() {
		
	}
}
