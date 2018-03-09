package com.robindrew.common.http.servlet.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface IHttpRequest extends HttpServletRequest {

	boolean exists(String key);

	Map<String, String> getHeaderMap();

	<E extends Enum<E>> E getEnum(String key, Class<E> enumClass);

	<V> V getValue(String key);

	<V> V getValue(String key, V defaultValue);

	void setValue(String key, Object value);

	String get(String key);

	String get(String key, String defaultValue);

	int getInt(String key);

	int getInt(String key, int defaultValue);

	long getLong(String key);

	long getLong(String key, long defaultValue);

	boolean getBoolean(String key);

	boolean getBoolean(String key, boolean defaultValue);

	List<String> getCookies(String key);

	BigDecimal getBigDecimal(String key);

	BigDecimal getBigDecimal(String key, BigDecimal defaultValue);

}
