package com.bigfatbrowncat.cateye.core.raw;

import java.util.Date;

import com.bigfatbrowncat.cateye.core.ImageDescription;
import com.bigfatbrowncat.cateye.core.raw.IBitmapsLibraryNative.Int8Bitmap;
import com.bigfatbrowncat.cateye.core.raw.IRawLoaderNative.RawImageDescriptionNative;

class RawImageDescription extends ImageDescription {
	protected Int8Bitmap thumbnail;

	protected float isoSpeed;
	protected float shutter;
	protected float aperture;
	protected float focalLength;
	protected Date timestamp;
	protected int shotOrder;
	protected String description;
	protected String artist;
	protected String cameraMaker;
	protected String cameraModel;
	protected int flip;

	private final IBitmapsLibraryNative bitmapsLibrary;

	public RawImageDescription(IBitmapsLibraryNative bitmapsLibrary) {
		this.bitmapsLibrary = bitmapsLibrary;
	}

	public void loadFromNative(RawImageDescriptionNative description) {
		this.isoSpeed = description.iso_speed;
		this.shutter = description.shutter;
		this.aperture = description.aperture;
		this.focalLength = description.focal_len;
		this.timestamp = description.timestamp.getDate();
		this.shotOrder = description.shot_order;
		this.description = description.desc;
		this.artist = description.artist;
		this.cameraMaker = description.camera_maker;
		this.cameraModel = description.camera_model;
		this.flip = description.flip;

		if (description.thumbnail != null) {
			this.thumbnail = bitmapsLibrary.Int8Bitmap_Copy(description.thumbnail);
		}
	}

	/**
	 * @return the thumbnail
	 */
	public Int8Bitmap getThumbnail() {
		return thumbnail;
	}

	/**
	 * @return the iso speed
	 */
	public float getIsoSpeed() {
		return isoSpeed;
	}

	/**
	 * @return the shutter
	 */
	public float getShutter() {
		return shutter;
	}

	/**
	 * @return the aperture
	 */
	public float getAperture() {
		return aperture;
	}

	/**
	 * @return the focal length
	 */
	public float getFocalLength() {
		return focalLength;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @return the shot order
	 */
	public int getShotOrder() {
		return shotOrder;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * @return the camera maker
	 */
	public String getCameraMaker() {
		return cameraMaker;
	}

	/**
	 * @return the camera model
	 */
	public String getCameraModel() {
		return cameraModel;
	}

	/**
	 * @return the flip
	 */
	public int getFlip() {
		return flip;
	}

	@Override
	public void dispose() {
		if (thumbnail != null) {
			bitmapsLibrary.Int8Bitmap_Destroy(thumbnail);
		}
	}
}
