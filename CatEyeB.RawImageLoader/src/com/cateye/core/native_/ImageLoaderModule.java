package com.cateye.core.native_;

import com.cateye.core.IImageLoader;
import com.google.inject.AbstractModule;

public class ImageLoaderModule extends AbstractModule
{
	@Override
	protected void configure()
	{
		bind(IImageLoader.class).to(RawImageLoader.class);
	}
}
