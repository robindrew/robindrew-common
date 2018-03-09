package com.robindrew.common.concurrent;

import com.robindrew.common.util.Check;

public class LoopingRunnableThread extends LoopingThread {

	private final Runnable runnable;

	public LoopingRunnableThread(String name, Runnable runnable) {
		super(name);
		this.runnable = Check.notNull("runnable", runnable);
	}

	@Override
	public void runOneIteration() {
		runnable.run();
	}

}
