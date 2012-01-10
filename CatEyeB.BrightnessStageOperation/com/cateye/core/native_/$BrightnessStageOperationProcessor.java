package com.cateye.core.native_;

import com.cateye.core.IPreciseBitmap;
import com.cateye.core.stage.StageOperationProcessor;
import com.cateye.stageoperations.brightness.BrightnessStageOperation;
import com.sun.jna.Library;
import com.sun.jna.Native;

public class $BrightnessStageOperationProcessor extends StageOperationProcessor<BrightnessStageOperation> implements Library
{
	public static native void Process($PreciseBitmap bmp, double brightness,
			$IBrightnessOperationProgressReporter progress_reporter);
	
	static
	{
		Native.register(LibraryLoader.load("brightness.CatEyeOperation"));
	}

	@Override
	public int calculateEffort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void process(BrightnessStageOperation stageOperation,
			IPreciseBitmap bitmap) {
		$PreciseBitmap rawBitmap = ($PreciseBitmap) bitmap;
		
		$BrightnessStageOperationProcessor.Process(rawBitmap, stageOperation.getBrightness(),
			new $IBrightnessOperationProgressReporter()
			{
				@Override
				public boolean invoke(float progress)
				{
					fireOnProgress(progress);
					return true;
				}
			}
		);
		
		fireOnImageProcessed(0, rawBitmap);
	}
}