package com.robindrew.common.http.servlet.executor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.io.ByteSource;
import com.robindrew.common.http.ContentType;
import com.robindrew.common.http.MimeType;
import com.robindrew.common.http.servlet.request.IHttpRequest;
import com.robindrew.common.http.servlet.response.IHttpResponse;
import com.robindrew.common.io.Resources;
import com.robindrew.common.util.Check;

public class ResourceDirectoryExecutor implements IHttpExecutor {

	private final String directory;
	private final Map<String, Resource> cache = new ConcurrentHashMap<>();

	public ResourceDirectoryExecutor(String directory) {
		this.directory = Check.notEmpty("directory", directory);
	}

	@Override
	public void execute(IHttpRequest request, IHttpResponse response) {
		String uri = request.getRequestURI();

		Resource resource = cache.get(uri);
		if (resource == null) {

			// File extension
			MimeType type = MimeType.withName(uri);

			// Locate the resources
			String resourceName = directory + uri;
			ByteSource source = Resources.toByteSource(resourceName);
			resource = new Resource(resourceName, type, source);

			cache.put(uri, resource);
		}

		// Success!
		response.ok(new ContentType(resource.getType()), resource.getSource());
	}

	private class Resource {

		private final String resourceName;
		private final MimeType type;
		private final ByteSource source;

		private Resource(String resourceName, MimeType type, ByteSource source) {
			this.resourceName = resourceName;
			this.type = type;
			this.source = source;
		}

		public MimeType getType() {
			return type;
		}

		public ByteSource getSource() {
			return source;
		}

		@Override
		public String toString() {
			return resourceName;
		}

	}

}
