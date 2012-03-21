package com.cateye.core.jni;

import com.cateye.core.IImageSaver;
import com.google.inject.AbstractModule;

public class ImageSaverModule extends AbstractModule
{
	@Override
	protected void configure()
	{
		bind(IImageSaver.class).to(PpmImageSaver.class).asEagerSingleton();
	}
}
