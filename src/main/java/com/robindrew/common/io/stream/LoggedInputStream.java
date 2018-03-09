package com.robindrew.common.io.stream;

import static com.robindrew.common.text.Strings.bytes;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.input.CountingInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.date.Delay;

public class LoggedInputStream extends CountingInputStream {

	private static final Logger log = LoggerFactory.getLogger(LoggedInputStream.class);

	private final String name;
	private Delay delay = new Delay(1, TimeUnit.SECONDS);

	public LoggedInputStream(String name, InputStream input) {
		super(input);
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
	protected void afterRead(int count) {
		super.afterRead(count);
		if (count > 0 && delay.expired(true)) {
			log.info("Read " + bytes(getByteCount()) + " from " + getName());
		}
	}

}
