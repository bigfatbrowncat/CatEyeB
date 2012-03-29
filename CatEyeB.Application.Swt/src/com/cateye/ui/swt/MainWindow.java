package com.cateye.ui.swt;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.cateye.core.IProgressListener;
import com.cateye.core.IPropertyChangedListener;
import com.cateye.core.stage.IStage;
import com.cateye.core.stage.StageFactory;
import com.cateye.stageoperations.compressor.CompressorStageOperation;
import com.cateye.stageoperations.compressor.ui.swt.CompressorStageOperationWidget;
import com.cateye.stageoperations.downsample.DownsampleStageOperation;
import com.cateye.stageoperations.hsb.HSBStageOperation;
import com.cateye.stageoperations.hsb.ui.swt.HSBStageOperationWidget;
import com.cateye.stageoperations.limiter.LimiterStageOperation;
import com.cateye.stageoperations.rgb.RGBStageOperation;
import com.cateye.stageoperations.rgb.ui.swt.RGBStageOperationWidget;
import com.google.inject.Inject;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.xml.sax.SAXException;

public class MainWindow
{
	protected Shell shell;
	protected Button loadBtn, processBtn;
	private final StageFactory stageFactory;
	
	private PreciseBitmapsVernissage vern = new PreciseBitmapsVernissage(1, 1);
	private PreciseImageViewer imageViewer;
	private IStage stage;
	
	private RGBStageOperation rgbStageOperation;
	private HSBStageOperation hsbStageOperation;
	private CompressorStageOperation compressorStageOperation;
	private DownsampleStageOperation downsampleStageOperation;

	private RGBStageOperationWidget rgbStageOperationWidget;
	private HSBStageOperationWidget hsbStageOperationWidget;
	private CompressorStageOperationWidget compressorStageOperationWidget;
	
	IPropertyChangedListener stageOperationPropertyChanged = new IPropertyChangedListener() 
	{
		
		@Override
		public void invoke(Object sender, String propertyName, Object newValue) 
		{
		}
	};
	
	@Inject
	public MainWindow(StageFactory stageFactory)
	{
		this.stageFactory = stageFactory;
	}

	/**
	 * Open the window.
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @wbp.parser.entryPoint
	 */
	public void open() throws ParserConfigurationException, SAXException, IOException
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
		downsampleStageOperation = new DownsampleStageOperation();
		downsampleStageOperation.setRate(2);
		
		hsbStageOperation = new HSBStageOperation();
		hsbStageOperation.setSaturation(0.9);
		hsbStageOperation.setHue(0);
		hsbStageOperation.setBrightness(3);
		
		rgbStageOperation = new RGBStageOperation();
		rgbStageOperation.setR(2);
		rgbStageOperation.setG(1.5);
		
		compressorStageOperation = new CompressorStageOperation();
		
		LimiterStageOperation limiterStageOperation = new LimiterStageOperation();
		limiterStageOperation.setPower(5);
		
		IStage stage = stageFactory.create();
		//stage.addStageOperation(downsampleStageOperation);
		stage.addStageOperation(hsbStageOperation);
		stage.addStageOperation(rgbStageOperation);
		stage.addStageOperation(compressorStageOperation);
		stage.addStageOperation(limiterStageOperation);
		
//		stage.loadImage("..//..//data//test//IMG_5196.CR2");
		
		stage.addOnProgressListener(new IProgressListener() {
			
			@Override
			public boolean invoke(Object arg0, float arg1) {
				System.out.println(arg1);
				return true;
			}
		});
		
		return stage;
	}

	
	private void createImageViewer(IStage stage, Composite parent)
	{
		imageViewer = new PreciseImageViewer(parent);

		vern.setUpdated(0, 0, true);
		vern.setBitmap(0, 0, stage.getOriginalBitmap());
		
		imageViewer.setVernissage(vern);
		imageViewer.setLayout(new FillLayout(SWT.HORIZONTAL));
	}
	
	/**	 * Create contents of the window.
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	protected void createContents() throws ParserConfigurationException, SAXException, IOException
	{
		shell = new Shell();
		shell.setSize(1100, 700);
		shell.setText("CatEyeB");
		
		GridLayout mainLayout = new GridLayout(2, false);
		shell.setLayout(mainLayout);
		mainLayout.horizontalSpacing = 5;
		mainLayout.marginLeft = 0;
		mainLayout.marginTop = 0;
		mainLayout.marginRight = 0;
		mainLayout.marginBottom = 0;
		
		Composite centerComposite = new Composite(shell, SWT.NONE);
		centerComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		centerComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Composite rightComposite = new Composite(shell, SWT.NONE);
		rightComposite.setLayout(new GridLayout(1, false));
		rightComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		
		createImageViewer(stage, centerComposite);

		// Stage operation widgets
		
		
		rgbStageOperationWidget = new RGBStageOperationWidget(rightComposite, SWT.NONE);
		rgbStageOperationWidget.setRgbStageOperation(rgbStageOperation);
		rgbStageOperationWidget.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		rgbStageOperation.addOnPropertyChangedListener(stageOperationPropertyChanged);
		
		hsbStageOperationWidget = new HSBStageOperationWidget(rightComposite, SWT.NONE);
		hsbStageOperationWidget.setHsbStageOperation(hsbStageOperation);
		hsbStageOperationWidget.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		hsbStageOperation.addOnPropertyChangedListener(stageOperationPropertyChanged);

		compressorStageOperationWidget = new CompressorStageOperationWidget(rightComposite, SWT.NONE);
		compressorStageOperationWidget.setCompressorStageOperation(compressorStageOperation);
		compressorStageOperationWidget.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		compressorStageOperation.addOnPropertyChangedListener(stageOperationPropertyChanged);
		
		Composite buttonsContainer = new Composite(rightComposite, SWT.NONE);
		RowLayout buttonsLayout = new RowLayout(SWT.HORIZONTAL);
		buttonsContainer.setLayout(buttonsLayout);

		loadBtn = new Button(buttonsContainer, SWT.NONE);
		loadBtn.setText("Load");
		loadBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				stage.loadImage("..//..//data//test//IMG_1520.CR2");
				processBtn.setEnabled(true);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		processBtn = new Button(buttonsContainer, SWT.NONE);
		processBtn.setEnabled(false);
		processBtn.setText("Process");
		processBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				stage.processImage();
				vern.setBitmap(0, 0, stage.getBitmap());
				imageViewer.redraw();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	
}
