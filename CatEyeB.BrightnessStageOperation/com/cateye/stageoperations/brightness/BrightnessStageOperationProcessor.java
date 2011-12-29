package com.cateye.stageoperations.brightness;

import com.cateye.core.IPreciseBitmap;
import com.cateye.core.native_.BrightnessStageOperationNative;
import com.cateye.core.stage.StageOperationProcessor;

public class BrightnessStageOperationProcessor extends
		StageOperationProcessor<BrightnessStageOperation>
{
	@Override
	public int calculateEffort()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void process(BrightnessStageOperation stageOperation,
			IPreciseBitmap bitmap)
	{
		BrightnessStageOperationNative.Process(bitmap, stageOperation, new IOnOperationProgressListener()
		{
			@Override
			public void invoke(float progress)
			{
				fireOnProgress(progress);
			}
		}, new IOnOperationProcessedListener()
		{
			@Override
			public void invoke(IPreciseBitmap result)
			{
				fireOnImageProcessed(0, result);
			}
		});
	}
	
}
