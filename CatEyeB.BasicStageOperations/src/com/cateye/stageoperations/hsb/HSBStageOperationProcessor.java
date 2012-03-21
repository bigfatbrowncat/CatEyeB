package com.cateye.stageoperations.hsb;

import com.cateye.core.IPreciseBitmap;
import com.cateye.core.IProgressListener;
import com.cateye.core.jni.LibraryLoader;
import com.cateye.core.stage.IStageOperationProcessor;
import com.cateye.stageoperations.hsb.HSBStageOperation;

class HSBStageOperationProcessor implements 
		IStageOperationProcessor<HSBStageOperation>
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
	public native void process(HSBStageOperation params, IPreciseBitmap bitmap, 
			IProgressListener progressListener);
}