package com.cateye.ui.swt;

import com.cateye.core.IPreciseBitmap;

public class PreciseBitmapsVernissage {
	private int columns, rows;
	private IPreciseBitmap[][] bitmaps;
	private String[][] captions;
	private boolean[][] updated;
	
	private IPreciseBitmapsVernissageChangedListener changedListener;
	
	public int getColumns()
	{
		return columns;
	}
	
	public int getRows()
	{
		return rows;
	}
	
	public PreciseBitmapsVernissage(int columns, int rows)
	{
		this.columns = columns;
		this.rows = rows;
		
		this.bitmaps = new IPreciseBitmap[rows][];
		for (int j = 0; j < rows; j++)
			this.bitmaps[j] = new IPreciseBitmap[columns];

		this.captions = new String[rows][];
		for (int j = 0; j < rows; j++)
			this.captions[j] = new String[columns];

		this.updated = new boolean[rows][];
		for (int j = 0; j < rows; j++)
			this.updated[j] = new boolean[columns];
	}
	
	protected void checkBounds(int i , int j)
	{
		if (i < 0 || i >= columns)
			throw new IndexOutOfBoundsException("Column value is out of bounds");
		if (j < 0 || j >= rows)
			throw new IndexOutOfBoundsException("Row value is out of bounds");
	}

	public void setBitmap(int i, int j, IPreciseBitmap bitmap)
	{
		checkBounds(i, j);
		bitmaps[j][i] = bitmap;
	}
	
	public IPreciseBitmap getBitmap(int i, int j)
	{
		checkBounds(i, j);
		return bitmaps[j][i];
	}
	
	public void setCaption(int i, int j, String caption)
	{
		checkBounds(i, j);
		captions[j][i] = caption;
	}
	
	public String getCaption(int i, int j)
	{
		checkBounds(i, j);
		return captions[j][i];
	}
	
	public void setUpdated(int i, int j, boolean updated)
	{
		checkBounds(i, j);
		this.updated[j][i] = updated;
	}
	
	public boolean getUpdated(int i, int j)
	{
		checkBounds(i, j);
		return updated[j][i];
	}
	
	public void setChangedListener(IPreciseBitmapsVernissageChangedListener listener)
	{
		this.changedListener = listener;
	}
	
	protected void onChanged(int column, int row)
	{
		if (changedListener != null)
		{
			changedListener.onChanged(this, column, row);
		}
	}
}
