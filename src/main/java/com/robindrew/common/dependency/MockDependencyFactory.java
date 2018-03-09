package com.robindrew.common.dependency;

import org.mockito.Mockito;

import com.robindrew.common.dependency.locator.IDependencyLocator;

public class MockDependencyFactory {

	@SuppressWarnings("unchecked")
	public static <C, I extends C> I setMockDependency(Class<C> clazz) {
		IDependencyLocator locator = DependencyFactory.getInstance();
		I instance = (I) Mockito.mock(clazz);
		locator.set(clazz, instance);
		return instance;
	}
}
