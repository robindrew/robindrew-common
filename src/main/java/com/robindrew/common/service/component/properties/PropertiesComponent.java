package com.robindrew.common.service.component.properties;

import static com.robindrew.common.properties.map.CompositePropertyMap.newComposite;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.io.Resources;
import com.robindrew.common.properties.PropertiesReader;
import com.robindrew.common.properties.map.IPropertyMap;
import com.robindrew.common.properties.map.PropertyMap;
import com.robindrew.common.properties.map.type.IProperty;
import com.robindrew.common.properties.map.type.StringProperty;
import com.robindrew.common.service.Services;
import com.robindrew.common.service.component.AbstractIdleComponent;
import com.robindrew.common.util.Java;

/**
 * The properties component requires a properties file for configuration.
 */
public class PropertiesComponent extends AbstractIdleComponent {

	private static final Logger log = LoggerFactory.getLogger(PropertiesComponent.class);

	private static final IProperty<String> propertiesPath = new StringProperty("service.properties.path");

	private static final String DEFAULT_PATH_PREFIX = "config/instance";
	private static final String DEFAULT_PATH_SUFFIX = ".properties";

	@Override
	protected void startupComponent() throws Exception {

		// Load the service properties
		Map<String, String> map = loadServiceProperties();

		// Build the new global property map
		if (!map.isEmpty()) {
			IPropertyMap propertyMap = new PropertyMap("Service", map);
			IPropertyMap globalProperties = newComposite("Global").withSystem().with(propertyMap).withEnv();
			PropertyMap.setPropertyMap(globalProperties);
		}
	}

	private Map<String, String> loadServiceProperties() {

		// Properties file specified on command line?
		String path = propertiesPath.get(null);
		if (path != null) {
			log.info("Loading properties from specified " + path);
			return PropertiesReader.readFromResource(path);
		}

		// Default resource path
		String basePath = DEFAULT_PATH_PREFIX + Services.getServiceInstance() + "/";

		// Default to ${host_address}.properties
		path = basePath + Java.getHostAddress() + DEFAULT_PATH_SUFFIX;
		if (Resources.exists(path)) {
			log.info("Loading properties from default path (host-specific) " + path);
			return PropertiesReader.readFromResource(path);
		}
		log.warn("Properties file not found: {}", path);

		// Finally fallback to common.properties
		path = basePath + "common" + DEFAULT_PATH_SUFFIX;
		log.info("Loading properties from default path (host-specific) " + path);
		return PropertiesReader.readFromResource(path);
	}

	@Override
	protected void shutdownComponent() throws Exception {
	}

}
