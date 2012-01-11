package com.cateye.core.stage;

import java.util.ArrayList;
import java.util.List;

import com.cateye.core.IPropertyChangedListener;

public abstract class StageOperation
{
	protected List<IPropertyChangedListener> propertyChangedListeners = new ArrayList<IPropertyChangedListener>();
	
	public void addOnPropertyChangedListener(IPropertyChangedListener listener)
	{
		propertyChangedListeners.add(listener);
	}
	
	public void removeOnPropertyChangedListener(
			IPropertyChangedListener listener)
	{
		propertyChangedListeners.remove(listener);
	}
	
	protected void fireOnPropertyChanged(String propertyName, Object newValue)
	{
		for (IPropertyChangedListener listener : propertyChangedListeners)
		{
			listener.invoke(this, propertyName, newValue);
		}
	}
}
