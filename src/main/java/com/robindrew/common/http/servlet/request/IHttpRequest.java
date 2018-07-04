package com.robindrew.common.http.servlet.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.robindrew.common.text.parser.IStringParser;

public interface IHttpRequest extends HttpServletRequest {

	Map<String, String> getHeaderMap();

	List<String> getCookies(String key);

	boolean exists(String key);

	void set(String key, Object value);

	Object get(String key, Object defaultValue);

	Object get(String key);

	String getString(String key);

	String getString(String key, String defaultValue);

	BigDecimal getBigDecimal(String key);

	BigDecimal getBigDecimal(String key, BigDecimal defaultValue);

	Boolean getBoolean(String key);

	Boolean getBoolean(String key, Boolean defaultValue);

	Integer getInteger(String key);

	Integer getInteger(String key, Integer defaultValue);

	Long getLong(String key);

	Long getLong(String key, Long defaultValue);

	<E extends Enum<E>> E getEnum(Class<E> enumClass, String key);

	<E extends Enum<E>> E getEnum(Class<E> enumClass, String key, E defaultValue);

	<V> V getValue(IStringParser<V> parser, String key);

	<V> V getValue(IStringParser<V> parser, String key, V defaultValue);

}
