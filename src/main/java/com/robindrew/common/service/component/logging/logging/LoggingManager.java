package com.robindrew.common.service.component.logging.logging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * The logging manager.
 */
public class LoggingManager implements LoggingManagerMBean {

	public static List<String> getLoggerNameList() {
		LogManager manager = LogManager.getLogManager();
		return Collections.list(manager.getLoggerNames());
	}

	private static final List<Logger> getLoggerList() {
		LogManager manager = LogManager.getLogManager();
		List<Logger> list = new ArrayList<>();
		for (String name : getLoggerNameList()) {
			Logger logger = manager.getLogger(name);
			if (logger != null) {
				list.add(logger);
			}
		}
		return list;
	}

	private static final Logger getLogger(String name) {
		LogManager manager = LogManager.getLogManager();
		return manager.getLogger(name);
	}

	private static Level getEffectiveLevel(Logger logger) {
		Level level = logger.getLevel();
		while (level == null) {
			logger = logger.getParent();
			level = logger.getLevel();
		}
		return level;
	}

	@Override
	public int getLoggerCount() {
		return getLoggerNames().size();
	}

	@Override
	public Set<String> getLoggerNames() {
		return new TreeSet<>(getLoggerNameList());
	}

	@Override
	public Map<String, String> getLoggerLevelMap() {
		Map<String, String> map = new TreeMap<>();
		for (Logger logger : getLoggerList()) {
			map.put(logger.getName(), getEffectiveLevel(logger).toString());
		}
		return map;
	}

	@Override
	public String getLogLevel(String name) {
		Logger logger = getLogger(name);
		return getEffectiveLevel(logger).toString();
	}

	@Override
	public String setLogLevel(String name, String level) {
		Logger logger = getLogger(name);
		logger.setLevel(Level.parse(level));
		return getEffectiveLevel(logger).toString();
	}

}
