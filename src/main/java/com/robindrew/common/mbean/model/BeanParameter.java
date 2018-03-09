package com.robindrew.common.mbean.model;

import javax.management.MBeanParameterInfo;
import javax.management.MBeanServer;

import com.robindrew.common.lang.clazz.ClassResolver;

public class BeanParameter implements IBeanParameter {

	private final int index;
	private final MBeanServer server;
	private final MBeanParameterInfo parameter;

	public BeanParameter(MBeanServer server, MBeanParameterInfo parameter, int index) {
		this.index = index;
		this.server = server;
		this.parameter = parameter;
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public Class<?> getType() {
		return new ClassResolver().resolveClass(parameter.getType());
	}

	@Override
	public String getTypeName() {
		return parameter.getType();
	}

	public MBeanServer getServer() {
		return server;
	}

	@Override
	public String getName() {
		return parameter.getName();
	}

	@Override
	public String getDescription() {
		return parameter.getDescription();
	}

	@Override
	public String toString() {
		return getName() + "(" + getTypeName() + ")";
	}
}
