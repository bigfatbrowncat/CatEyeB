package com.cateye.core;

public class Image
{
	protected ImageDescription description;
	protected IPreciseBitmap bitmap;
	protected IImageLoader loader;
	
	/**
	 * @return the description of image
	 */
	public ImageDescription getDescription()
	{
		if (description == null) description = loader.loadDescriptionForImage(this);
		return description;
	}
	
	/**
	 * @return the precise bitmap
	 */
	public IPreciseBitmap getBitmap()
	{
		if (bitmap == null) bitmap = loader.loadPreciseBitmapForImage(this);
		return bitmap;
	}
	
	public Image(IImageLoader loader)
	{
		if (loader == null) throw new IllegalArgumentException("loader shouldn't be null");
		this.loader = loader;
	}

	public Image(IImageLoader loader, ImageDescription imageDescription, IPreciseBitmap bitmap)
	{
		this(loader);
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
			this.bitmap.free();
		}
		
		if (this.description != null)
		{
			this.description.dispose();
		}
	}
}
