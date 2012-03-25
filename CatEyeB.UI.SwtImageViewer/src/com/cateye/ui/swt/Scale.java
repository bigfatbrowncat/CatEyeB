package com.cateye.ui.swt;

import java.io.IOException;

import org.eclipse.swt.SWT;

import javax.xml.parsers.*;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
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
	static class SkinElement
	{
		private Image skin;
		private Integer left, top, width, height, baseline;
		public static SkinElement loadFromNode(Node node, int height, int baseline)
		{
			SkinElement res = new SkinElement();
			res.left = Integer.parseInt(node.getAttributes().getNamedItem("left").getNodeValue());
			res.top = Integer.parseInt(node.getAttributes().getNamedItem("top").getNodeValue());
			res.width = Integer.parseInt(node.getAttributes().getNamedItem("width").getNodeValue());
			res.height = height;
			res.baseline = baseline - res.top;
			return res;
		}
		public void loadSkin(Image texture)
		{
			skin = Scale.cutSkinPart(texture, left, top, width, height);
		}
		public void draw(GC gc, int x, int y)
		{
			gc.drawImage(skin, x, y - baseline);
		}
		public void draw(GC gc, int x, int y, int targetWidth, int targetHeight)
		{
			gc.drawImage(skin, 0, 0, width, height, x, y - baseline, targetWidth, targetHeight);
		}
		public Integer getLeft() 
		{
			return left;
		}
		public Integer getTop() 
		{
			return top;
		}
		public Integer getWidth() 
		{
			return width;
		}
		public Integer getHeight() 
		{
			return height;
		}

	}
	
	static class ScaleSkinElement
	{
		public Integer height, baseline;
		public SkinElement leftSide, rangeZone, rightSide;
		public static ScaleSkinElement loadFromNode(Node node)
		{
			ScaleSkinElement res = new ScaleSkinElement();
			
			res.height = Integer.parseInt(node.getAttributes().getNamedItem("height").getNodeValue());
			res.baseline = Integer.parseInt(node.getAttributes().getNamedItem("baseline").getNodeValue());
			
			for (int i2 = 0; i2 < node.getChildNodes().getLength(); i2++)
			{
				Node baseItem = node.getChildNodes().item(i2);
				if (baseItem.getNodeName().equals("leftside"))
				{
					res.leftSide = SkinElement.loadFromNode(baseItem, res.height, res.baseline);
				}
				else if (baseItem.getNodeName().equals("rangezone"))
				{
					res.rangeZone = SkinElement.loadFromNode(baseItem, res.height, res.baseline);
				}
				else if (baseItem.getNodeName().equals("rightside"))
				{
					res.rightSide = SkinElement.loadFromNode(baseItem, res.height, res.baseline);
				}
			}
			
			return res;
		}
		
		public void loadSkin(Image texture)
		{
			leftSide.loadSkin(texture);
			rangeZone.loadSkin(texture);
			rightSide.loadSkin(texture);
		}

		public void draw(GC gc, int x, int y, int width)
		{
			//System.out.println(gc.getAdvanced());
			leftSide.draw(gc, x, y);
			rangeZone.draw(gc, x + leftSide.getWidth(), y, width - leftSide.getWidth() - rightSide.getWidth(), height);
			rightSide.draw(gc, x + width - rightSide.getWidth(), y);
		}
		
	}

	private String textureFileName = null;
	private Integer height = null, markerWidth = 20;
	public double value = 0.5;
	private Image skin;
	private int markerDragX;
	private boolean markerDragging; 
	
	private ScaleSkinElement baseSkinElement, markerSkinElement;
	
	private void makeSkin(Device device, int foreR, int foreG, int foreB, int backR, int backG, int backB)
	{
		Image img = new Image(device, textureFileName);
		
		ImageData textureMaskData = img.getImageData();
		ImageData resData = new ImageData(textureMaskData.width, textureMaskData.height, textureMaskData.depth, textureMaskData.palette);
		
		resData.alphaData = new byte[resData.width * resData.height];

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
	
	private static Image cutSkinPart(Image src, int x, int y, int w, int h)
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
	
	private static void pasteSkinPart(Image part, Image dest, int x, int y)
	{
		ImageData partImageData = part.getImageData();
		int partb = partImageData.depth / 8;
		ImageData destImageData = dest.getImageData();
		int destb = destImageData.depth / 8;
		int w = destImageData.width, h = destImageData.height;
		
		for (int i = 0; i < partImageData.width; i++)
		for (int j = 0; j < partImageData.height; j++)
		{
			int partpos = (j * w + i);
			int destpos = ((j + y) * destImageData.width + (i + x)) * destb;
			destImageData.data[destpos * destb] = destImageData.data[partpos * partb];
			destImageData.data[destpos * destb + 1] = destImageData.data[partpos * partb + 1];
			destImageData.data[destpos * destb + 2] = destImageData.data[partpos * partb + 2];
			destImageData.alphaData[destpos] = destImageData.alphaData[partpos];
		}
	}
	/*
	private static Image scaleSkinPart(Image src, int newWidth, int newHeight)
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
	*/
	public Scale(Composite parent) throws ParserConfigurationException, SAXException, IOException
	{
		super(parent, SWT.NO_BACKGROUND | SWT.DOUBLE_BUFFERED);

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
			height = Integer.parseInt(scaleElement.getAttributes().getNamedItem("height").getNodeValue());
			
			for (int i = 0; i < scaleElement.getChildNodes().getLength(); i++)
			{
				Node scaleItem = scaleElement.getChildNodes().item(i);
				if (scaleItem.getNodeName().equals("mask"))
				{
					textureFileName = scaleItem.getAttributes().getNamedItem("name").getNodeValue();
				}
				else if (scaleItem.getNodeName().equals("base"))
				{
					baseSkinElement = ScaleSkinElement.loadFromNode(scaleItem);
				}
				else if (scaleItem.getNodeName().equals("marker"))
				{
					markerSkinElement = ScaleSkinElement.loadFromNode(scaleItem);
				}
			}
		}
		
		makeSkin(getDisplay(), fore_red, fore_green, fore_blue, back_red, back_green, back_blue);
		baseSkinElement.loadSkin(skin);
		markerSkinElement.loadSkin(skin);
		
		addPaintListener(new PaintListener()
		{
			@Override
			public void paintControl(PaintEvent e)
			{
				int scaleWidth = Scale.this.getSize().x;
				
				Color foreColor = getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND); 
				e.gc.setForeground(foreColor);
				e.gc.fillRectangle(0, 0, Scale.this.getSize().x, Scale.this.getSize().y);
				
				baseSkinElement.draw(e.gc, 0, height / 2, scaleWidth);
				int markerX = (int) (value * (scaleWidth - markerWidth));
				markerSkinElement.draw(e.gc, markerX, height / 2, markerWidth);
				
			}
		});

		addMouseMoveListener(new MouseMoveListener() {
			
			@Override
			public void mouseMove(MouseEvent arg0) 
			{
				if (markerDragging)
				{
					int scaleWidth = Scale.this.getSize().x;

					int markerX = arg0.x - markerDragX;
					double newValue = (double)markerX / (scaleWidth - markerWidth);
					if (newValue < 0) newValue = 0;
					if (newValue > 1) newValue = 1;
					
					if (Math.abs(value - newValue) >= Double.MIN_NORMAL)
					{
						value = newValue;
						Scale.this.redraw();
					}
				}
			}
		});
		
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				if (markerDragging)
				{
					markerDragging = false;
				}
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) 
			{
				int scaleWidth = Scale.this.getSize().x;
				int markerX = (int) (value * (scaleWidth - markerWidth));
				
				if (arg0.x > markerX && arg0.x < markerX + markerWidth &&
				    arg0.y > height / 2 - markerSkinElement.height / 2 &&
				    arg0.y < height / 2 + markerSkinElement.height / 2)
				{
					markerDragX = arg0.x - markerX;
					markerDragging = true;
				}
				
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
