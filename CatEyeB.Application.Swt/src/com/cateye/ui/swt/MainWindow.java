package com.cateye.ui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.cateye.core.stage.IStage;
import com.cateye.core.stage.StageFactory;
import com.cateye.stageoperations.brightness.BrightnessStageOperation;
import com.cateye.stageoperations.limiter.LimiterStageOperation;
import com.google.inject.Inject;

public class MainWindow
{
	protected Shell shell;
	protected Button button;
	private final StageFactory stageFactory;
	
	@Inject
	public MainWindow(StageFactory stageFactory)
	{
		this.stageFactory = stageFactory;
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
		BrightnessStageOperation brightnessStageOperation = new BrightnessStageOperation();
		brightnessStageOperation.setBrightness(100);
		LimiterStageOperation limiterStageOperation = new LimiterStageOperation();
		limiterStageOperation.setPower(1.9);
		
		IStage stage = stageFactory.create();
		stage.addStageOperation(brightnessStageOperation);
		stage.addStageOperation(limiterStageOperation);
		
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application 2");
		
		FillLayout layout = new FillLayout();
		layout.type = SWT.VERTICAL;
		shell.setLayout(layout);
		
		stage.loadImage("..//..//data//test//IMG_5697.CR2");
		stage.processImage();
		
		final PreciseImageViewer imageViewer = new PreciseImageViewer(shell);
		imageViewer.setSize(400, 250);
		
		//imageViewer.setBitmap(image.getDescription().getThumbnail());
		PreciseBitmapsVernissage vern = new PreciseBitmapsVernissage(2, 2);
		for (int j = 0; j < 2; j++)
		for (int i = 0; i < 2; i++)
		{
			vern.setUpdated(i, j, true);
			vern.setBitmap(i, j, stage.getOriginalBitmap());
		}
		
		vern.setBitmap(0, 1, stage.getBitmap());
		
		vern.setUpdated(0, 0, false);
		vern.setCaption(1, 0, "This is the caption for (1, 0) picture"); 
		
		imageViewer.setVernissage(vern);
		//imageViewer.setBitmap(image.getBitmap());
	}
}
