package com.cateye.ui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import com.cateye.core.IPreciseBitmap;
import com.cateye.core.IPreviewBitmap;
import com.cateye.core.jni.LibraryLoader;

public class PreciseImageViewer extends Canvas
{
	private PreciseBitmapsVernissage vernissage;
	
	public PreciseImageViewer(Composite parent)
	{
		super(parent, SWT.NO_BACKGROUND);
		
		addPaintListener(new PaintListener()
		{
			@Override
			public void paintControl(PaintEvent e)
			{
				Rectangle rect = getClientArea();

				// Fill self in black
				if (vernissage != null)
				{
					int frame_w = rect.width / vernissage.getColumns();
					int frame_h = rect.height / vernissage.getRows();
					
					for (int i = 0; i < vernissage.getColumns(); i++)
					for (int j = 0; j < vernissage.getRows(); j++)
					{
						if (vernissage.getUpdated(i, j))
						{
							IPreciseBitmap pbmp = vernissage.getBitmap(i, j);
							
							if (pbmp != null)
							{
								drawImage(e.gc.handle, pbmp, frame_w * i, frame_h * j, 
										rect.width / vernissage.getColumns() + 1, 
										rect.height / vernissage.getRows() + 1, 0, 0, 2);	// TODO: Put position here
							}
						}
						else
						{
							e.gc.setBackground(new Color(e.gc.getDevice(), 0, 0, 0));
							e.gc.fillRectangle(frame_w * i, frame_h * j, rect.width / vernissage.getColumns(), rect.height / vernissage.getRows());
						}
					}
				}
				/*
				drawImage(e.gc.handle, bitmap, 0, 0, rect.width / 2, rect.height, 0, 0, 2);
				drawImage(e.gc.handle, bitmap, rect.width / 2, 0, rect.width / 2, rect.height, 0, 0, 2);*/
			}
		});
	}
	
	public void setVernissage(PreciseBitmapsVernissage vernissage)
	{
		this.vernissage = vernissage;
	}
	
	static final native void drawImage(int handle, IPreciseBitmap bitmap, 
			int frame_left, int frame_top, int frame_width, int frame_height, 
			int offset_left, int offset_top, float scale_out);
	
	static
	{
		LibraryLoader.attach("ImageViewer.SwtWidgets");
	}
}
