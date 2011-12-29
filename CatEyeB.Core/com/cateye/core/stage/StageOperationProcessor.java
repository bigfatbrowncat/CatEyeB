package com.cateye.core.stage;

import java.util.ArrayList;
import java.util.List;

import com.cateye.core.IOnProgressListener;
import com.cateye.core.IPreciseBitmap;

public abstract class StageOperationProcessor<T extends StageOperation>
{
	protected List<IOnProgressListener> progressListeners = new ArrayList<IOnProgressListener>();
	
	public void addOnProgressListener(IOnProgressListener listener)
	{
		progressListeners.add(listener);
	}
	
	public void removeOnProgressListener(IOnProgressListener listener)
	{
		progressListeners.remove(listener);
	}
	
	protected void fireOnProgress(float progress)
	{
		for (IOnProgressListener listener : progressListeners)
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
