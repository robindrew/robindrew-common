package com.robindrew.common.xml.dom;

import com.robindrew.common.xml.IXmlBuilder;
import com.robindrew.common.xml.XmlBuilder;

public class DomFormatter implements IDomFormatter {

	@Override
	public String format(String xml) {
		return format(xml, new XmlBuilder(true));
	}

	@Override
	public String format(String xml, IXmlBuilder builder) {
		if (xml == null) {
			throw new NullPointerException("xml");
		}
		if (builder == null) {
			throw new NullPointerException("builder");
		}

		// Parse
		IDomParser parser = new DomParser();
		IDomElement element = parser.parse(xml);

		// Format
		format(builder, element);
		return builder.toString();
	}

	private void format(IXmlBuilder xml, IDomElement element) {

		// Empty
		if (element.isEmpty()) {
			xml.open(element.getName());
			attributes(xml, element);
			xml.close();
			return;
		}

		// Value
		if (element.hasValue()) {
			xml.open(element.getName());
			attributes(xml, element);
			xml.close(element.getValue());
			return;
		}

		// Open
		xml.open(element.getName());
		attributes(xml, element);
		for (IDomElement child : element.getChildList()) {
			format(xml, child);
		}
		xml.close();
	}

	private void attributes(IXmlBuilder xml, IDomElement element) {
		for (IDomAttribute attribute : element.getAttributeList()) {
			xml.attribute(attribute.getName(), attribute.getValue());
		}
	}

}
