package com.cateye.ui.swt;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import com.cateye.core.IPreciseBitmap;
import com.cateye.core.Image;
import com.cateye.core.native_.LibraryLoader;

public class ImageViewer extends Canvas
{
	private int nativeHandle;
	private Image image;
	
	public ImageViewer(Composite parent, int style)
	{
		super(parent, style);
		
		addPaintListener(new PaintListener()
		{
			@Override
			public void paintControl(PaintEvent e)
			{
				Rectangle rect = ImageViewer.this.getClientArea();
				IPreciseBitmap bitmap = ImageViewer.this.image.getBitmap();
				
				if (bitmap != null)
					drawImage(e.gc.handle, bitmap, 0, 0, rect.width, rect.height);
			}
		});
	}
	
	public void setImage(Image image)
	{
		this.image = image;
	}
	
	static final native void drawImage(int handle, IPreciseBitmap bitmap, int x, int y, int width, int height);
	
	static
	{
		LibraryLoader.attach("ImageViewer.SwtWidgets");
	}
}
