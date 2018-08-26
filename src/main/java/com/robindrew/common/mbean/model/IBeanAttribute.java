package com.robindrew.common.mbean.model;

public interface IBeanAttribute extends Comparable<IBeanAttribute> {

	IBean getBean();

	String getName();

	boolean isAvailable();

	boolean isReadable();

	boolean isWritable();

	Object getValue();

	void setValue(Object value);

	Class<?> getType();

}
