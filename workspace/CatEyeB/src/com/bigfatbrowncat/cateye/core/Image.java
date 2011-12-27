package com.bigfatbrowncat.cateye.core;

public abstract class Image {
	protected ImageDescription description;
	protected PreciousBitmap bitmap;
	
	/**
	 * @return the description of image
	 */
	public ImageDescription getDescription() {
		return description;
	}

	/**
	 * @return the precious bitmap
	 */
	public PreciousBitmap getBitmap() {
		return bitmap;
	}
	
	public Image(ImageDescription imageDescription, PreciousBitmap bitmap) {
		this.description = imageDescription;
		this.bitmap = bitmap;
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
