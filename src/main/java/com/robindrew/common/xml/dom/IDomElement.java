package com.robindrew.common.xml.dom;

import java.util.List;

public interface IDomElement {

	String getName();

	String getValue();

	boolean hasValue();

	boolean isEmpty();

	int attributes();

	int attributes(String name);

	boolean hasAttributes();

	int children();

	int children(String name);

	boolean hasChildren();

	IDomAttribute getFirstAttribute(String name);

	List<IDomAttribute> getAttributeList();

	List<IDomAttribute> getAttributeList(String name);

	IDomElement getFirstChild(String name);

	List<IDomElement> getChildList();

	List<IDomElement> getChildList(String name);

}
