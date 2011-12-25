package com.bigfatbrowncat.cateye.core;
import java.util.Date;

import com.bigfatbrowncat.cateye.core.raw.IBitmapsLibraryNative;

public abstract class ImageDescription {
	/**
	 * @return the thumbnail
	 */
	// TODO: Yeah, I know it's weird. We have to replace Int8Bitmap onto the more appropriate class
	public abstract IBitmapsLibraryNative.Int8Bitmap getThumbnail();

	/**
	 * @return the iso speed
	 */
	public abstract float getIsoSpeed();

	/**
	 * @return the shutter
	 */
	public abstract float getShutter();

	/**
	 * @return the aperture
	 */
	public abstract float getAperture();

	/**
	 * @return the focal length
	 */
	public abstract float getFocalLength();

	/**
	 * @return the timestamp
	 */
	public abstract Date getTimestamp();

	/**
	 * @return the shot order
	 */
	public abstract int getShotOrder();

	/**
	 * @return the description
	 */
	public abstract String getDescription();

	/**
	 * @return the artist
	 */
	public abstract String getArtist();

	/**
	 * @return the camera maker
	 */
	public abstract String getCameraMaker();

	/**
	 * @return the camera model
	 */
	public abstract String getCameraModel();

	/**
	 * @return the flip
	 */
	public abstract int getFlip();
	
	/**
	 * Releases the native resources
	 */
	public abstract void dispose();
}
