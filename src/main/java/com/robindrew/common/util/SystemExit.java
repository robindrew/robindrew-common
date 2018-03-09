package com.robindrew.common.util;

import java.util.concurrent.atomic.AtomicReference;

class SystemExit extends Thread {

	/** The exit status (can only be set once). */
	private static final AtomicReference<Integer> exitStatus = new AtomicReference<>();

	/**
	 * Call {@link Runtime#exit(int)} asynchronously to allow for non-blocking calls.
	 * @param status the exit status code.
	 */
	public static final void exitAsync(final int status) {
		if (exitStatus.compareAndSet(null, status)) {
			new SystemExit(status).start();
		}
	}

	private final int status;

	private SystemExit(int status) {
		super("SystemExit");
		this.status = status;
	}

	@Override
	public void run() {
		Runtime.getRuntime().exit(status);
	}

}
