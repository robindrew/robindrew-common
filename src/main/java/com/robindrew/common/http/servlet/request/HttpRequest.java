package com.robindrew.common.http.servlet.request;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import com.robindrew.common.text.parser.IStringParser;

public class HttpRequest extends HttpServletRequestWrapper implements IHttpRequest {

	public HttpRequest(HttpServletRequest request) {
		super(request);
	}

	@Override
	public HttpServletRequest getRequest() {
		return (HttpServletRequest) super.getRequest();
	}

	@Override
	public Map<String, String> getHeaderMap() {
		Map<String, String> map = new LinkedHashMap<>();
		for (String name : Collections.list(getHeaderNames())) {
			map.put(name, getHeader(name));
		}
		return map;
	}

	@Override
	public List<String> getCookies(String key) {
		List<String> values = new ArrayList<>();
		for (Cookie cookie : getCookies()) {
			if (cookie.getName().equals(key)) {
				values.add(cookie.getValue());
			}
		}
		return values;
	}

	@Override
	public void set(String key, Object value) {
		HttpSession session = getRequest().getSession(true);
		session.setAttribute(key, value);
	}

	@Override
	public Object get(String key, Object defaultValue) {

		// Firstly look at the parameters
		Object parameter = getRequest().getParameter(key);
		if (parameter != null) {
			return parameter;
		}

		// Next take a look at the session attributes
		HttpSession session = getRequest().getSession(false);
		if (session != null) {
			parameter = session.getAttribute(key);
			if (parameter != null) {
				return parameter;
			}
		}

		// Return the default value
		return defaultValue;
	}

	@Override
	public Object get(String key) {
		Object value = get(key, null);
		if (value == null) {
			throw new IllegalArgumentException("key not found: " + key);
		}
		return value;
	}

	@Override
	public boolean exists(String key) {
		return get(key, null) != null;
	}

	@Override
	public String getString(String key) {
		String value = getString(key, null);
		if (value == null) {
			throw new IllegalArgumentException("key not found: " + key);
		}
		return value;
	}

	@Override
	public String getString(String key, String defaultValue) {
		Object value = get(key, null);
		if (value == null) {
			return defaultValue;
		}
		return value.toString();
	}

	@Override
	public BigDecimal getBigDecimal(String key) {
		BigDecimal value = getBigDecimal(key, null);
		if (value == null) {
			throw new IllegalArgumentException("key not found: " + key);
		}
		return value;
	}

	@Override
	public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
		Object value = get(key, null);
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof BigDecimal) {
			return (BigDecimal) value;
		}
		return new BigDecimal(value.toString());
	}

	@Override
	public Boolean getBoolean(String key) {
		Boolean value = getBoolean(key, null);
		if (value == null) {
			throw new IllegalArgumentException("key not found: " + key);
		}
		return value;
	}

	@Override
	public Boolean getBoolean(String key, Boolean defaultValue) {
		Object value = get(key, null);
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof Boolean) {
			return (Boolean) value;
		}
		return Boolean.parseBoolean(value.toString());
	}

	@Override
	public Integer getInteger(String key) {
		Integer value = getInteger(key, null);
		if (value == null) {
			throw new IllegalArgumentException("key not found: " + key);
		}
		return value;
	}

	@Override
	public Integer getInteger(String key, Integer defaultValue) {
		Object value = get(key, null);
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof Integer) {
			return (Integer) value;
		}
		return Integer.parseInt(value.toString());
	}

	@Override
	public Long getLong(String key) {
		Long value = getLong(key, null);
		if (value == null) {
			throw new IllegalArgumentException("key not found: " + key);
		}
		return value;
	}

	@Override
	public Long getLong(String key, Long defaultValue) {
		Object value = get(key, null);
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof Long) {
			return (Long) value;
		}
		return Long.parseLong(value.toString());
	}

	@Override
	public <E extends Enum<E>> E getEnum(Class<E> enumClass, String key) {
		E value = getEnum(enumClass, key, null);
		if (value == null) {
			throw new IllegalArgumentException("key not found: " + key);
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends Enum<E>> E getEnum(Class<E> enumClass, String key, E defaultValue) {
		Object value = get(key, null);
		if (value == null) {
			return defaultValue;
		}
		if (value.getClass().equals(enumClass)) {
			return (E) value;
		}
		return Enum.valueOf(enumClass, value.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V getValue(IStringParser<V> parser, String key) {
		Object value = get(key);
		if (value instanceof String) {
			return parser.parse((String) value);
		}
		return (V) value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V getValue(IStringParser<V> parser, String key, V defaultValue) {
		Object value = get(key, null);
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof String) {
			return parser.parse((String) value);
		}
		return (V) value;
	}
}
