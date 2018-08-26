package com.robindrew.common.mbean.model;

import static com.google.common.base.Throwables.getRootCause;

import javax.management.Attribute;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.lang.clazz.ClassResolver;
import com.robindrew.common.util.Java;

public class BeanAttribute implements IBeanAttribute {

	private static final Logger log = LoggerFactory.getLogger(BeanAttribute.class);

	private final IBean bean;
	private final MBeanAttributeInfo attribute;

	public BeanAttribute(IBean bean, MBeanAttributeInfo attribute) {
		this.bean = bean;
		this.attribute = attribute;
	}

	@Override
	public String getName() {
		return attribute.getName();
	}

	@Override
	public Class<?> getType() {
		return new ClassResolver().resolveClass(attribute.getType());
	}

	@Override
	public Object getValue() {
		try {
			MBeanServer server = bean.getServer();
			ObjectName name = bean.getObjectName();
			return server.getAttribute(name, getName());
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public void setValue(Object value) {
		try {
			MBeanServer server = bean.getServer();
			ObjectName name = bean.getObjectName();
			Attribute attr = new Attribute(getName(), value);
			server.setAttribute(name, attr);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public int compareTo(IBeanAttribute compare) {
		return getName().compareTo(compare.getName());
	}

	@Override
	public IBean getBean() {
		return bean;
	}

	@Override
	public boolean isAvailable() {
		try {
			getValue();
			return true;
		} catch (Exception e) {
			Throwable root = getRootCause(e);
			if (!(root instanceof UnsupportedOperationException)) {
				log.warn("Attribute not available: " + getBean() + "." + getName(), e);
			}
			return false;
		}
	}

	@Override
	public boolean isReadable() {
		return attribute.isReadable();
	}

	@Override
	public boolean isWritable() {
		return attribute.isWritable();
	}

}
