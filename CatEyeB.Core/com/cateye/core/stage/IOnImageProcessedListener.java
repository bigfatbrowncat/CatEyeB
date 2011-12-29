package com.cateye.core.stage;

import com.cateye.core.IPreciseBitmap;

public interface IOnImageProcessedListener
{
	void invoke(Object sender, int code, IPreciseBitmap bitmap);
}
