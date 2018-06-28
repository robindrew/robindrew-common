package com.robindrew.common.service.component.jetty.handler.page;

import java.util.Map;

import com.robindrew.common.http.servlet.executor.IVelocityHttpContext;
import com.robindrew.common.http.servlet.executor.VelocityHttpExecutor;
import com.robindrew.common.http.servlet.request.IHttpRequest;
import com.robindrew.common.http.servlet.response.IHttpResponse;
import com.robindrew.common.service.Services;
import com.robindrew.common.text.Strings;
import com.robindrew.common.util.Java;

public abstract class AbstractServicePage extends VelocityHttpExecutor {

	protected AbstractServicePage(IVelocityHttpContext context, String templateName) {
		super(context, templateName);
	}

	public String getTitle() {
		return Services.getServiceName() + " #" + Services.getServiceInstance();
	}

	@Override
	protected void execute(IHttpRequest request, IHttpResponse response, Map<String, Object> dataMap) {

		dataMap.put("title", getTitle());

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
		dataMap.put("javaMaxMemory", Strings.bytes(Java.maxMemory()));
		dataMap.put("javaUsedMemory", Strings.bytes(Java.usedMemory()));
		dataMap.put("javaPercentMemory", Strings.percent(Java.usedMemory(), Java.maxMemory()));
	}

}
