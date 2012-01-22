package com.cateye.ui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import com.cateye.core.IPreviewBitmap;
import com.cateye.core.native_.LibraryLoader;

public class PreviewImageViewer extends Canvas
{
	private IPreviewBitmap bitmap;
	
	public PreviewImageViewer(Composite parent)
	{
		super(parent, SWT.NO_BACKGROUND);
		
		addPaintListener(new PaintListener()
		{
			@Override
			public void paintControl(PaintEvent e)
			{
				Rectangle rect = getClientArea();
				if (bitmap != null)
				{
					drawImage(e.gc.handle, bitmap, 0, 0, rect.width, rect.height, 1);
				}
				else
				{
					// Fill self in black
					e.gc.setBackground(new Color(e.gc.getDevice(), 0, 0, 0));
					e.gc.fillRectangle(rect);
				}
			}
		});
	}
	
	public void setBitmap(IPreviewBitmap bitmap)
	{
		this.bitmap = bitmap;
	}
	
	static final native void drawImage(int handle, IPreviewBitmap bitmap, int crop_left, int crop_top, int crop_width, int crop_height, float scale_out);
	
	static
	{
		LibraryLoader.attach("ImageViewer.SwtWidgets");
	}
}
