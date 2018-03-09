package com.robindrew.common.template.velocity.map;

public interface IVelocityMap {

	void put(String name, String text);

	byte[] get(String name);

}
