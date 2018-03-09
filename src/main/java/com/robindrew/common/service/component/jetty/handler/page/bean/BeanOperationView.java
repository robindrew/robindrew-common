package com.robindrew.common.service.component.jetty.handler.page.bean;

import java.util.ArrayList;
import java.util.List;

import com.robindrew.common.mbean.model.IBean;
import com.robindrew.common.mbean.model.IBeanOperation;

public class BeanOperationView {

	public static List<BeanOperationView> getOperations(IBean bean) {
		List<BeanOperationView> list = new ArrayList<>();
		int index = 0;
		for (IBeanOperation operation : bean.getOperations()) {
			list.add(new BeanOperationView(operation, index++));
		}
		return list;
	}

	private final IBeanOperation operation;
	private final int index;

	public BeanOperationView(IBeanOperation operation, int index) {
		this.operation = operation;
		this.index = index;
	}

	public IBeanOperation getOperation() {
		return operation;
	}

	public String getName() {
		return operation.getName();
	}

	public int getIndex() {
		return index;
	}

	public List<BeanParameterView> getParameters() {
		return BeanParameterView.getParameters(operation);
	}

	public String getReturnTypeName() {
		return BeanAttributeView.getTypeName(operation.getReturnType());
	}

}
