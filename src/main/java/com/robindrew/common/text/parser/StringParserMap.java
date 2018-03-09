package com.robindrew.common.text.parser;

import java.awt.Color;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.robindrew.common.date.UnitTime;
import com.robindrew.common.lang.bytes.UnitByte;

public class StringParserMap implements IStringParserMap {

	private final Map<Class<?>, IStringParser<?>> typeToParserMap = new HashMap<Class<?>, IStringParser<?>>();

	public StringParserMap() {

		// Primitives and equivalent object types
		setParser(Boolean.class, new BooleanParser());
		setParser(boolean.class, new BooleanParser());
		setParser(Character.class, new CharacterParser());
		setParser(char.class, new CharacterParser());
		setParser(Float.class, new FloatParser());
		setParser(float.class, new FloatParser());
		setParser(Double.class, new DoubleParser());
		setParser(double.class, new DoubleParser());
		setParser(Byte.class, new ByteParser());
		setParser(byte.class, new ByteParser());
		setParser(Short.class, new ShortParser());
		setParser(short.class, new ShortParser());
		setParser(Integer.class, new IntegerParser());
		setParser(int.class, new IntegerParser());
		setParser(Long.class, new LongParser());
		setParser(long.class, new LongParser());

		// Common object types
		setParser(File.class, new FileParser());
		setParser(UnitTime.class, new UnitTimeParser());
		setParser(UnitByte.class, new UnitByteParser());
		setParser(String.class, new StringParser());
		setParser(Date.class, new DateParser());
		setParser(LocalDate.class, new LocalDateParser());
		setParser(LocalTime.class, new LocalTimeParser());
		setParser(Color.class, new ColorParser());
		setParser(BigInteger.class, new BigIntegerParser());
		setParser(BigDecimal.class, new BigDecimalParser());
		setParser(String[].class, new StringArrayParser());
	}

	@Override
	public <T> T parse(Class<T> type, String text) {
		IStringParser<T> parser = getParser(type);
		if (parser == null) {
			throw new IllegalArgumentException("type not supported: " + type);
		}
		return parser.parse(text);
	}

	@Override
	public synchronized <T> void setParser(Class<T> type, IStringParser<T> parser) {
		if (type == null) {
			throw new NullPointerException("type");
		}
		if (parser == null) {
			throw new NullPointerException("parser");
		}
		typeToParserMap.put(type, parser);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected <T> IStringParser<T> handleDynamicType(Class<T> type) {
		IStringParser<T> parser = null;

		// Dynamically register enum types
		if (Enum.class.isAssignableFrom(type)) {
			parser = new EnumParser(type);
			setParser(type, parser);
		}

		return parser;
	}

	@Override
	@SuppressWarnings("unchecked")
	public synchronized <T> IStringParser<T> getParser(Class<T> type) {
		if (type == null) {
			throw new NullPointerException("type");
		}
		IStringParser<T> parser = (IStringParser<T>) typeToParserMap.get(type);
		if (parser == null) {
			parser = handleDynamicType(type);
		}
		return parser;
	}

}
