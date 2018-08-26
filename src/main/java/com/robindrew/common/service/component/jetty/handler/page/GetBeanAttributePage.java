package com.robindrew.common.service.component.jetty.handler.page;

import java.util.Map;

import javax.management.ObjectName;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.robindrew.common.http.servlet.executor.IVelocityHttpContext;
import com.robindrew.common.http.servlet.request.IHttpRequest;
import com.robindrew.common.http.servlet.response.IHttpResponse;
import com.robindrew.common.mbean.model.BeanServer;
import com.robindrew.common.mbean.model.IBean;
import com.robindrew.common.mbean.model.IBeanAttribute;
import com.robindrew.common.service.component.jetty.handler.page.bean.BeanAttributeView;

public class GetBeanAttributePage extends AbstractServicePage {

	public GetBeanAttributePage(IVelocityHttpContext context, String templateName) {
		super(context, templateName);
	}

	@Override
	protected void execute(IHttpRequest request, IHttpResponse response, Map<String, Object> dataMap) {
		super.execute(request, response, dataMap);

		String domain = request.getString("domain");
		String type = request.getString("type");
		String name = request.getString("name");
		String attributeName = request.getString("attribute");

		BeanServer server = new BeanServer();
		IBean bean = server.getBean(domain, type, name);
		IBeanAttribute attribute = bean.getAttribute(attributeName);

		Object value = attribute.getValue();
		dataMap.put("bean", bean);
		dataMap.put("attribute", new BeanAttributeView(attribute));

		// String
		if (value instanceof String || value instanceof ObjectName) {
			dataMap.put("valueType", "String");
			dataMap.put("value", value);
			return;
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(value);
		dataMap.put("valueType", "Json");
		dataMap.put("value", json);
	}
}
