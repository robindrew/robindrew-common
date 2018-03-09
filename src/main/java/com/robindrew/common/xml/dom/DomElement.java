package com.robindrew.common.xml.dom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.robindrew.common.xml.XmlBuilder;

public class DomElement implements IDomElement {

	private final Node node;

	public DomElement(Node node) {
		this.node = node;
	}

	@Override
	public String getName() {
		return node.getNodeName();
	}

	@Override
	public String getValue() {
		String value = node.getNodeValue();
		if (value != null) {
			return value;
		}

		// A single text node is a value too
		NodeList nodeList = node.getChildNodes();
		if (nodeList == null || nodeList.getLength() != 1) {
			return null;
		}
		Node item = nodeList.item(0);
		if (item.getNodeType() == Node.TEXT_NODE) {
			return item.getNodeValue();
		}
		return null;
	}

	@Override
	public boolean hasValue() {
		return getValue() != null;
	}

	@Override
	public int attributes() {
		NamedNodeMap attributes = node.getAttributes();
		if (attributes == null) {
			return 0;
		}
		return attributes.getLength();
	}

	@Override
	public boolean hasAttributes() {
		return attributes() > 0;
	}

	@Override
	public int children() {
		NodeList nodeList = node.getChildNodes();
		if (nodeList == null) {
			return 0;
		}
		int count = 0;
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node item = nodeList.item(i);
			if (item.getNodeType() == Node.ELEMENT_NODE) {
				count++;
			}
		}
		return count;
	}

	@Override
	public boolean hasChildren() {
		return children() > 0;
	}

	@Override
	public boolean isEmpty() {
		return !hasValue() && !hasChildren();
	}

	@Override
	public List<IDomAttribute> getAttributeList() {
		NamedNodeMap attributes = node.getAttributes();
		if (attributes == null || attributes.getLength() == 0) {
			return Collections.emptyList();
		}
		List<IDomAttribute> list = new ArrayList<IDomAttribute>(attributes.getLength());
		for (int i = 0; i < attributes.getLength(); i++) {
			Node item = attributes.item(i);
			list.add(new DomAttribute(item));
		}
		return list;
	}

	@Override
	public IDomAttribute getFirstAttribute(String name) {
		NamedNodeMap attributes = node.getAttributes();
		if (attributes == null || attributes.getLength() == 0) {
			return null;
		}
		for (int i = 0; i < attributes.getLength(); i++) {
			Node item = attributes.item(i);
			if (item.getNodeName().equals(name)) {
				return new DomAttribute(item);
			}
		}
		return null;
	}

	@Override
	public List<IDomAttribute> getAttributeList(String name) {
		NamedNodeMap attributes = node.getAttributes();
		if (attributes == null || attributes.getLength() == 0) {
			return Collections.emptyList();
		}
		List<IDomAttribute> list = new ArrayList<IDomAttribute>(attributes.getLength());
		for (int i = 0; i < attributes.getLength(); i++) {
			Node item = attributes.item(i);
			if (item.getNodeName().equals(name)) {
				list.add(new DomAttribute(item));
			}
		}
		return list;
	}

	@Override
	public List<IDomElement> getChildList() {
		NodeList nodeList = node.getChildNodes();
		if (nodeList == null || nodeList.getLength() == 0) {
			return Collections.emptyList();
		}
		List<IDomElement> list = new ArrayList<IDomElement>(nodeList.getLength());
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node item = nodeList.item(i);
			if (item.getNodeType() == Node.ELEMENT_NODE) {
				list.add(new DomElement(item));
			}
		}
		return list;
	}

	@Override
	public int attributes(String name) {
		NamedNodeMap attributes = node.getAttributes();
		if (attributes == null || attributes.getLength() == 0) {
			return 0;
		}
		int count = 0;
		for (int i = 0; i < attributes.getLength(); i++) {
			Node item = attributes.item(i);
			if (item.getNodeName().equals(name)) {
				count++;
			}
		}
		return count;
	}

	@Override
	public int children(String name) {
		NodeList nodeList = node.getChildNodes();
		if (nodeList == null || nodeList.getLength() == 0) {
			return 0;
		}
		int count = 0;
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node item = nodeList.item(i);
			if (item.getNodeType() == Node.ELEMENT_NODE) {
				if (item.getNodeName().equals(name)) {
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public IDomElement getFirstChild(String name) {
		NodeList nodeList = node.getChildNodes();
		if (nodeList == null || nodeList.getLength() == 0) {
			return null;
		}
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node item = nodeList.item(i);
			if (item.getNodeType() == Node.ELEMENT_NODE) {
				if (item.getNodeName().equals(name)) {
					return new DomElement(item);
				}
			}
		}
		return null;
	}

	@Override
	public List<IDomElement> getChildList(String name) {
		NodeList nodeList = node.getChildNodes();
		if (nodeList == null || nodeList.getLength() == 0) {
			return Collections.emptyList();
		}
		List<IDomElement> list = new ArrayList<IDomElement>(nodeList.getLength());
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node item = nodeList.item(i);
			if (item.getNodeType() == Node.ELEMENT_NODE) {
				if (item.getNodeName().equals(name)) {
					list.add(new DomElement(item));
				}
			}
		}
		return list;
	}

	@Override
	public String toString() {
		XmlBuilder builder = new XmlBuilder(false);
		appendTo(this, builder);
		return builder.toString();
	}

	private void appendTo(IDomElement element, XmlBuilder builder) {
		boolean empty = element.isEmpty();
		if (empty && !element.hasAttributes()) {
			builder.empty(element.getName());
			return;
		}

		builder.open(element.getName());
		for (IDomAttribute attribute : element.getAttributeList()) {
			builder.attribute(attribute.getName(), attribute.getValue());
		}
		if (empty) {
			builder.close();
			return;
		}
		String value = element.getValue();
		if (value != null) {
			builder.close(value);
			return;
		}
		for (IDomElement child : element.getChildList()) {
			appendTo(child, builder);
		}
		builder.close();
	}
}
