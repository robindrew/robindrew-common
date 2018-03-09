package com.robindrew.common.service.component.jetty.handler.page;

import java.util.LinkedHashMap;
import java.util.Map;

import com.robindrew.common.html.Bootstrap;
import com.robindrew.common.http.servlet.executor.IVelocityHttpContext;
import com.robindrew.common.http.servlet.request.IHttpRequest;
import com.robindrew.common.http.servlet.response.IHttpResponse;
import com.robindrew.common.text.Strings;
import com.robindrew.common.util.Check;

public class IndexPage extends AbstractServicePage {

	private final Map<String, Link> linkMap = new LinkedHashMap<>();

	public IndexPage(IVelocityHttpContext context, String templateName) {
		super(context, templateName);

		addLinks();
	}

	public void addLink(String name, String url, String color) {
		addLink(new Link(name, url, color));
	}

	public void addLink(Link link) {
		linkMap.put(link.getName(), link);
	}

	public void addLinks() {
		addLink("MBeans", "/BeanConsole", Bootstrap.COLOR_PRIMARY);
		addLink("System", "/System", Bootstrap.COLOR_PRIMARY);
	}

	@Override
	protected void execute(IHttpRequest request, IHttpResponse response, Map<String, Object> dataMap) {
		super.execute(request, response, dataMap);

		dataMap.put("linkMap", linkMap);
	}

	public static class Link {

		private final String name;
		private final String url;
		private final String color;

		public Link(String name, String url, String color) {
			this.name = Check.notEmpty("name", name);
			this.url = Check.notEmpty("url", url);
			this.color = Check.notEmpty("color", color);
		}

		public String getName() {
			return name;
		}

		public String getDisplayName() {
			return getName();
		}

		public String getHtmlName() {
			return getName().replace(' ', '_');
		}

		public String getUrl() {
			return url;
		}

		public String getColor() {
			return color;
		}

		@Override
		public String toString() {
			return Strings.toString(this);
		}

	}

}
