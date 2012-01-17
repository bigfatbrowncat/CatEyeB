package com.cateye.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.cateye.ui.swt.ImageViewer;

public class MainWindow
{
	
	protected Shell shell;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			MainWindow window = new MainWindow();
			window.open();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Open the window.
	 */
	public void open()
	{
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
	}
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents()
	{
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application 2");
		
		FillLayout fillLayout = new FillLayout();
		fillLayout.type = SWT.VERTICAL;
		shell.setLayout(fillLayout);
		
		final ImageViewer imageViewer = new ImageViewer(shell, SWT.NO_BACKGROUND);
		imageViewer.setSize(400, 250);
	}
}
