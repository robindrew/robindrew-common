package com.robindrew.common.lang.reflect;

import java.lang.annotation.Annotation;

/**
 * A collection of annotation utility methods.
 */
public final class AnnotationUtil {

	/**
	 * Utility constructor.
	 */
	private AnnotationUtil() {
	}

	/**
	 * Find an annotation from the given annotation array.
	 * @param annotations the annotations.
	 * @param clazz the annotation class.
	 * @return the annotation instance.
	 */
	@SuppressWarnings("unchecked")
	public static final <A extends Annotation> A getAnnotation(Annotation[] annotations, Class<A> clazz) {
		if (annotations != null) {
			for (Annotation annotation : annotations) {
				if (clazz.equals(annotation.annotationType())) {
					return (A) annotation;
				}
			}
		}
		return null;
	}

}
