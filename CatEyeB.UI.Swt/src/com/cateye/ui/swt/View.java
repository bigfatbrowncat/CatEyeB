package com.cateye.ui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * Base class for the view
 */
public abstract class View<T extends ViewModel> extends Composite
{
	protected T viewModel;
	
	public View(Composite parent, T viewModel)
	{
		super(parent, SWT.DEFAULT);
		this.viewModel = viewModel;
	}
}
