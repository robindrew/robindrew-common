package com.robindrew.common.service.component.jetty.handler;

import static com.robindrew.common.text.Strings.getStackTrace;
import static com.robindrew.common.util.Check.notNull;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.http.HttpMethod;
import com.robindrew.common.http.servlet.executor.IHttpExecutor;
import com.robindrew.common.http.servlet.executor.ResourceDirectoryExecutor;
import com.robindrew.common.http.servlet.matcher.CompositeMatcher;
import com.robindrew.common.http.servlet.matcher.DomainMatcher;
import com.robindrew.common.http.servlet.matcher.DomainRegexMatcher;
import com.robindrew.common.http.servlet.matcher.IHttpRequestMatcher;
import com.robindrew.common.http.servlet.matcher.MethodMatcher;
import com.robindrew.common.http.servlet.matcher.UriMatcher;
import com.robindrew.common.http.servlet.matcher.UriRegexMatcher;
import com.robindrew.common.http.servlet.request.HttpRequest;
import com.robindrew.common.http.servlet.request.IHttpRequest;
import com.robindrew.common.http.servlet.response.HttpResponse;
import com.robindrew.common.http.servlet.response.IHttpResponse;
import com.robindrew.common.http.servlet.response.exception.HttpResponseException;
import com.robindrew.common.util.Check;

public class MatcherHttpHandler extends AbstractHandler {

	private static final Logger log = LoggerFactory.getLogger(MatcherHttpHandler.class);

	private final Map<IHttpRequestMatcher, IHttpExecutor> matcherToHandlerMap = new ConcurrentHashMap<>();

	/**
	 * Register a handler to handle HTTP requests.
	 */
	public void register(IHttpRequestMatcher matcher, IHttpExecutor handler) {
		Check.notNull("matcher", matcher);
		Check.notNull("handler", handler);

		matcherToHandlerMap.put(matcher, handler);
		log.info("[Register] {} -> {}", matcher, handler);
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		IHttpRequest httpRequest = new HttpRequest(request);
		IHttpResponse httpResponse = new HttpResponse(response);

		// Lookup the handler
		IHttpExecutor handler = getHandler(httpRequest);
		if (handler == null) {
			httpResponse.notFound();
			return;
		}

		// Handle the request/response
		try {
			handler.execute(httpRequest, httpResponse);
		} catch (HttpResponseException hre) {
			hre.populate(httpResponse);
			httpResponse.getOutputStream().flush();
		} catch (Throwable t) {
			handleThrowable(httpRequest, httpResponse, t);
		}
	}

	protected void handleThrowable(IHttpRequest request, IHttpResponse response, Throwable t) {

		// Build the exception
		StringBuilder error = new StringBuilder();

		// Request line
		error.append(request.getMethod()).append(' ');
		error.append(request.getRequestURI());
		String query = request.getQueryString();
		if (query != null) {
			error.append('?').append(query);
		}
		error.append(' ');
		error.append(request.getProtocol()).append("\r\n");

		// Headers
		for (Entry<String, String> header : request.getHeaderMap().entrySet()) {
			error.append(header.getKey()).append(": ");
			error.append(header.getValue()).append("\r\n");
		}

		error.append("\r\n");
		error.append(getStackTrace(t));

		response.internalServerError(error.toString());
	}

	protected IHttpExecutor getHandler(IHttpRequest request) {
		for (Entry<IHttpRequestMatcher, IHttpExecutor> entry : matcherToHandlerMap.entrySet()) {
			IHttpRequestMatcher matcher = entry.getKey();
			if (matcher.matches(request)) {
				return entry.getValue();
			}
		}
		return null;
	}

	public ExecutorBuilder register() {
		return new ExecutorBuilder();
	}

	public void uri(String uri, IHttpExecutor executor) {
		register().uri(uri).executor(executor);
	}

	public void uriPattern(String uriRegex, IHttpExecutor executor) {
		register().uriPattern(uriRegex).executor(executor);
	}

	public void resources(String uriRegex, String directory) {
		register().uriPattern(uriRegex).resourceDirectory(directory);
	}

	public class ExecutorBuilder {

		private final Set<IHttpRequestMatcher> matcherSet = new LinkedHashSet<>();

		private ExecutorBuilder matcher(IHttpRequestMatcher matcher) {
			matcherSet.add(notNull("matcher", matcher));
			return this;
		}

		public void resourceDirectory(String directory) {
			executor(new ResourceDirectoryExecutor(directory));
		}

		public void executor(IHttpExecutor executor) {
			notNull("executor", executor);

			IHttpRequestMatcher matcher = CompositeMatcher.of(matcherSet);
			register(matcher, executor);
		}

		public ExecutorBuilder method(String method) {
			return matcher(new MethodMatcher(method));
		}

		public ExecutorBuilder method(HttpMethod method) {
			return matcher(new MethodMatcher(method));
		}

		public ExecutorBuilder domain(String domain) {
			return matcher(new DomainMatcher(domain));
		}

		public ExecutorBuilder domainPattern(String domainRegex) {
			return matcher(new DomainRegexMatcher(domainRegex));
		}

		public ExecutorBuilder uri(String uri) {
			return matcher(new UriMatcher(uri));
		}

		public ExecutorBuilder uriPattern(String uriRegex) {
			return matcher(new UriRegexMatcher(uriRegex));
		}

	}
}
