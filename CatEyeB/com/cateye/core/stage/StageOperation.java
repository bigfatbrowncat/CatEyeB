package com.cateye.core.stage;

import java.util.ArrayList;
import java.util.List;

import com.cateye.core.IOnPropertyChangedListener;

public abstract class StageOperation {
	protected List<IOnPropertyChangedListener> propertyChangedListeners = new ArrayList<IOnPropertyChangedListener>();
	
	public void addOnPropertyChangedListener(IOnPropertyChangedListener listener) {
		propertyChangedListeners.add(listener);
	}
	
	public void removeOnPropertyChangedListener(IOnPropertyChangedListener listener) {
		propertyChangedListeners.remove(listener);
	}
	
	protected void fireOnPropertyChanged(String propertyName, Object newValue) {
		for (IOnPropertyChangedListener listener : propertyChangedListeners) {
			listener.invoke(this, propertyName, newValue);
		}
	}
}
