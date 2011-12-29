package com.cateye.core.native_;

import com.cateye.core.IPreciseBitmap;
import com.cateye.stageoperations.brightness.BrightnessStageOperation;
import com.cateye.stageoperations.brightness.IOnOperationProcessedListener;
import com.cateye.stageoperations.brightness.IOnOperationProgressListener;

public class BrightnessStageOperationNative
{
	public static void Process(IPreciseBitmap bitmap,
			BrightnessStageOperation operation,
			final IOnOperationProgressListener progressListener,
			final IOnOperationProcessedListener processedListener)
	{
		$PreciseBitmap rawBitmap = ($PreciseBitmap) bitmap;
		
		$BrightnessStageOperation.Process(rawBitmap, operation.getBrightness(),
			new $IBrightnessOperationProgressReporter()
			{
				@Override
				public boolean invoke(float progress)
				{
					progressListener.invoke(progress);
					return true;
				}
			}
		);
		
		processedListener.invoke(rawBitmap);
	}
}
