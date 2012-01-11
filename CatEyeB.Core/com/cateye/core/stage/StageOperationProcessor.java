package com.cateye.core.stage;

import java.util.ArrayList;
import java.util.List;

import com.cateye.core.IPreciseBitmap;
import com.cateye.core.IProgressListener;

public abstract class StageOperationProcessor<T extends StageOperation>
{
	//# Progress listeners
	protected List<IProgressListener> progressListeners = new ArrayList<IProgressListener>();
	
	public void addOnProgressListener(IProgressListener listener)
	{
		progressListeners.add(listener);
	}
	
	public void removeOnProgressListener(IProgressListener listener)
	{
		progressListeners.remove(listener);
	}
	
	protected void fireOnProgress(float progress)
	{
		for (IProgressListener listener : progressListeners)
			listener.invoke(this, progress);
	}
	
	//# Image processed listeners
	protected List<IOnImageProcessedListener> imageProcessedListeners = new ArrayList<IOnImageProcessedListener>();
	
	public void addOnImageProcessedListener(IOnImageProcessedListener listener)
	{
		imageProcessedListeners.add(listener);
	}
	
	public void removeOnImageProcessedListener(
			IOnImageProcessedListener listener)
	{
		imageProcessedListeners.remove(listener);
	}
	
	protected void fireOnImageProcessed(IPreciseBitmap bitmap)
	{
		for (IOnImageProcessedListener listener : imageProcessedListeners)
			listener.invoke(this, bitmap);
	}
	
	//# Interface
	/**
	 * Calculates effort of the current processor
	 */
	public abstract int calculateEffort();
	
	/**
	 * Processes the bitmap
	 */
	public abstract void process(T params, IPreciseBitmap bitmap);
}
