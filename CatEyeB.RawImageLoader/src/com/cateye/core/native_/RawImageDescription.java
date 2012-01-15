package com.cateye.core.native_;

import java.util.Date;

import com.cateye.core.IPreviewBitmap;
import com.cateye.core.ImageDescription;

class RawImageDescription extends ImageDescription
{
	protected IPreviewBitmap thumbnail;
	
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
	
	protected void setDate(int value)
	{
		this.timestamp = new Date(value * 1000L);
	}
	
	/**
	 * @return the thumbnail
	 */
	public IPreviewBitmap getThumbnail()
	{
		return thumbnail;
	}
	
	/**
	 * @return the iso speed
	 */
	public float getIsoSpeed()
	{
		return isoSpeed;
	}
	
	/**
	 * @return the shutter
	 */
	public float getShutter()
	{
		return shutter;
	}
	
	/**
	 * @return the aperture
	 */
	public float getAperture()
	{
		return aperture;
	}
	
	/**
	 * @return the focal length
	 */
	public float getFocalLength()
	{
		return focalLength;
	}
	
	/**
	 * @return the timestamp
	 */
	public Date getTimestamp()
	{
		return timestamp;
	}
	
	/**
	 * @return the shot order
	 */
	public int getShotOrder()
	{
		return shotOrder;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * @return the artist
	 */
	public String getArtist()
	{
		return artist;
	}
	
	/**
	 * @return the camera maker
	 */
	public String getCameraMaker()
	{
		return cameraMaker;
	}
	
	/**
	 * @return the camera model
	 */
	public String getCameraModel()
	{
		return cameraModel;
	}
	
	/**
	 * @return the flip
	 */
	public int getFlip()
	{
		return flip;
	}
	
	@Override
	public void dispose()
	{
		if (thumbnail != null)
		{
			thumbnail.dispose();
		}
	}
}
