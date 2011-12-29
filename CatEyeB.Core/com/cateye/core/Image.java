package com.cateye.core;

public class Image
{
	protected ImageDescription description;
	protected IPreciseBitmap bitmap;
	
	/**
	 * @return the description of image
	 */
	public ImageDescription getDescription()
	{
		return description;
	}
	
	/**
	 * @return the precious bitmap
	 */
	public IPreciseBitmap getBitmap()
	{
		return bitmap;
	}
	
	public void setBitmap(IPreciseBitmap bitmap)
	{
		this.bitmap = bitmap;
	}
	
	public Image(ImageDescription imageDescription, IPreciseBitmap bitmap)
	{
		this.description = imageDescription;
		this.bitmap = bitmap;
	}
	
	public int getWidth()
	{
		return bitmap.getWidth();
	}
	
	public int getHeight()
	{
		return bitmap.getHeight();
	}
	
	public void dispose()
	{
		if (this.bitmap != null)
		{
			this.bitmap.dispose();
		}
		
		if (this.description != null)
		{
			this.description.dispose();
		}
	}
}
