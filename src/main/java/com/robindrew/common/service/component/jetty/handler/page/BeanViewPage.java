package com.robindrew.common.service.component.jetty.handler.page;

import java.util.Map;

import com.robindrew.common.http.servlet.executor.IVelocityHttpContext;
import com.robindrew.common.http.servlet.request.IHttpRequest;
import com.robindrew.common.http.servlet.response.IHttpResponse;
import com.robindrew.common.mbean.model.BeanServer;
import com.robindrew.common.mbean.model.IBean;
import com.robindrew.common.service.component.jetty.handler.page.bean.BeanAttributeView;
import com.robindrew.common.service.component.jetty.handler.page.bean.BeanOperationView;

public class BeanViewPage extends AbstractServicePage {

	public BeanViewPage(IVelocityHttpContext context, String templateName) {
		super(context, templateName);
	}

	@Override
	protected void execute(IHttpRequest request, IHttpResponse response, Map<String, Object> dataMap) {
		super.execute(request, response, dataMap);

		String domain = request.get("domain");
		String type = request.get("type");
		String name = request.get("name");

		BeanServer server = new BeanServer();
		IBean bean = server.getBean(domain, type, name);
		dataMap.put("bean", bean);
		dataMap.put("attributes", BeanAttributeView.getAttributes(bean));
		dataMap.put("operations", BeanOperationView.getOperations(bean));
	}
}
