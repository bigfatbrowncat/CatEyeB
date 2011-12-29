package com.cateye.core;

public abstract class Image {
	protected ImageDescription description;
	protected PreciseBitmap bitmap;
	
	/**
	 * @return the description of image
	 */
	public ImageDescription getDescription() {
		return description;
	}

	/**
	 * @return the precious bitmap
	 */
	public PreciseBitmap getBitmap() {
		return bitmap;
	}
	
	public void setBitmap(PreciseBitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	public Image(ImageDescription imageDescription, PreciseBitmap bitmap) {
		this.description = imageDescription;
		this.bitmap = bitmap;
	}
	
	public int getWidth() {
		return bitmap.width;
	}
	
	public int getHeight() {
		return bitmap.height;
	}
	
	public void dispose() {
		if (this.bitmap != null) {
			this.bitmap.dispose();
		}
		
		if (this.description != null) {
			this.description.dispose();
		}
	}
}
