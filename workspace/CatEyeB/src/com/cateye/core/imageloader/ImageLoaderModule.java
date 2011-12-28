package com.cateye.core.imageloader;

import com.cateye.core.IImageLoader;
import com.google.inject.AbstractModule;

public class ImageLoaderModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(IImageLoader.class).to(RawImageLoader.class);
	}
}
