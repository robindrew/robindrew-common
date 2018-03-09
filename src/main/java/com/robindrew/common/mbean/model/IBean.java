package com.robindrew.common.mbean.model;

import java.util.Set;

import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

public interface IBean extends Comparable<IBean> {

	int getId();

	String getDomain();

	String getType();

	String getName();

	boolean hasName();

	Class<?> getTypeClass();

	Set<IBeanAttribute> getAttributes();

	Set<IBeanOperation> getOperations();

	IBeanAttribute getAttribute(int index);

	IBeanAttribute getAttribute(String name);

	IBeanOperation getOperation(int index);

	boolean isStandard();

	MBeanInfo getInfo();

	ObjectName getObjectName();

	MBeanServer getServer();

	ObjectInstance getInstance();

}
