package com.robindrew.common.lang.reflect.field;

import java.lang.annotation.Annotation;
import java.util.List;

public interface IFieldLister {

	IFieldLister setRecursive(boolean recursive);

	IFieldLister setAccessible(boolean accessible);

	IFieldLister includeStatic(boolean include);

	IFieldLister includeFinal(boolean include);

	IFieldLister includeAnnotation(Class<? extends Annotation> include);

	List<IField> getFieldList(Class<?> type);

}
