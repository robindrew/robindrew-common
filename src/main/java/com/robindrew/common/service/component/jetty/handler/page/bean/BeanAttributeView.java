package com.robindrew.common.service.component.jetty.handler.page.bean;

import java.util.ArrayList;
import java.util.List;

import com.robindrew.common.mbean.model.IBean;
import com.robindrew.common.mbean.model.IBeanAttribute;

public class BeanAttributeView {

	public static String getTypeName(Class<?> type) {
		if (type.isArray()) {
			int dimensions = 1;
			Class<?> component = type.getComponentType();
			while (component.isArray()) {
				dimensions++;
				component = component.getComponentType();
			}
			StringBuilder name = new StringBuilder();
			name.append(component.getName());
			for (int i = 0; i < dimensions; i++) {
				name.append("[]");
			}
			return name.toString();
		}
		return type.getName();
	}

	public static List<BeanAttributeView> getAttributes(IBean bean) {
		List<BeanAttributeView> list = new ArrayList<>();
		for (IBeanAttribute attribute : bean.getAttributes()) {
			list.add(new BeanAttributeView(attribute));
		}
		return list;
	}

	private final IBeanAttribute attribute;

	public BeanAttributeView(IBeanAttribute attribute) {
		this.attribute = attribute;
	}

	public IBeanAttribute getAttribute() {
		return attribute;
	}

	public String getName() {
		return attribute.getName();
	}

	public String getTypeName() {
		return getTypeName(getType());
	}

	public boolean isNull() {
		return isAvailable() && getValue() == null;
	}

	public boolean isString() {
		return isAvailable() && getValue() instanceof String;
	}

	public boolean isSimpleType() {
		if (!isAvailable()) {
			return true;
		}

		Class<?> type = getType();
		if (type.isPrimitive()) {
			return true;
		}
		if (type.equals(String.class)) {
			String value = (String) getValue();
			return value.length() < 200;
		}
		if (Enum.class.isAssignableFrom(type)) {
			return true;
		}
		return false;
	}

	public boolean isAvailable() {
		return attribute.isAvailable();
	}

	public Object getValue() {
		return attribute.getValue();
	}

	public Class<?> getType() {
		return attribute.getType();
	}

}
