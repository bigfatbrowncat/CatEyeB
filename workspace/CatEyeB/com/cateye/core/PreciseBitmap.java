package com.cateye.core;

public abstract class PreciseBitmap {
	protected int width;
	protected int height;
	
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	
	public abstract void dispose();
}
