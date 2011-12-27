package com.bigfatbrowncat.cateye.core.native_;

import com.bigfatbrowncat.cateye.core.PreciousBitmap;

public class RawPreciousBitmap extends PreciousBitmap {
	private FloatBitmap bitmap;

	/**
	 * @return the bitmap
	 */
	public FloatBitmap getBitmap() {
		return bitmap;
	}
	
	public RawPreciousBitmap(FloatBitmap bitmap) {
		this.bitmap = FloatBitmap.FloatBitmap_Copy(bitmap);
		this.width = bitmap.width;
		this.height = bitmap.height;
	}

	@Override
	public void dispose() {
		FloatBitmap.FloatBitmap_Destroy(this.bitmap);
		this.bitmap = null;
	}
}
