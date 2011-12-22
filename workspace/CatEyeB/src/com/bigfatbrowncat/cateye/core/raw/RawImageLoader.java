package com.bigfatbrowncat.cateye.core.raw;

import java.util.ArrayList;
import java.util.List;

import com.bigfatbrowncat.cateye.core.IImageLoader;
import com.bigfatbrowncat.cateye.core.IOnProgressListener;
import com.bigfatbrowncat.cateye.core.Image;

class RawImageLoader implements IImageLoader {
	private final List<IOnProgressListener> onProgressListeners = new ArrayList<IOnProgressListener>();
	
	@Override
	public void addOnProgressListener(IOnProgressListener listener) {
		onProgressListeners.add(listener);
	}

	@Override
	public void removeOnProgressListener(IOnProgressListener listener) {
		onProgressListeners.remove(listener);
	}
	
	protected void invokeOnProgress(float progress) {
		for (IOnProgressListener listener : onProgressListeners) {
			listener.invoke(progress);
		}
	}
	
	@Override
	public Boolean canLoad(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image load(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}
}
