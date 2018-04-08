package com.robindrew.common.service;

import com.robindrew.common.properties.map.type.IProperty;
import com.robindrew.common.properties.map.type.IntegerProperty;
import com.robindrew.common.properties.map.type.StringProperty;
import com.robindrew.common.util.Check;

/**
 * The services utility class.
 */
public class Services {

	/** The service name. */
	private static final StringProperty serviceName = new StringProperty("service.name");
	/** The service port. */
	private static final IProperty<Integer> servicePort = new IntegerProperty("service.port");
	/** The service instance. */
	private static final IProperty<Integer> serviceInstance = new IntegerProperty("service.instance").defaultValue(1);
	/** The service environment. */
	private static final IProperty<String> serviceEnv = new StringProperty("service.env").defaultValue("DEV");

	private Services() {
		// Utility class - private constructor.
	}

	/**
	 * Returns the service name.
	 */
	public static final String getServiceName() {
		return serviceName.get();
	}

	/**
	 * Returns the service environment.
	 */
	public static final String getServiceEnv() {
		return serviceEnv.get();
	}

	/**
	 * Returns the service port.
	 */
	public static int getServicePort() {
		return servicePort.get();
	}

	/**
	 * Returns a port offset to the base service port.
	 * @param offset the offset (0-9)
	 * @return the port.
	 */
	public static int getOffsetPort(int offset) {
		if (offset < 0 || offset > 9) {
			throw new IllegalArgumentException("offset=" + offset);
		}
		return getServiceInstance() + offset;
	}

	/**
	 * Returns the service instance.
	 */
	public static int getServiceInstance() {
		return serviceInstance.get();
	}

	public static String setServiceName(String name) {
		Check.notEmpty("name", name);
		serviceName.defaultValue(name);
		return getServiceName();
	}
}
