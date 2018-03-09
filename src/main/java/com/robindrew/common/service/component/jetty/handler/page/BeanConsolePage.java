package com.robindrew.common.service.component.jetty.handler.page;

import java.util.Map;

import com.robindrew.common.http.servlet.executor.IVelocityHttpContext;
import com.robindrew.common.http.servlet.request.IHttpRequest;
import com.robindrew.common.http.servlet.response.IHttpResponse;
import com.robindrew.common.mbean.model.BeanServer;

public class BeanConsolePage extends AbstractServicePage {

	public BeanConsolePage(IVelocityHttpContext context, String templateName) {
		super(context, templateName);
	}

	@Override
	protected void execute(IHttpRequest request, IHttpResponse response, Map<String, Object> dataMap) {
		super.execute(request, response, dataMap);

		BeanServer server = new BeanServer();
		dataMap.put("standardBeans", server.getBeans(true));
		dataMap.put("customBeans", server.getBeans(false));
	}
}
