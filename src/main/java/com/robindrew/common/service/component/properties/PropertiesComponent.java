package com.robindrew.common.service.component.properties;

import static com.robindrew.common.properties.map.CompositePropertyMap.newComposite;

import java.util.Map;

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

	private static final IProperty<String> propertiesPath = new StringProperty("service.properties.path");

	private static final String DEFAULT_PATH_PREFIX = "config/instance";
	private static final String DEFAULT_PATH_SUFFIX = ".properties";

	@Override
	protected void startupComponent() throws Exception {

		// Load the service properties
		String path = getPath();
		Map<String, String> map = PropertiesReader.readFromResource(path);

		// Build the new global property map
		if (!map.isEmpty()) {
			IPropertyMap propertyMap = new PropertyMap("Service", map);
			IPropertyMap globalProperties = newComposite("Global").withSystem().with(propertyMap).withEnv();
			PropertyMap.setPropertyMap(globalProperties);
		}
	}

	private String getPath() {
		String path = propertiesPath.get(null);
		if (path != null) {
			return path;
		}

		int instance = Services.getServiceInstance();

		// The default path ...
		return DEFAULT_PATH_PREFIX + instance + "/" + Java.getHostAddress() + DEFAULT_PATH_SUFFIX;
	}

	@Override
	protected void shutdownComponent() throws Exception {
	}

}
