package com.cateye.ui.swt;

import java.io.IOException;

import org.eclipse.swt.SWT;

import javax.xml.parsers.*;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class Scale extends Canvas
{
	String textureFileName = null;
	Integer baseHeight = null;
	Integer leftSideLeft = null, leftSideTop = null, leftSideWidth = null;
	Integer rangeZoneLeft = null, rangeZoneTop = null, rangeZoneWidth = null;
	Integer rightSideLeft = null, rightSideTop = null, rightSideWidth = null;
	
	private Image skin;
	private Image leftSide, rightSide, rangeZone;
	
	private void makeSkin(Device device, int foreR, int foreG, int foreB, int backR, int backG, int backB)
	{
		Image img = new Image(device, textureFileName);
		
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
	
	private Image cutSkinPart(Image src, int x, int y, int w, int h)
	{
		ImageData srcImageData = src.getImageData();
		ImageData resImageData = new ImageData(w, h, srcImageData.depth, srcImageData.palette);
		resImageData.alphaData = new byte[w * h];
		int b = srcImageData.depth / 8;
		
		for (int i = 0; i < w; i++)
		for (int j = 0; j < h; j++)
		{
			resImageData.data[(j * w + i) * b] = srcImageData.data[((j + y) * srcImageData.width + (i + x)) * b];
			resImageData.data[(j * w + i) * b + 1] = srcImageData.data[((j + y) * srcImageData.width + (i + x)) * b + 1];
			resImageData.data[(j * w + i) * b + 2] = srcImageData.data[((j + y) * srcImageData.width + (i + x)) * b + 2];
			resImageData.alphaData[j * w + i] = srcImageData.alphaData[(j + y) * srcImageData.width + (i + x)];
		}
		
		return new Image(src.getDevice(), resImageData);
	}
	
	private Image scaleSkinPart(Image src, int newWidth, int newHeight)
	{
		ImageData srcImageData = src.getImageData();
		ImageData resImageData = new ImageData(newWidth, newWidth, srcImageData.depth, srcImageData.palette);
		resImageData.alphaData = new byte[newWidth * newWidth];
		int b = srcImageData.depth / 8;
		
		double kw = (double)newWidth / srcImageData.width; 
		double kh = (double)newHeight / srcImageData.height; 
		
		for (int i = 0; i < newWidth; i++)
		for (int j = 0; j < newHeight; j++)
		{
			resImageData.data[(j * newWidth + i) * b] = srcImageData.data[(int)((j / kh) * srcImageData.width + (i / kw)) * b];
			resImageData.data[(j * newWidth + i) * b + 1] = srcImageData.data[(int)((j / kh) * srcImageData.width + (i / kw)) * b + 1];
			resImageData.data[(j * newWidth + i) * b + 2] = srcImageData.data[(int)((j / kh) * srcImageData.width + (i / kw)) * b + 2];
			resImageData.alphaData[j * newWidth + i] = srcImageData.alphaData[(int)((j / kh) * srcImageData.width + (i / kw))];
		}
		
		return new Image(src.getDevice(), resImageData);
	}
	
	public Scale(Composite parent) throws ParserConfigurationException, SAXException, IOException
	{
		super(parent, /*SWT.NO_BACKGROUND*/ SWT.NONE);

		Color foreColor = getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND); 
		
		int fore_red = foreColor.getRed();
		int fore_green = foreColor.getGreen();
		int fore_blue = foreColor.getBlue();

		int back_red = (foreColor.getRed() + 255) / 2;
		int back_green = (foreColor.getGreen() + 255) / 2;
		int back_blue = (foreColor.getBlue() + 255) / 2;
		
		// Reading XML skin description
		
		DocumentBuilderFactory xmlParserFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder xmlParser = xmlParserFactory.newDocumentBuilder();
		Document d = xmlParser.parse("../../res/scale_texture.xml");
		
		Element scaleElement = d.getDocumentElement();
		if (scaleElement.getNodeName().equals("scale"))
		{
			for (int i = 0; i < scaleElement.getChildNodes().getLength(); i++)
			{
				Node scaleItem = scaleElement.getChildNodes().item(i);
				if (scaleItem.getNodeName().equals("mask"))
				{
					textureFileName = scaleItem.getAttributes().getNamedItem("name").getNodeValue();
				}
				else if (scaleItem.getNodeName().equals("base"))
				{
					baseHeight = Integer.parseInt(scaleItem.getAttributes().getNamedItem("height").getNodeValue());
					
					for (int i2 = 0; i2 < scaleItem.getChildNodes().getLength(); i2++)
					{
						Node baseItem = scaleItem.getChildNodes().item(i2);
						if (baseItem.getNodeName().equals("leftside"))
						{
							leftSideLeft = Integer.parseInt(baseItem.getAttributes().getNamedItem("left").getNodeValue());
							leftSideTop = Integer.parseInt(baseItem.getAttributes().getNamedItem("top").getNodeValue());
							leftSideWidth = Integer.parseInt(baseItem.getAttributes().getNamedItem("width").getNodeValue());
						}
						else if (baseItem.getNodeName().equals("rangezone"))
						{
							rangeZoneLeft = Integer.parseInt(baseItem.getAttributes().getNamedItem("left").getNodeValue());
							rangeZoneTop = Integer.parseInt(baseItem.getAttributes().getNamedItem("top").getNodeValue());
							rangeZoneWidth = Integer.parseInt(baseItem.getAttributes().getNamedItem("width").getNodeValue());
						}
						else if (baseItem.getNodeName().equals("rightside"))
						{
							rightSideLeft = Integer.parseInt(baseItem.getAttributes().getNamedItem("left").getNodeValue());
							rightSideTop = Integer.parseInt(baseItem.getAttributes().getNamedItem("top").getNodeValue());
							rightSideWidth = Integer.parseInt(baseItem.getAttributes().getNamedItem("width").getNodeValue());
						}
					}
				}
					
			}
		}
		
		makeSkin(getDisplay(), fore_red, fore_green, fore_blue, back_red, back_green, back_blue);
		leftSide = cutSkinPart(skin, leftSideLeft, leftSideTop, leftSideWidth, baseHeight);
		rightSide = cutSkinPart(skin, rightSideLeft, rightSideTop, rightSideWidth, baseHeight);
		rangeZone = cutSkinPart(skin, rangeZoneLeft, rangeZoneTop, rangeZoneWidth, baseHeight);

		addPaintListener(new PaintListener()
		{
			@Override
			public void paintControl(PaintEvent e)
			{
				int scaleWidth = Scale.this.getSize().x;
				
				int rangeZoneCurrentWidth = scaleWidth - leftSideWidth - rightSideWidth;
				Image rangeZoneCurrent = scaleSkinPart(rangeZone, rangeZoneCurrentWidth, baseHeight);
				
				e.gc.drawImage(leftSide, 0, 0);
				e.gc.drawImage(rangeZoneCurrent, leftSideWidth, 0);
				e.gc.drawImage(rightSide, leftSideWidth + rangeZoneCurrentWidth, 0);
				
				//draw(e.gc.handle);
			}
		});
	}
}
