package com.robindrew.common.json;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;

public interface IJson {

	boolean contains(String name);

	String get(String name);

	String get(String name, boolean optional);

	IJson getObject(String name);

	boolean getBoolean(String name);

	Boolean getBoolean(String name, boolean optional);

	long getLong(String name);

	int getInt(String name);

	Integer getInt(String name, boolean optional);

	BigDecimal getBigDecimal(String name);

	BigDecimal getBigDecimal(String name, boolean optional);

	<E extends Enum<E>> E getEnum(String name, Class<E> enumType);

	LocalDateTime getLocalDateTime(String name);

	LocalTime getLocalTime(String name);

	List<IJson> getObjectList(String name);

	<E> List<E> getList(String string, Function<IJson, E> function);

}
