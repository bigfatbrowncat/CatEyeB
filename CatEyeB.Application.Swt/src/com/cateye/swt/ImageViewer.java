package com.cateye.swt;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ImageViewer extends Canvas
{
	private int nativeHandle;
	
	public ImageViewer(Composite parent, int style)
	{
		super(parent, style);
		Label label = new Label(parent, style);
		label.setText("Hello");
		
		this.addPaintListener(new PaintListener()
		{
			@Override
			public void paintControl(PaintEvent arg0)
			{
				Rectangle clientArea = getClientArea();
				arg0.gc.drawString("Normal", 5, 5);
				GC gc = arg0.gc;
				gc.drawLine(0, 0, clientArea.width, clientArea.height);
				//gc.dispose();
			}
		});
	}
}
