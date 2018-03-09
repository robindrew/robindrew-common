package com.robindrew.common.io.stream;

import static com.robindrew.common.text.Strings.bytes;

import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.output.CountingOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.date.Delay;

public class LoggedOutputStream extends CountingOutputStream {

	private static final Logger log = LoggerFactory.getLogger(LoggedOutputStream.class);

	private final String name;
	private Delay delay = new Delay(1, TimeUnit.SECONDS);

	public LoggedOutputStream(String name, OutputStream output) {
		super(output);
		if (name.isEmpty()) {
			throw new IllegalArgumentException("name is empty");
		}
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Delay getDelay() {
		return delay;
	}

	public void setDelay(Delay delay) {
		if (delay == null) {
			throw new NullPointerException("delay");
		}
		this.delay = delay;
	}

	@Override
	protected void beforeWrite(int count) {
		super.beforeWrite(count);
		if (count > 0 && getDelay().expired(true)) {
			log.info("Written " + bytes(getByteCount()) + " bytes to " + getName());
		}
	}

}
