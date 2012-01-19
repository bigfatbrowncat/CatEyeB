package com.cateye.swt;

import com.cateye.core.native_.BrightnessStageOperationModule;
import com.cateye.core.native_.ImageLoaderModule;
import com.cateye.core.native_.ImageSaverModule;
import com.cateye.core.stage.StageModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;

public class ApplicationLauncher
{
	protected static Injector injector;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			initIoC();
			MainWindow window = injector.getInstance(MainWindow.class);
			window.open();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		initIoC();
	}
	
	private static void initIoC()
	{
		injector = Guice.createInjector(Modules.combine(
				new ImageLoaderModule(),
				new ImageSaverModule(),
				new StageModule(),
				new BrightnessStageOperationModule()
			)
		);
	}	
}
