package com.cateye.core.stage;

import java.util.ArrayList;
import java.util.List;

import com.cateye.core.IProgressListener;
import com.cateye.core.IPreciseBitmap;

public abstract class StageOperationProcessor<T extends StageOperation>
{
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
		{
			listener.invoke(this, progress);
		}
	}
	
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
	
	protected void fireOnImageProcessed(int code, IPreciseBitmap bitmap)
	{
		for (IOnImageProcessedListener listener : imageProcessedListeners)
		{
			listener.invoke(this, code, bitmap);
		}
	}
	
	public abstract int calculateEffort();
	
	public abstract void process(T stageOperation, IPreciseBitmap bitmap);
}
