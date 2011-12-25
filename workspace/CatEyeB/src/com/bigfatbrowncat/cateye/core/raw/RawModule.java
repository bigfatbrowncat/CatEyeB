package com.bigfatbrowncat.cateye.core.raw;

import com.bigfatbrowncat.cateye.core.IImageLoader;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.sun.jna.Native;

public class RawModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(IImageLoader.class).to(RawImageLoader.class);
	}
	
	@Provides @Singleton
	IRawLoaderNative provideRawLoaderNative()
	{
		String libraryPath = new java.io.File("..\\ssrl\\bin\\Debug\\ssrl.dll").getAbsolutePath();
		
		return (IRawLoaderNative)Native.loadLibrary(libraryPath, IRawLoaderNative.class);
	}
	
	@Provides @Singleton
	IBitmapsLibraryNative provideBitmapsLibraryNative()
	{
		String libraryPath = new java.io.File("..\\bitmaps\\bin\\Debug\\bitmaps.dll").getAbsolutePath();
		
		return (IBitmapsLibraryNative)Native.loadLibrary(libraryPath, IBitmapsLibraryNative.class);
	}
}
