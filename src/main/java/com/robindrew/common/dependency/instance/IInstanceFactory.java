package com.robindrew.common.dependency.instance;

public interface IInstanceFactory<I> {

	I newInstance(Object... args);

}
