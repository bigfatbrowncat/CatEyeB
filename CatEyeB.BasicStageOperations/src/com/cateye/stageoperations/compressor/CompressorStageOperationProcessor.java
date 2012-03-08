package com.cateye.stageoperations.compressor;

import com.cateye.core.IPreciseBitmap;
import com.cateye.core.IProgressListener;
import com.cateye.core.native_.LibraryLoader;
import com.cateye.core.stage.IStageOperationProcessor;

public class CompressorStageOperationProcessor implements
		IStageOperationProcessor<CompressorStageOperation> 
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
	public native void process(CompressorStageOperation params, IPreciseBitmap bitmap,
			IProgressListener progressListener);

}
