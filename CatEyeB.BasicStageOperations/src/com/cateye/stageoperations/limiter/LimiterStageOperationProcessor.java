package com.cateye.stageoperations.limiter;

import com.cateye.core.IPreciseBitmap;
import com.cateye.core.IProgressListener;
import com.cateye.core.jni.LibraryLoader;
import com.cateye.core.stage.IStageOperationProcessor;


class LimiterStageOperationProcessor implements IStageOperationProcessor<LimiterStageOperation>
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
	public native void process(LimiterStageOperation params, IPreciseBitmap bitmap, IProgressListener progressListener);
}