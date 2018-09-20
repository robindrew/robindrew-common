package com.robindrew.common.concurrent;

import static com.robindrew.common.util.Check.notNull;

public class LoopingRunnableThread extends LoopingThread {

	private final Runnable runnable;

	public LoopingRunnableThread(String name, Runnable runnable) {
		super(name);
		this.runnable = notNull("runnable", runnable);
	}

	@Override
	public void runOneIteration() {
		runnable.run();
	}

}
