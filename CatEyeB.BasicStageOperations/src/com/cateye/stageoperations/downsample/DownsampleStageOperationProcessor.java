package com.cateye.stageoperations.downsample;

import com.cateye.core.IPreciseBitmap;
import com.cateye.core.IProgressListener;
import com.cateye.core.jni.LibraryLoader;
import com.cateye.core.stage.IStageOperationProcessor;

public class DownsampleStageOperationProcessor implements
		IStageOperationProcessor<DownsampleStageOperation> 
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
	public native void process(DownsampleStageOperation params, IPreciseBitmap bitmap,
			IProgressListener progressListener);

}
