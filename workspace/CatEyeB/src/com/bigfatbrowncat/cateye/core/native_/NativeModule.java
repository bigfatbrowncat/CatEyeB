package com.bigfatbrowncat.cateye.core.native_;

import com.bigfatbrowncat.cateye.core.IImageLoader;
import com.google.inject.AbstractModule;

public class NativeModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(IImageLoader.class).to(RawImageLoader.class);
	}
}
