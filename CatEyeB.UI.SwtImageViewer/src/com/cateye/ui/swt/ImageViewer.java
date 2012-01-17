package com.cateye.ui.swt;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import com.cateye.core.IPreciseBitmap;
import com.cateye.core.native_.LibraryLoader;

public class ImageViewer extends Canvas
{
	private int nativeHandle;
	final IPreciseBitmap bmp;
	
	public ImageViewer(Composite parent, int style)
	{
		super(parent, style);
		bmp = null;
		
		addPaintListener(new PaintListener()
		{
			@Override
			public void paintControl(PaintEvent e)
			{
				Rectangle rect = ImageViewer.this.getClientArea();
				drawImage(e.gc.handle, bmp, 0, 0, rect.width, rect.height);
			}
		});
	}
	
	static final native void drawImage(int handle, IPreciseBitmap bitmap, int x, int y, int width, int height);
	
	static
	{
		LibraryLoader.attach("ImageViewer.SwtWidgets");
	}
}
