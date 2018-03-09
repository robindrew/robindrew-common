package com.robindrew.common.mbean.annotated;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.mbean.IMBeanRegistry;
import com.robindrew.common.util.Check;
import com.robindrew.common.util.Java;

public class AnnotatedMBeanRegistry implements IMBeanRegistry {

	private static final Logger log = LoggerFactory.getLogger(AnnotatedMBeanRegistry.class);

	private static ObjectName getObjectName(Object object, String name) {
		try {
			String packageName = object.getClass().getPackage().getName();
			String className = object.getClass().getSimpleName();
			return new ObjectName(packageName + ":type=" + className + (name == null ? "" : (",name=" + name)));
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public void register(Object object) {
		register(object, (String) null);
	}

	@Override
	public void register(Object object, String name) {
		if (object == null) {
			throw new NullPointerException();
		}

		// Object Name
		ObjectName objectName = getObjectName(object, name);

		// Register
		register(object, objectName);
	}

	@Override
	public void register(Object object, ObjectName name) {
		Check.notNull("object", object);
		Check.notNull("name", name);

		try {

			// Annotated MBean
			Object mbean = new AnnotatedMBean(object);

			// Register
			MBeanServer server = ManagementFactory.getPlatformMBeanServer();
			server.registerMBean(mbean, name);
			log.info("Registered MBean: " + object + " (" + name + ")");

		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

}
