package com.cateye.ui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class Scale extends Canvas
{
	public Scale(Composite parent)
	{
		super(parent, SWT.NO_BACKGROUND);
		
		addPaintListener(new PaintListener()
		{
			@Override
			public void paintControl(PaintEvent e)
			{
				org.eclipse.swt.graphics.Image img = new Image(e.display, "../../scale.png");
				org.eclipse.swt.graphics.Image img_alpha = new Image(e.display, "../../scale_mask.png");
				
				ImageData idata = img.getImageData();
				ImageData mask_id = img_alpha.getImageData();
				
				idata.alphaData = new byte[idata.width * idata.height];
				
				for (int i = 0; i < idata.alphaData.length; i++)
				{
					byte mask = (byte)(255 - mask_id.data[i * mask_id.depth / 8]);
					
					idata.alphaData[i] = (byte) mask;
				}
			
				img = new Image(e.display, idata);
				
				e.gc.drawImage(img, 0, 0);
				//draw(e.gc.handle);
			}
		});
	}
/*	
	static final native void draw(int handle);
	
	static
	{
		LibraryLoader.attach("ImageViewer.SwtWidgets");
	}*/
}
