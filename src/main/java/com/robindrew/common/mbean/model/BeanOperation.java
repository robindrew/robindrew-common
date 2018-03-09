package com.robindrew.common.mbean.model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.lang.clazz.ClassResolver;
import com.robindrew.common.mbean.MBeanRuntimeException;
import com.robindrew.common.util.Java;

public class BeanOperation implements IBeanOperation {

	private static final Logger log = LoggerFactory.getLogger(BeanOperation.class);

	private final IBean bean;
	private final MBeanOperationInfo operation;

	public BeanOperation(IBean bean, MBeanOperationInfo operation) {
		this.bean = bean;
		this.operation = operation;
	}

	@Override
	public String getName() {
		return operation.getName();
	}

	@Override
	public int parameters() {
		return operation.getSignature().length;
	}

	@Override
	public List<IBeanParameter> getParameters() {
		MBeanServer server = getBean().getServer();
		List<IBeanParameter> list = new ArrayList<IBeanParameter>();
		int index = 0;
		for (MBeanParameterInfo parameter : getMBeanOperationInfo().getSignature()) {
			list.add(new BeanParameter(server, parameter, index++));
		}
		return list;
	}

	private MBeanOperationInfo getMBeanOperationInfo() {
		return operation;
	}

	@Override
	public int compareTo(IBeanOperation compare) {
		int value = getName().compareTo(compare.getName());
		if (value != 0) {
			return value;
		}
		int parameters1 = this.parameters();
		int parameters2 = compare.parameters();
		value = (parameters1 < parameters2 ? -1 : (parameters1 == parameters2 ? 0 : 1));
		if (value != 0) {
			return value;
		}
		return 1;
	}

	private String[] getSignature() {
		MBeanParameterInfo[] signature = operation.getSignature();
		String[] array = new String[signature.length];
		for (int i = 0; i < signature.length; i++) {
			array[i] = signature[i].getType();
		}
		return array;
	}

	@Override
	public String getDescription() {
		return operation.getDescription();
	}

	public String getReturnTypeName() {
		return operation.getReturnType();
	}

	public Class<?> getReturnTypeClass() {
		return new ClassResolver().resolveClass(getReturnTypeName());
	}

	public String getMethodName() {
		return operation.getName();
	}

	public String getParameterName(int index) {
		MBeanParameterInfo[] signature = operation.getSignature();
		return signature[index].getName();
	}

	public String[] getParameterNames() {
		String[] names = new String[parameters()];
		for (int i = 0; i < names.length; i++) {
			names[i] = getParameterName(i);
		}
		return names;
	}

	public String[] getParameterTypeNames() {
		MBeanParameterInfo[] parameters = operation.getSignature();
		String[] names = new String[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			names[i] = parameters[i].getType();
		}
		return names;
	}

	public Class<?>[] getParameterTypeClasses() {
		MBeanParameterInfo[] parameters = operation.getSignature();
		Class<?>[] types = new Class[parameters.length];
		ClassResolver resolver = new ClassResolver();
		for (int i = 0; i < parameters.length; i++) {
			types[i] = resolver.resolveClass(parameters[i].getType());
		}
		return types;
	}

	public Method getMethod() {
		Class<?> type = getBean().getTypeClass();
		try {
			return type.getMethod(getMethodName(), getParameterTypeClasses());
		} catch (Exception e) {
			throw new MBeanRuntimeException(e);
		}
	}

	@Override
	public Object invoke(Object[] parameters) {
		try {
			MBeanServer server = getBean().getServer();
			ObjectName name = getBean().getObjectName();
			log.info("Invoke: " + getName() + Arrays.asList(parameters));
			return server.invoke(name, getName(), parameters, getSignature());
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public Object invoke(Collection<?> parameters) {
		Object[] array = parameters.toArray(new Object[parameters.size()]);
		return invoke(array);
	}

	@Override
	public IBean getBean() {
		return bean;
	}

	@Override
	public Class<?> getReturnType() {
		String type = operation.getReturnType();
		return new ClassResolver().resolveClass(type);
	}

}
