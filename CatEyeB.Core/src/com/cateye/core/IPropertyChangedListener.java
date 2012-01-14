package com.cateye.core;

public interface IPropertyChangedListener
{
	void invoke(Object sender, String propertyName, Object newValue);
}
