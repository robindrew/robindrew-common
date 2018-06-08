package com.robindrew.common.eclipse.project;

import org.simpleframework.xml.Element;

public class Variable {

	@Element
	private String name;
	@Element
	private String value;

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

}
