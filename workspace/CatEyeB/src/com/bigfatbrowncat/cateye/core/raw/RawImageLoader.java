package com.bigfatbrowncat.cateye.core.raw;

import java.util.ArrayList;
import java.util.List;

import com.bigfatbrowncat.cateye.core.IImageLoader;
import com.bigfatbrowncat.cateye.core.IOnProgressListener;
import com.bigfatbrowncat.cateye.core.Image;
import com.bigfatbrowncat.cateye.core.ImageDescription;
import com.bigfatbrowncat.cateye.core.raw.IRawLoaderNative.RawImageDescriptionNative;
import com.google.inject.Inject;
import com.sun.jna.Pointer;

class RawImageLoader implements IImageLoader {
	private final IRawLoaderNative nativeLoader;
	private final IBitmapsLibraryNative bitmapsLibrary;

	@Inject
	public RawImageLoader(IRawLoaderNative nativeLoader,
			IBitmapsLibraryNative bitmapsLibrary) {
		this.nativeLoader = nativeLoader;
		this.bitmapsLibrary = bitmapsLibrary;
	}

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

	@Override
	public ImageDescription loadDescription(String fileName) {
		String filePath = new java.io.File(fileName).getAbsolutePath();
		
		// load description to java structure
		Pointer descriptionPtr = nativeLoader.ExtractedDescription_Create();
		nativeLoader.ExtractedDescription_LoadFromFile(filePath, descriptionPtr);
		RawImageDescriptionNative description = new RawImageDescriptionNative();
		description.readFromMemory(descriptionPtr);

		RawImageDescription result = new RawImageDescription(bitmapsLibrary);
		result.loadFromNative(description);

		nativeLoader.ExtractedDescription_Destroy(descriptionPtr);

		return result;
	}
}
