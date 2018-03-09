package com.robindrew.common.mbean.model;

public interface IBeanAttribute extends Comparable<IBeanAttribute> {

	IBean getBean();

	String getName();

	boolean isAvailable();

	Object getValue();

	Class<?> getType();

}
