package com.robindrew.common.service.component.jetty.handler.page;

import static com.robindrew.common.dependency.DependencyFactory.getDependency;
import static com.robindrew.common.service.component.stats.StatsComponent.JAVA_HEAP_FREE;
import static com.robindrew.common.service.component.stats.StatsComponent.JAVA_HEAP_USED;
import static com.robindrew.common.service.component.stats.StatsComponent.SYSTEM_MEMORY_FREE;
import static com.robindrew.common.service.component.stats.StatsComponent.SYSTEM_MEMORY_USED;

import java.lang.management.BufferPoolMXBean;
import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.robindrew.common.http.servlet.executor.IVelocityHttpContext;
import com.robindrew.common.http.servlet.request.IHttpRequest;
import com.robindrew.common.http.servlet.response.IHttpResponse;
import com.robindrew.common.service.Services;
import com.robindrew.common.service.component.jetty.handler.page.system.MemoryStats;
import com.robindrew.common.service.component.stats.IStatsCache;
import com.robindrew.common.service.component.stats.StatsInstantSet;
import com.robindrew.common.text.Strings;
import com.robindrew.common.util.Java;
import com.robindrew.common.util.SystemProperties;

public class SystemPage extends AbstractServicePage {

	public SystemPage(IVelocityHttpContext context, String templateName) {
		super(context, templateName);
	}

	@Override
	protected void execute(IHttpRequest request, IHttpResponse response, Map<String, Object> dataMap) {
		super.execute(request, response, dataMap);

		dataMap.put("serviceName", Services.getServiceName());
		dataMap.put("serviceInstance", Services.getServiceInstance());
		dataMap.put("serviceEnv", Services.getServiceEnv());
		dataMap.put("servicePort", Services.getServicePort());

		dataMap.put("systemHost", Java.getHostName());
		dataMap.put("systemAddress", Java.getHostAddress());
		dataMap.put("systemTime", Strings.date(Java.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));

		dataMap.put("processId", Java.getProcessId());

		dataMap.put("javaStartTime", Strings.date(Java.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
		dataMap.put("javaUptime", Strings.time(Java.getUptime()));
		dataMap.put("javaMaxHeapMemory", Strings.bytes(Java.maxMemory()));
		dataMap.put("javaUsedHeapMemory", Strings.bytes(Java.usedMemory()));
		dataMap.put("javaPercentHeapMemory", Strings.percent(Java.usedMemory(), Java.maxMemory()));

		dataMap.put("javaMaxDirectMemory", getJavaMaxDirectMemory());
		dataMap.put("javaUsedDirectMemory", getJavaUsedDirectMemory());
		dataMap.put("javaPercentDirectMemory", getJavaPercentDirectMemory());

		dataMap.put("javaMainClass", SystemProperties.getJavaCommand());
		dataMap.put("javaTimezone", SystemProperties.getTimeZone());
		dataMap.put("javaVersion", SystemProperties.getJavaVersion());
		dataMap.put("javaFileEncoding", SystemProperties.getFileEncoding());
		dataMap.put("javaWorkingDirectory", SystemProperties.getWorkingDirectory());

		dataMap.put("operatingSystem", SystemProperties.getOperatingSystem());
		dataMap.put("systemMaxMemory", Strings.bytes(Java.getSystemMaxMemory(1)));
		dataMap.put("systemUsedMemory", Strings.bytes(Java.getSystemUsedMemory(1)));
		dataMap.put("systemPercentMemory", getSystemPercentMemory());

		dataMap.put("systemProperties", toMap(System.getProperties()));
		dataMap.put("envProperties", toMap(System.getenv()));

		// Charts
		dataMap.put("pieChartJavaMemoryUsed", Java.usedMemory() / (1024 * 1024));
		dataMap.put("pieChartJavaMemoryFree", Java.freeMemory() / (1024 * 1024));

		dataMap.put("pieChartSystemMemoryUsed", Java.getSystemUsedMemory(1) / (1024 * 1024));
		dataMap.put("pieChartSystemMemoryFree", Java.getSystemFreeMemory(1) / (1024 * 1024));

		IStatsCache stats = getDependency(IStatsCache.class, null);
		if (stats != null) {
			setJavaHeapStats(stats, dataMap);
			setSystemMemoryStats(stats, dataMap);
		}
	}

	private void setJavaHeapStats(IStatsCache stats, Map<String, Object> dataMap) {
		StatsInstantSet used = new StatsInstantSet(stats.getStats(JAVA_HEAP_USED));
		StatsInstantSet free = new StatsInstantSet(stats.getStats(JAVA_HEAP_FREE));
		dataMap.put("javaHeapStats", MemoryStats.toSet(used, free));
	}

	private void setSystemMemoryStats(IStatsCache stats, Map<String, Object> dataMap) {
		StatsInstantSet used = new StatsInstantSet(stats.getStats(SYSTEM_MEMORY_USED));
		StatsInstantSet free = new StatsInstantSet(stats.getStats(SYSTEM_MEMORY_FREE));
		dataMap.put("systemMemoryStats", MemoryStats.toSet(used, free));
	}

	private String getJavaMaxDirectMemory() {
		try {
			for (BufferPoolMXBean bean : ManagementFactory.getPlatformMXBeans(BufferPoolMXBean.class)) {
				if (bean.getName().equals("direct")) {
					return Strings.bytes(bean.getTotalCapacity());
				}
			}
		} catch (Exception e) {
		}
		return "not available";
	}

	private String getJavaUsedDirectMemory() {
		try {
			for (BufferPoolMXBean bean : ManagementFactory.getPlatformMXBeans(BufferPoolMXBean.class)) {
				if (bean.getName().equals("direct")) {
					return Strings.bytes(bean.getMemoryUsed());
				}
			}
		} catch (Exception e) {
		}
		return "not available";
	}

	private String getJavaPercentDirectMemory() {
		try {
			for (BufferPoolMXBean bean : ManagementFactory.getPlatformMXBeans(BufferPoolMXBean.class)) {
				if (bean.getName().equals("direct")) {
					long used = bean.getMemoryUsed();
					long total = bean.getTotalCapacity();
					return Strings.percent(used, total);
				}
			}
		} catch (Exception e) {
		}
		return "not available";
	}

	private String getSystemPercentMemory() {
		long free = Java.getSystemFreeMemory(1);
		long total = Java.getSystemFreeMemory(1);
		return Strings.percent(total - free, total);
	}

	private Object toMap(Map<?, ?> map) {
		String separator = System.getProperty("path.separator");
		TreeMap<String, String> newMap = new TreeMap<>();
		for (Entry<?, ?> entry : map.entrySet()) {
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
			if (key.toLowerCase().endsWith("path")) {
				value = value.replace(separator, "<br>");
			}
			newMap.put(key, value);
		}
		return newMap;
	}

}
