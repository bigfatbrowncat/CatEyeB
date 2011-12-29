package com.cateye.core.stage;

import com.cateye.core.PreciseBitmap;

public interface IOnImageProcessedListener {
	void invoke(Object sender, int code, PreciseBitmap bitmap);
}
