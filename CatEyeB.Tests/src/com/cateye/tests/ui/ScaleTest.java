package com.cateye.tests.ui;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.xml.sax.SAXException;

import com.cateye.ui.swt.Scale;

public class ScaleTest 
{
	private Shell shell;
	private Scale scale;
	
	public ScaleTest() throws ParserConfigurationException, SAXException, IOException
	{
		Display display = Display.getDefault();
		
		shell = new Shell();
		shell.setText("Scale test window");
		
		FillLayout fl = new FillLayout();
		shell.setLayout(fl);
		
		scale = new Scale(shell);

		shell.open();
		shell.layout();
		
		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
	
	public static void main(String[] args)
	{
		try {
			new ScaleTest();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
