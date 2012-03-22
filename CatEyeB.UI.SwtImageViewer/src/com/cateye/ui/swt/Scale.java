package com.cateye.ui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class Scale extends Canvas
{
	private Image skin;
	
	private void makeSkin(Device device, String filename, int foreR, int foreG, int foreB, int backR, int backG, int backB)
	{
		Image img = new Image(device, filename);
		
		ImageData textureMaskData = img.getImageData();
		ImageData resData = new ImageData(textureMaskData.width, textureMaskData.height, textureMaskData.depth, textureMaskData.palette);
		
		resData.alphaData = new byte[resData.width * resData.height];
		
		System.out.println((byte)132);
		for (int i = 0; i < textureMaskData.width * textureMaskData.height; i++)
		{
			int red_mask = (textureMaskData.data[i * textureMaskData.depth / 8 + 2] + 256) % 256;				// frame bump
			int green_mask = (textureMaskData.data[i * textureMaskData.depth / 8 + 1] + 256) % 256;		// inner picture/color
			int blue_mask = (textureMaskData.data[i * textureMaskData.depth / 8] + 256) % 256;
			
			int r = Math.min((foreR * blue_mask + backR * red_mask) / (blue_mask + red_mask + 1), green_mask);
			int g = Math.min((foreG * blue_mask + backG * red_mask) / (blue_mask + red_mask + 1), green_mask);
			int b = Math.min((foreB * blue_mask + backB * red_mask) / (blue_mask + red_mask + 1), green_mask);

			resData.alphaData[i] = (byte)(Math.max(Math.max(255 - green_mask, red_mask), blue_mask));
			resData.data[i * textureMaskData.depth / 8 + 2] = (byte)r;
			resData.data[i * textureMaskData.depth / 8 + 1] = (byte)g;
			resData.data[i * textureMaskData.depth / 8 + 0] = (byte)b;
		}
	
		skin = new Image(device, resData);
				
	}
	
	public Scale(Composite parent)
	{
		super(parent, SWT.NO_BACKGROUND);

		Color foreColor = getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND); 
		
		int fore_red = foreColor.getRed();
		int fore_green = foreColor.getGreen();
		int fore_blue = foreColor.getBlue();

		int back_red = (foreColor.getRed() + 255) / 2;
		int back_green = (foreColor.getGreen() + 255) / 2;
		int back_blue = (foreColor.getBlue() + 255) / 2;
		
		System.out.println(fore_red);
		
		makeSkin(getDisplay(), "../../res/scale_texturemask.png", fore_red, fore_green, fore_blue, back_red, back_green, back_blue);
		addPaintListener(new PaintListener()
		{
			@Override
			public void paintControl(PaintEvent e)
			{

				e.gc.drawImage(skin, -30, 0);
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
