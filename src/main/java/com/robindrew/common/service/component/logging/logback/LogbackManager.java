package com.robindrew.common.service.component.logging.logback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;

/**
 * The logback manager.
 */
public class LogbackManager implements LogbackManagerMBean {

	private static final String ROOT_LOGGER_NAME = "ROOT";

	private static List<Logger> getLoggerList() {
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		return ImmutableList.copyOf(context.getLoggerList());
	}

	private static boolean isRootLogger(Logger logger) {
		return logger.getName().equals(ROOT_LOGGER_NAME);
	}

	private static Logger getLogger(String name) {
		List<Logger> loggerList = getLoggerList();
		for (Logger logger : loggerList) {
			if (logger.getName().equals(name)) {
				return logger;
			}
		}
		List<Logger> matches = new ArrayList<>();
		for (Logger logger : loggerList) {
			if (logger.getName().contains(name)) {
				matches.add(logger);
			}
		}
		if (matches.size() == 1) {
			return matches.get(0);
		}
		if (matches.size() > 1) {
			throw new IllegalArgumentException("Too many loggers match given name: '" + name + "'");
		}
		throw new IllegalArgumentException("Logger does not exist with name: '" + name + "'");
	}

	@Override
	public int getLoggerCount() {
		return getLoggerNames().size();
	}

	@Override
	public Set<String> getLoggerNames() {
		Set<String> nameList = new TreeSet<>();
		for (Logger logger : getLoggerList()) {
			nameList.add(logger.getName());
		}
		return nameList;
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

	@Override
	public String resetLogLevel(String name) {
		Logger logger = getLogger(name);
		logger.setLevel(null);
		return logger.getEffectiveLevel().toString();
	}

	@Override
	public int resetAllLogLevels() {
		int count = 0;
		for (Logger logger : getLoggerList()) {
			if (!isRootLogger(logger)) {
				Level current = logger.getLevel();
				logger.setLevel(null);
				if (current != logger.getLevel()) {
					count++;
				}
			}
		}
		return count;
	}

}
