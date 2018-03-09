package com.robindrew.common.mbean;

import javax.management.ObjectName;

public interface IMBeanRegistry {

	void register(Object object, ObjectName name);

	void register(Object object, String name);

	void register(Object object);

}
