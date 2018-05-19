package com.robindrew.common.service.component.jetty.handler.page;

import java.util.List;
import java.util.Map;

import javax.management.ObjectName;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.robindrew.common.http.servlet.executor.IVelocityHttpContext;
import com.robindrew.common.http.servlet.request.IHttpRequest;
import com.robindrew.common.http.servlet.response.IHttpResponse;
import com.robindrew.common.mbean.model.BeanServer;
import com.robindrew.common.mbean.model.IBean;
import com.robindrew.common.mbean.model.IBeanOperation;
import com.robindrew.common.mbean.model.IBeanParameter;
import com.robindrew.common.service.component.jetty.handler.page.bean.BeanOperationView;
import com.robindrew.common.text.Strings;
import com.robindrew.common.text.parser.IStringParser;
import com.robindrew.common.text.parser.StringParserMap;

public class BeanOperationPage extends AbstractServicePage {

	public BeanOperationPage(IVelocityHttpContext context, String templateName) {
		super(context, templateName);
	}

	@Override
	protected void execute(IHttpRequest request, IHttpResponse response, Map<String, Object> dataMap) {
		super.execute(request, response, dataMap);

		String domain = request.getString("domain");
		String type = request.getString("type");
		String name = request.getString("name");
		int operationIndex = request.getInteger("operation");

		BeanServer server = new BeanServer();
		IBean bean = server.getBean(domain, type, name);
		IBeanOperation operation = bean.getOperation(operationIndex);

		BeanOperationView view = new BeanOperationView(operation, operationIndex);
		dataMap.put("bean", bean);
		dataMap.put("operation", view);
		dataMap.put("returnType", view.getReturnTypeName());

		Object value;
		Object[] parameters = getParameters(operation, request);
		try {
			value = operation.invoke(parameters);
		} catch (Exception e) {
			value = Strings.getStackTrace(e);
			dataMap.put("operationFailed", true);
			dataMap.put("valueType", "String");
			dataMap.put("value", value);
			return;
		}

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

	private Object[] getParameters(IBeanOperation operation, IHttpRequest request) {
		List<IBeanParameter> parameterList = operation.getParameters();
		Object[] parameters = new Object[parameterList.size()];
		for (int i = 0; i < parameterList.size(); i++) {
			IBeanParameter parameter = parameterList.get(i);
			String key = "param" + i + "-" + parameter.getName();
			String value = request.getString(key);
			parameters[i] = parseValue(value, parameter);
		}
		return parameters;
	}

	private Object parseValue(String value, IBeanParameter parameter) {
		Class<?> type = parameter.getType();
		StringParserMap map = new StringParserMap();
		IStringParser<?> parser = map.getParser(type);
		return parser.parse(value);
	}
}
