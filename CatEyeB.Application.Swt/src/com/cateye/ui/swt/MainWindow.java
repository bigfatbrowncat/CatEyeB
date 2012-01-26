package com.cateye.ui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.cateye.core.stage.IStage;
import com.cateye.core.stage.StageFactory;
import com.cateye.stageoperations.hsb.HSBStageOperation;
import com.cateye.stageoperations.limiter.LimiterStageOperation;
import com.cateye.stageoperations.rgb.RGBStageOperation;
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
		HSBStageOperation hsbStageOperation = new HSBStageOperation();
		hsbStageOperation.setSaturation(0.9);
		hsbStageOperation.setHue(0);
		hsbStageOperation.setBrightness(15);
		
		RGBStageOperation rgbStageOperation = new RGBStageOperation();
		rgbStageOperation.setB(2);
		rgbStageOperation.setG(1.5);
		
		LimiterStageOperation limiterStageOperation = new LimiterStageOperation();
		limiterStageOperation.setPower(3);
		
		IStage stage = stageFactory.create();
		stage.addStageOperation(hsbStageOperation);
		stage.addStageOperation(rgbStageOperation);
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
		
		PreciseBitmapsVernissage vern = new PreciseBitmapsVernissage(2, 1);

		vern.setUpdated(0, 0, true);
		vern.setBitmap(0, 0, stage.getOriginalBitmap());
		
		vern.setUpdated(1, 0, true);
		vern.setBitmap(1, 0, stage.getBitmap());
		
		vern.setCaption(1, 0, "This is the caption for (1, 0) picture"); 
		
		imageViewer.setVernissage(vern);

	}
}
