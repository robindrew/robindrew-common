package com.robindrew.common.text.parser;

public interface IStringParserMap {

	<T> T parse(Class<T> type, String text);

	<T> void setParser(Class<T> type, IStringParser<T> parser);

	<T> IStringParser<T> getParser(Class<T> type);

}
