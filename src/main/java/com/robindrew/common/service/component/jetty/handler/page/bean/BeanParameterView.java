package com.robindrew.common.service.component.jetty.handler.page.bean;

import java.util.ArrayList;
import java.util.List;

import com.robindrew.common.mbean.model.IBeanOperation;
import com.robindrew.common.mbean.model.IBeanParameter;

public class BeanParameterView {

	public static List<BeanParameterView> getParameters(IBeanOperation operation) {
		List<BeanParameterView> list = new ArrayList<>();
		for (IBeanParameter parameter : operation.getParameters()) {
			list.add(new BeanParameterView(parameter));
		}
		return list;
	}

	private final IBeanParameter parameter;

	public BeanParameterView(IBeanParameter parameter) {
		this.parameter = parameter;
	}

	public IBeanParameter getParameter() {
		return parameter;
	}

	public String getName() {
		return parameter.getName();
	}

	public String getTypeName() {
		return BeanAttributeView.getTypeName(parameter.getType());
	}

}
