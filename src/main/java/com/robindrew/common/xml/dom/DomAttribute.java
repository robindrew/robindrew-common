package com.robindrew.common.xml.dom;

import org.w3c.dom.Node;

public class DomAttribute implements IDomAttribute {

	private final Node node;

	public DomAttribute(Node node) {
		this.node = node;
	}

	@Override
	public String getName() {
		return node.getNodeName();
	}

	@Override
	public String getValue() {
		return node.getNodeValue();
	}

	@Override
	public String toString() {
		return getName() + "='" + getValue() + "'";
	}

}
