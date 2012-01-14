package com.cateye.core.stage;

import com.cateye.core.IProgressListener;

public interface IStage
{
	public abstract void addOnProgressListener(IProgressListener listener);
	public abstract void removeOnProgressListener(IProgressListener listener);
	
	public abstract void addStageOperation(StageOperation operation);
	public abstract void removeStageOperation(StageOperation operation);
	
	public abstract void loadImage(String fileName);
	public abstract void saveImage(String fileName);
	public abstract boolean isImageLoaded();
	
	public abstract void processImage();
	
	public abstract void dispose();	
}