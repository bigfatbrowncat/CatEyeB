package com.cateye.core;

public interface IOnPropertyChangedListener {
	void invoke(Object sender, String propertyName, Object newValue);
}
