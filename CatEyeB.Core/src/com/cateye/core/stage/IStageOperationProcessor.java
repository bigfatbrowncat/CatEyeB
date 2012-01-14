package com.cateye.core.stage;

import com.cateye.core.IPreciseBitmap;
import com.cateye.core.IProgressListener;

public interface IStageOperationProcessor<T extends StageOperation>
{
	/**
	 * Calculates effort of the current processor
	 */
	public abstract int calculateEffort();
	
	/**
	 * Processes the bitmap
	 */
	public abstract void process(T params, IPreciseBitmap bitmap, IProgressListener progressListener);
}
