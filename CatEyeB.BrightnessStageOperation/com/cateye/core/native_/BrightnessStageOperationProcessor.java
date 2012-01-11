package com.cateye.core.native_;

import com.cateye.core.IPreciseBitmap;
import com.cateye.core.stage.StageOperationProcessor;
import com.cateye.stageoperations.brightness.BrightnessStageOperation;

class BrightnessStageOperationProcessor extends StageOperationProcessor<BrightnessStageOperation>
{
	static native void Process(PreciseBitmap bmp, BrightnessStageOperation operation);
	
	static
	{
		LibraryLoader.attach("brightness.CatEyeOperation");
	}

	@Override
	public int calculateEffort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void process(BrightnessStageOperation params, IPreciseBitmap bitmap) {
		Process((PreciseBitmap)bitmap, params);
		
		fireOnImageProcessed(bitmap);
	}
}