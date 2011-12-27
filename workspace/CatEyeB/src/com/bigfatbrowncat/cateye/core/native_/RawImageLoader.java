package com.bigfatbrowncat.cateye.core.native_;

import java.util.ArrayList;
import java.util.List;

import com.bigfatbrowncat.cateye.core.IImageLoader;
import com.bigfatbrowncat.cateye.core.IOnProgressListener;
import com.bigfatbrowncat.cateye.core.Image;
import com.sun.jna.Pointer;

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
		RawImageDescription description = loadDescription(fileName);
		return new RawImage(description, null);
	}

	@Override
	public RawImageDescription loadDescription(String fileName) {
		String filePath = new java.io.File(fileName).getAbsolutePath();
		
		// load description to java structure
		Pointer descriptionPtr = RawImageDescriptionNative.ExtractedDescription_Create();
		RawImageDescriptionNative.ExtractedDescription_LoadFromFile(filePath, descriptionPtr);
		RawImageDescriptionNative description = new RawImageDescriptionNative();
		description.readFromMemory(descriptionPtr);

		RawImageDescription result = new RawImageDescription();
		result.loadFromNative(description);

		RawImageDescriptionNative.ExtractedDescription_Destroy(descriptionPtr);

		return result;
	}
}
