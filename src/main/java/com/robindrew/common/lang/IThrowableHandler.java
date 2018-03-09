package com.robindrew.common.lang;

import java.lang.Thread.UncaughtExceptionHandler;

public interface IThrowableHandler extends UncaughtExceptionHandler {

	void handle(Throwable throwable);

}
