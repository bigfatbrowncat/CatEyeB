package com.cateye.ui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.cateye.core.IPropertyChangedListener;
import com.cateye.core.stage.IStage;
import com.cateye.core.stage.StageFactory;
import com.cateye.stageoperations.hsb.HSBStageOperation;
import com.cateye.stageoperations.limiter.LimiterStageOperation;
import com.cateye.stageoperations.rgb.RGBStageOperation;
import com.cateye.stageoperations.rgb.ui.swt.RGBStageOperationWidget;
import com.google.inject.Inject;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.layout.GridLayout;

public class MainWindow
{
	protected Shell shell;
	protected Button button;
	private final StageFactory stageFactory;
	
	private PreciseBitmapsVernissage vern = new PreciseBitmapsVernissage(2, 1);
	private PreciseImageViewer imageViewer;
	private IStage stage;
	
	private RGBStageOperation rgbStageOperation;
	private RGBStageOperationWidget rgbStageOperationWidget;
	private Composite centerComposite;

	
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
		stage = createStage();

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
	
	private IStage createStage()
	{
		HSBStageOperation hsbStageOperation = new HSBStageOperation();
		hsbStageOperation.setSaturation(0.9);
		hsbStageOperation.setHue(0);
		hsbStageOperation.setBrightness(7);
		
		rgbStageOperation = new RGBStageOperation();
		rgbStageOperation.setB(2);
		rgbStageOperation.setG(1.5);
		
		LimiterStageOperation limiterStageOperation = new LimiterStageOperation();
		limiterStageOperation.setPower(10);
		
		IStage stage = stageFactory.create();
//		stage.addStageOperation(hsbStageOperation);
		stage.addStageOperation(rgbStageOperation);
		stage.addStageOperation(limiterStageOperation);
		
		stage.loadImage("..//..//data//test//IMG_5697.CR2");
		stage.processImage();
		
		return stage;
	}

	
	private void createImageViewer(IStage stage, Composite parent)
	{
		centerComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		imageViewer = new PreciseImageViewer(parent);

		vern.setUpdated(0, 0, true);
		vern.setBitmap(0, 0, stage.getOriginalBitmap());
		
		vern.setUpdated(1, 0, true);
		vern.setBitmap(1, 0, stage.getBitmap());
		
		vern.setCaption(1, 0, "This is the caption for (1, 0) picture"); 
		
		imageViewer.setVernissage(vern);
		imageViewer.setLayout(new FillLayout(SWT.HORIZONTAL));
	}
	
	/**	 * Create contents of the window.
	 */
	protected void createContents()
	{
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("CatEyeB");
		shell.setLayout(new BorderLayout(0, 0));
		
		Composite rightComposite = new Composite(shell, SWT.NONE);
		rightComposite.setLayoutData(BorderLayout.EAST);
		rightComposite.setLayout(new GridLayout(1, false));
		
		centerComposite = new Composite(shell, SWT.NONE);
		centerComposite.setLayoutData(BorderLayout.CENTER);
		
		createImageViewer(stage, centerComposite);
		rgbStageOperationWidget = new RGBStageOperationWidget(rightComposite, SWT.NONE);
		rgbStageOperationWidget.setRgbStageOperation(rgbStageOperation);
		rgbStageOperation.addOnPropertyChangedListener(new IPropertyChangedListener() {
			
			@Override
			public void invoke(Object sender, String propertyName, Object newValue) 
			{
				stage.processImage();
				vern.setBitmap(1, 0, stage.getBitmap());
				imageViewer.redraw();
			}
		});
	}
}
