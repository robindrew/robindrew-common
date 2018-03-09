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

public class HttpRequest extends HttpServletRequestWrapper implements IHttpRequest {

	public HttpRequest(HttpServletRequest request) {
		super(request);
	}

	@Override
	public HttpServletRequest getRequest() {
		return (HttpServletRequest) super.getRequest();
	}

	@Override
	public String get(String key) {
		String value = getRequest().getParameter(key);
		if (value == null) {
			throw new IllegalArgumentException("key not found: " + key);
		}
		return value;
	}

	@Override
	public String get(String key, String defaultValue) {
		String value = getRequest().getParameter(key);
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

	@Override
	public int getInt(String key) {
		return Integer.parseInt(get(key));
	}

	@Override
	public int getInt(String key, int defaultValue) {
		if (!exists(key)) {
			return defaultValue;
		}
		return getInt(key);
	}

	@Override
	public long getLong(String key) {
		return Long.parseLong(get(key));
	}

	@Override
	public long getLong(String key, long defaultValue) {
		if (!exists(key)) {
			return defaultValue;
		}
		return getLong(key);
	}

	@Override
	public boolean exists(String key) {
		return getRequest().getParameter(key) != null;
	}

	@Override
	public Map<String, String> getHeaderMap() {
		Map<String, String> map = new LinkedHashMap<>();
		for (String name : Collections.list(getHeaderNames())) {
			map.put(name, getHeader(name));
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V getValue(String key) {
		HttpSession session = getRequest().getSession(false);
		if (session == null) {
			throw new IllegalStateException("Session does not exist");
		}
		V value = (V) session.getAttribute(key);
		if (value == null) {
			throw new IllegalArgumentException("Session does not contain key: '" + key + "'");
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V getValue(String key, V defaultValue) {
		HttpSession session = getRequest().getSession(false);
		if (session == null) {
			return defaultValue;
		}
		V value = (V) session.getAttribute(key);
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

	@Override
	public void setValue(String key, Object value) {
		HttpSession session = getRequest().getSession(true);
		session.setAttribute(key, value);
	}

	@Override
	public <E extends Enum<E>> E getEnum(String key, Class<E> enumClass) {
		String value = get(key);
		return Enum.valueOf(enumClass, value);
	}

	@Override
	public boolean getBoolean(String key) {
		return Boolean.parseBoolean(get(key));
	}

	@Override
	public boolean getBoolean(String key, boolean defaultValue) {
		if (!exists(key)) {
			return defaultValue;
		}
		return getBoolean(key);
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
	public BigDecimal getBigDecimal(String key) {
		return new BigDecimal(get(key));
	}

	@Override
	public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
		if (!exists(key)) {
			return defaultValue;
		}
		return getBigDecimal(key);
	}

}
