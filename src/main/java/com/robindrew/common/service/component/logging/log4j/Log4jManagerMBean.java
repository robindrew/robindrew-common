package com.robindrew.common.service.component.logging.log4j;

import java.util.Map;
import java.util.Set;

import com.robindrew.common.mbean.annotated.Name;

public interface Log4jManagerMBean {

	int getLoggerCount();

	Set<String> getLoggerNames();

	Map<String, String> getLoggerLevelMap();

	String getLogLevel(@Name("name") String name);

	String setLogLevel(@Name("name") String name, @Name("level") String level);

}
