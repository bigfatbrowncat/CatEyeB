package com.bigfatbrowncat.cateye.core.raw;

import com.sun.jna.Callback;

public interface ExtractingErrorReporter extends Callback {
	boolean invoke(int error);
}
