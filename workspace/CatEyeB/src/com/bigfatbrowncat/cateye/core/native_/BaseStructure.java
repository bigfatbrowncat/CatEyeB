package com.bigfatbrowncat.cateye.core.native_;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class BaseStructure extends Structure {
	public void readFromMemory(Pointer p) {
		useMemory(p);
		read();
	}
	
	public void writeToMemory(Pointer p) {
		useMemory(p);
		write();
	}
}
