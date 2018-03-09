package com.robindrew.common.http.servlet.authenticate;

import com.google.common.base.Optional;
import com.robindrew.common.http.servlet.Servlets;
import com.robindrew.common.http.servlet.executor.IHttpExecutor;
import com.robindrew.common.http.servlet.request.IHttpRequest;
import com.robindrew.common.http.servlet.response.IHttpResponse;
import com.robindrew.common.http.servlet.response.exception.HttpUnauthorizedException;
import com.robindrew.common.util.Check;

public class BasicAuthenticationExecutor implements IHttpExecutor {

	private final IBasicAuthenticator authenticator;

	public BasicAuthenticationExecutor(IBasicAuthenticator authenticator) {
		this.authenticator = Check.notNull("authenticator", authenticator);
	}

	@Override
	public void execute(IHttpRequest request, IHttpResponse response) {

		Optional<IBasicAuthentication> authentication = Servlets.getAuthentication(request);

		// Authentication missing?
		if (!authentication.isPresent()) {
			throw new HttpUnauthorizedException();
		}

		// Authentication failed?
		if (!authenticator.authenticate(authentication.get())) {
			throw new HttpUnauthorizedException();
		}

		// Success!
	}

}
