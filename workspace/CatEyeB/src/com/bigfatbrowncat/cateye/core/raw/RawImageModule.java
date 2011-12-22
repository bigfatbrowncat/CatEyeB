package com.bigfatbrowncat.cateye.core.raw;

import com.bigfatbrowncat.cateye.core.IImageLoader;
import com.google.inject.AbstractModule;

public class RawImageModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(IImageLoader.class).to(RawImageLoader.class);
	}
}
