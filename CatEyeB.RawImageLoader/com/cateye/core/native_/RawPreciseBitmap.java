package com.cateye.core.native_;

class RawPreciseBitmap extends com.cateye.core.PreciseBitmap {
	private $PreciseBitmap bitmap;

	/**
	 * @return the bitmap
	 */
	public $PreciseBitmap getNativeBitmap() {
		return bitmap;
	}
	
	public RawPreciseBitmap($PreciseBitmap bitmap) {
		this.bitmap = $BitmapsLibrary.PreciseBitmap_Copy(bitmap);
		this.width = bitmap.width;
		this.height = bitmap.height;
	}

	@Override
	public void dispose() {
		$BitmapsLibrary.PreciseBitmap_Destroy(this.bitmap);
		this.bitmap = null;
	}
}
