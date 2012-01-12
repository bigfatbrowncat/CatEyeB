package com.cateye.core.stage;

import com.cateye.core.IProgressListener;

/**
 * Private class of progress listeners
 */
class StageOperationProgressListener implements IProgressListener
{
	private final float effortPart;
	private final Stage stage;
	
	public StageOperationProgressListener(Stage stage, int processorEffort, int summaryEfforts)
	{
		this.stage = stage;
		this.effortPart = processorEffort / (float) summaryEfforts;
	}
	
	@Override
	public void invoke(Object sender, float progress)
	{
		stage.invokeOnProgress(progress * effortPart);
	}
}