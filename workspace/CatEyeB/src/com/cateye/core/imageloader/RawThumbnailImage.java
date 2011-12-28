package com.cateye.core.imageloader;

import com.cateye.core.ThumbnailBitmap;

class RawThumbnailImage extends ThumbnailBitmap {
	private $PreviewBitmap bitmap;

	/**
	 * @return the bitmap
	 */
	public $PreviewBitmap getBitmap() {
		return bitmap;
	}
	
	public RawThumbnailImage($PreviewBitmap bitmap) {
		this.bitmap = $PreviewBitmap.PreviewBitmap_Copy(bitmap);
		this.width = bitmap.width;
		this.height = bitmap.height;
	}

	@Override
	public void dispose() {
		$PreviewBitmap.PreviewBitmap_Destroy(this.bitmap);
		this.bitmap = null;
	}
}
