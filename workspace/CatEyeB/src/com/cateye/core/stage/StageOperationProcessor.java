package com.cateye.core.stage;

import java.util.ArrayList;
import java.util.List;

import com.cateye.core.IOnProgressListener;

public abstract class StageOperationProcessor<T extends StageOperation> {
	protected List<IOnProgressListener> progressListeners = new ArrayList<IOnProgressListener>();
	
	public abstract int calculateEffort();
	
	public void addOnProgressListener(IOnProgressListener listener) {
		progressListeners.add(listener);
	}
	
	public void removeOnProgressListener(IOnProgressListener listener) {
		progressListeners.remove(listener);
	}
	
	protected void fireOnProgress(float progress) {
		for (IOnProgressListener listener : progressListeners) {
			listener.invoke(this, progress);
		}
	}
	
	public abstract void process(T stageOperation);
}
