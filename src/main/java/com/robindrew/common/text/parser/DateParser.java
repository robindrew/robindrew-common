package com.robindrew.common.text.parser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser extends ObjectParser<Date> {

	private static final String DEFAULT_FORMAT = "yyyy-MM-dd";

	private final DateFormat format;

	public DateParser(DateFormat format) {
		if (format == null) {
			throw new NullPointerException("format");
		}
		this.format = format;
	}

	public DateParser(String format) {
		this(new SimpleDateFormat(format));
	}

	public DateParser() {
		this(DEFAULT_FORMAT);
	}

	public DateFormat getFormat() {
		return format;
	}

	@Override
	protected Date parseObject(String text) {
		try {
			synchronized (format) {
				return format.parse(text);
			}
		} catch (ParseException e) {
			throw new ParserException(e);
		}
	}

}
