package com.bigfatbrowncat.cateye.core.native_;

import com.bigfatbrowncat.cateye.core.ThumbnailBitmap;

public class RawThumbnailImage extends ThumbnailBitmap {
	private Int8Bitmap bitmap;

	/**
	 * @return the bitmap
	 */
	public Int8Bitmap getBitmap() {
		return bitmap;
	}
	
	public RawThumbnailImage(Int8Bitmap bitmap) {
		this.bitmap = Int8Bitmap.Int8Bitmap_Copy(bitmap);
		this.width = bitmap.width;
		this.height = bitmap.height;
	}

	@Override
	public void dispose() {
		Int8Bitmap.Int8Bitmap_Destroy(this.bitmap);
		this.bitmap = null;
	}
}
