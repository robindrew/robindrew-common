package com.robindrew.common.mbean.model;

import java.util.Collection;
import java.util.List;

public interface IBeanOperation extends Comparable<IBeanOperation> {

	IBean getBean();

	String getName();

	int parameters();

	List<IBeanParameter> getParameters();

	Object invoke(Object[] parameters);

	Object invoke(Collection<?> parameters);

	String getDescription();

	Class<?> getReturnType();

}
