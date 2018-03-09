package com.robindrew.common.mbean.annotated;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;

import com.robindrew.common.lang.clazz.ClassResolver;
import com.robindrew.common.lang.reflect.AnnotationUtil;
import com.robindrew.common.util.Java;

public class AnnotatedMBean extends StandardMBean {

	@SuppressWarnings("unchecked")
	private static final <T> Class<T> getInterface(T type) {
		String name = type.getClass().getSimpleName() + "MBean";
		Class<?>[] mbeanInterfaces = type.getClass().getInterfaces();
		for (Class<?> mbeanInterface : mbeanInterfaces) {
			if (mbeanInterface.getSimpleName().equals(name)) {
				return (Class<T>) mbeanInterface;
			}
		}
		throw new IllegalArgumentException("Unable to find MBean interface for class: " + type.getClass());
	}

	public <T> AnnotatedMBean(T type) throws NotCompliantMBeanException {
		super(type, getInterface(type));
	}

	protected Method getMethod(MBeanOperationInfo operation) throws Exception {
		Class<?> mbeanInterface = getMBeanInterface();
		String name = operation.getName();

		// Parameters
		MBeanParameterInfo[] signature = operation.getSignature();
		Class<?>[] types = new Class[signature.length];
		ClassResolver resolver = new ClassResolver();
		for (int i = 0; i < signature.length; i++) {
			types[i] = resolver.resolveClass(signature[i].getType());
		}
		return mbeanInterface.getMethod(name, types);
	}

	protected Method getMethod(MBeanAttributeInfo attribute) throws Exception {
		Class<?> mbeanInterface = getMBeanInterface();
		String name = attribute.getName();

		// Getter
		if (attribute.isReadable()) {
			if (attribute.getType().equalsIgnoreCase("boolean")) {
				return mbeanInterface.getMethod("is" + name, new Class<?>[0]);
			}
			return mbeanInterface.getMethod("get" + name, new Class<?>[0]);
		}

		// Setter
		Class<?> type = new ClassResolver().resolveClass(attribute.getType());
		return mbeanInterface.getMethod("set" + name, new Class<?>[] { type });
	}

	@Override
	protected String getDescription(MBeanOperationInfo operation) {
		try {
			Method method = getMethod(operation);
			Description annotation = method.getAnnotation(Description.class);
			if (annotation != null) {
				return annotation.value();
			}
		} catch (Exception e) {
			throw Java.propagate(e);
		}
		return super.getDescription(operation);
	}

	@Override
	protected String getDescription(MBeanAttributeInfo attribute) {
		try {
			Method method = getMethod(attribute);
			Description annotation = method.getAnnotation(Description.class);
			if (annotation != null) {
				return annotation.value();
			}
		} catch (Exception e) {
			throw Java.propagate(e);
		}
		return super.getDescription(attribute);
	}

	@Override
	protected String getParameterName(MBeanOperationInfo operation, MBeanParameterInfo parameter, int index) {
		try {
			Method method = getMethod(operation);
			Annotation[] annotations = method.getParameterAnnotations()[index];
			Name annotation = AnnotationUtil.getAnnotation(annotations, Name.class);
			if (annotation != null) {
				return annotation.value();
			}
		} catch (Exception e) {
			throw Java.propagate(e);
		}
		return super.getParameterName(operation, parameter, index);
	}

}