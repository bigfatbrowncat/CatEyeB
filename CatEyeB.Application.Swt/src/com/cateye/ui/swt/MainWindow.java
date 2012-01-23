package com.cateye.ui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.internal.ImageList;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.cateye.core.IImageLoader;
import com.cateye.core.Image;
import com.cateye.ui.swt.PreviewImageViewer;
import com.google.inject.Inject;
import com.google.inject.Stage;

public class MainWindow
{
	protected Shell shell;
	private final IImageLoader imageLoader;
	
	@Inject
	public MainWindow(IImageLoader imageLoader)
	{
		this.imageLoader = imageLoader;
	}

	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
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
				display.sleep();
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
		
		final PreciseImageViewer imageViewer = new PreciseImageViewer(shell);
		imageViewer.setSize(400, 250);
		
		Image image = imageLoader.loadImageFromFile("..//..//data//test//IMG_5697.CR2");
		//imageViewer.setBitmap(image.getDescription().getThumbnail());
		PreciseBitmapsVernissage vern = new PreciseBitmapsVernissage(2, 2);
		for (int j = 0; j < 2; j++)
		for (int i = 0; i < 2; i++)
		{
			vern.setUpdated(i, j, true);
			vern.setBitmap(i, j, image.getBitmap());
		}
		vern.setUpdated(0, 0, false);
		vern.setCaption(1, 0, "This is the caption for (1, 0) picture"); 
		
		imageViewer.setVernissage(vern);
		//imageViewer.setBitmap(image.getBitmap());
	}
}
