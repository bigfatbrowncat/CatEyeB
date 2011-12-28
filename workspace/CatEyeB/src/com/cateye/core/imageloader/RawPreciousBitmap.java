package com.cateye.core.imageloader;

class RawPreciousBitmap extends com.cateye.core.PreciousBitmap {
	private $PreciousBitmap bitmap;

	/**
	 * @return the bitmap
	 */
	public $PreciousBitmap getBitmap() {
		return bitmap;
	}
	
	public RawPreciousBitmap($PreciousBitmap bitmap) {
		this.bitmap = $PreciousBitmap.PreciousBitmap_Copy(bitmap);
		this.width = bitmap.width;
		this.height = bitmap.height;
	}

	@Override
	public void dispose() {
		$PreciousBitmap.PreciousBitmap_Destroy(this.bitmap);
		this.bitmap = null;
	}
}
