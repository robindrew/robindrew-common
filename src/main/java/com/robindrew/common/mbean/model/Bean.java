package com.robindrew.common.mbean.model;

import java.util.Set;
import java.util.TreeSet;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import com.robindrew.common.mbean.MBeanRuntimeException;
import com.robindrew.common.util.Java;

public class Bean implements IBean {

	private final MBeanServer server;
	private final ObjectInstance instance;

	public Bean(MBeanServer server, ObjectInstance instance) {
		this.server = server;
		this.instance = instance;
	}

	@Override
	public MBeanServer getServer() {
		return server;
	}

	@Override
	public ObjectInstance getInstance() {
		return instance;
	}

	@Override
	public MBeanInfo getInfo() {
		try {
			return getServer().getMBeanInfo(getObjectName());
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public ObjectName getObjectName() {
		return getInstance().getObjectName();
	}

	@Override
	public String getDomain() {
		return getObjectName().getDomain();
	}

	@Override
	public int getId() {
		return System.identityHashCode(getInstance().getObjectName());
	}

	@Override
	public String getType() {
		return getObjectName().getKeyProperty("type");
	}

	@Override
	public String getName() {
		return getObjectName().getKeyProperty("name");
	}

	@Override
	public String toString() {
		StringBuilder name = new StringBuilder();
		name.append(getDomain()).append(".");
		name.append(getType());
		if (hasName()) {
			name.append(".").append(getName());
		}
		return name.toString();
	}

	@Override
	public int compareTo(IBean bean) {
		return toString().compareTo(bean.toString());
	}

	@Override
	public boolean hasName() {
		return getName() != null;
	}

	@Override
	public Set<IBeanAttribute> getAttributes() {
		Set<IBeanAttribute> set = new TreeSet<IBeanAttribute>();
		for (MBeanAttributeInfo attribute : getInfo().getAttributes()) {
			set.add(new BeanAttribute(this, attribute));
		}
		return set;
	}

	@Override
	public Class<?> getTypeClass() {
		try {
			String className = getInfo().getClassName() + "MBean";
			return Class.forName(className);
		} catch (Exception e) {
			throw new MBeanRuntimeException(e);
		}
	}

	@Override
	public Set<IBeanOperation> getOperations() {
		Set<IBeanOperation> set = new TreeSet<IBeanOperation>();
		for (MBeanOperationInfo operation : getInfo().getOperations()) {
			set.add(new BeanOperation(this, operation));
		}
		return set;
	}

	@Override
	public boolean isStandard() {
		String domain = getDomain();
		if (domain.startsWith("java.")) {
			return true;
		}
		if (domain.startsWith("com.sun.")) {
			return true;
		}
		if (domain.equals("JMImplementation")) {
			return true;
		}
		return false;
	}

	@Override
	public IBeanAttribute getAttribute(int index) {
		return getElement(getAttributes(), index);
	}

	@Override
	public IBeanOperation getOperation(int index) {
		return getElement(getOperations(), index);
	}

	private <E> E getElement(Set<E> set, int index) {
		int i = 0;
		for (E element : set) {
			if (i == index) {
				return element;
			}
			i++;
		}
		throw new IllegalArgumentException("index=" + index);
	}

	@Override
	public IBeanAttribute getAttribute(String name) {
		for (IBeanAttribute attribute : getAttributes()) {
			if (attribute.getName().equals(name)) {
				return attribute;
			}
		}
		throw new IllegalArgumentException("name: '" + name + "'");
	}

}
