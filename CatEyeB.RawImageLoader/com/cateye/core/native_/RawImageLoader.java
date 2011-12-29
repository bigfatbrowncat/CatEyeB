package com.cateye.core.native_;

import java.util.ArrayList;
import java.util.List;

import com.cateye.core.IImageLoader;
import com.cateye.core.IOnImageLoadedListener;
import com.cateye.core.IOnProgressListener;
import com.cateye.core.Image;
import com.sun.jna.Pointer;

class RawImageLoader implements IImageLoader {
	private final List<IOnProgressListener> progressListeners = new ArrayList<IOnProgressListener>();
	private final List<IOnImageLoadedListener> imageLoadedListeners = new ArrayList<IOnImageLoadedListener>();

	@Override
	public void addOnProgressListener(IOnProgressListener listener) {
		progressListeners.add(listener);
	}

	@Override
	public void removeOnProgressListener(IOnProgressListener listener) {
		progressListeners.remove(listener);
	}

	@Override
	public void addOnImageLoadedListener(IOnImageLoadedListener listener) {
		imageLoadedListeners.add(listener);
	}

	@Override
	public void removeOnImageLoadedListener(IOnImageLoadedListener listener) {
		imageLoadedListeners.remove(listener);
	}
	
	protected void invokeOnProgress(float progress) {
		for (IOnProgressListener listener : progressListeners) {
			listener.invoke(this, progress);
		}
	}

	protected void invokeOnImageLoaded(Image image) {
		for (IOnImageLoadedListener listener : imageLoadedListeners) {
			listener.invoke(this, image);
		}
	}
	
	@Override
	public Boolean canLoad(String fileName) {
		return true; // TODO: implement checking if loading is available
	}

	@Override
	public void load(String fileName) {
		RawImageDescription description = loadDescription(fileName);
		resultReporter = new ExtractingResultReporter(description, this);
		$RawImage.ExtractedRawImage_LoadFromFile(fileName, true, progressReporter, resultReporter);
	}

	@Override
	public RawImageDescription loadDescription(String fileName) {
		String filePath = new java.io.File(fileName).getAbsolutePath();
		
		// load description to java structure
		Pointer descriptionPtr = $RawImageDescription.ExtractedDescription_Create();
		$RawImageDescription.ExtractedDescription_LoadFromFile(filePath, descriptionPtr);
		$RawImageDescription description = new $RawImageDescription();
		description.readFromMemory(descriptionPtr);

		RawImageDescription result = new RawImageDescription();
		result.loadFromNative(description);

		$RawImageDescription.ExtractedDescription_Destroy(descriptionPtr);

		return result;
	}
	
	ExtractingProgressReporter progressReporter = new ExtractingProgressReporter(this);
	ExtractingResultReporter resultReporter;
	
	/**
	 * Implementation of native extracting progress reporter
	 */
	static class ExtractingProgressReporter implements $IExtractingProgressReporter {
		private final RawImageLoader loader;

		public ExtractingProgressReporter(RawImageLoader loader) {
			this.loader = loader;
		}
		
		@Override
		public boolean invoke(float progress) {
			loader.invokeOnProgress(progress);
			return true;
		}
	}
	
	/**
	 * Implementation of native extracting result reporter
	 */
	static class ExtractingResultReporter extends $IExtractingResultReporter {
		private final RawImageDescription description;
		private final RawImageLoader loader;

		public ExtractingResultReporter(RawImageDescription description, RawImageLoader loader) {
			this.description = description;
			this.loader = loader;
		}

		@Override
		public void callback(int code, Pointer res) {
			$PreciseBitmap bitmap = new $PreciseBitmap();
			bitmap.readFromMemory(res);
			if (code == 0) {
				Image image = new RawImage(description, new RawPreciseBitmap(bitmap));
				loader.invokeOnImageLoaded(image);
			} else {
				throw new RuntimeException("Something is wrong");
			}
		}
	}
}
