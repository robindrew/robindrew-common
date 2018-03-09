package com.robindrew.common.io.lineprocessor;

import com.google.common.io.LineProcessor;

public abstract class SimpleLineProcessor<R> implements LineProcessor<R> {

	private R result = null;

	public void setResult(R result) {
		this.result = result;
	}

	@Override
	public R getResult() {
		return result;
	}

}
