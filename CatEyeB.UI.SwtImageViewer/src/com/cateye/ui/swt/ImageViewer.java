package com.cateye.ui.swt;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import com.cateye.core.native_.LibraryLoader;

public class ImageViewer extends Canvas
{
	private int nativeHandle;
	
	public ImageViewer(Composite parent, int style)
	{
		super(parent, style);
		
		/*PaintListener listener = new PaintListener()
		{
			@Override
			public void paintControl(PaintEvent e)
			{
	            Rectangle clientArea = getClientArea();
	            e.gc.drawLine(0,0,clientArea.width,clientArea.height); 
	            //drawControl(handle, pe.x, pe.y, pe.width, pe.height);
			}
		};
		
		this.addPaintListener(listener);*/
	}
	
	static final native void drawControl(int handle, int x, int y, int width, int height);
	
	static
	{
		LibraryLoader.attach("ImageViewer.SwtWidgets");
	}
}
