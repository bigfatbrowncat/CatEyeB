package com.cateye.stageoperations.rgb;

import com.cateye.core.IPreciseBitmap;
import com.cateye.core.IProgressListener;
import com.cateye.core.native_.LibraryLoader;
import com.cateye.core.stage.IStageOperationProcessor;


class RGBStageOperationProcessor implements IStageOperationProcessor<RGBStageOperation>
{
	static
	{
		LibraryLoader.attach("Basic.CatEyeStageOperation");
	}

	@Override
	public int calculateEffort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public native void process(RGBStageOperation params, IPreciseBitmap bitmap, IProgressListener progressListener);
}