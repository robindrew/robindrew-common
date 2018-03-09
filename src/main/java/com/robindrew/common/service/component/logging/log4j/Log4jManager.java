package com.robindrew.common.service.component.logging.log4j;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * The log4j manager.
 */
public class Log4jManager implements Log4jManagerMBean {

	@SuppressWarnings("unchecked")
	private static final List<Logger> getLoggerList() {
		return Collections.list(LogManager.getCurrentLoggers());
	}

	private static Logger getLogger(String name) {
		Logger logger = LogManager.getLogger(name);
		if (logger == null) {
			throw new IllegalArgumentException("Logger does not exist with name: '" + name + "'");
		}
		return logger;
	}

	@Override
	public int getLoggerCount() {
		return getLoggerNames().size();
	}

	@Override
	public Set<String> getLoggerNames() {
		Set<String> names = new TreeSet<>();
		for (Logger logger : getLoggerList()) {
			names.add(logger.getName());
		}
		return names;
	}

	@Override
	public Map<String, String> getLoggerLevelMap() {
		Map<String, String> map = new TreeMap<>();
		for (Logger logger : getLoggerList()) {
			map.put(logger.getName(), logger.getEffectiveLevel().toString());
		}
		return map;
	}

	@Override
	public String getLogLevel(String name) {
		Logger logger = getLogger(name);
		return logger.getEffectiveLevel().toString();
	}

	@Override
	public String setLogLevel(String name, String level) {
		Logger logger = getLogger(name);
		logger.setLevel(Level.toLevel(level));
		return logger.getEffectiveLevel().toString();
	}

}
