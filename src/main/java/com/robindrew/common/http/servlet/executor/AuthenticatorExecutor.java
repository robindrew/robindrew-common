package com.robindrew.common.http.servlet.executor;

import java.util.List;
import java.util.Optional;

import com.robindrew.common.http.servlet.request.IHttpRequest;
import com.robindrew.common.http.servlet.response.IHttpResponse;
import com.robindrew.common.http.servlet.response.exception.HttpUnauthorizedException;

public abstract class AuthenticatorExecutor implements IHttpExecutor {

	private volatile IAuthenticator authenticator;

	protected IAuthenticator getAuthenticator() {
		return authenticator;
	}

	protected String getTokenKey() {
		return "AUTH_TOKEN";
	}

	protected String getPasswordKey() {
		return "AUTH_PASSWORD";
	}

	protected String getUsernameKey() {
		return "AUTH_USERNAME";
	}

	protected void authenticate(IHttpRequest request, IHttpResponse response) {
		IAuthenticator authenticator = getAuthenticator();

		// Authentication succeeds if no authenticator set
		if (authenticator == null) {
			return;
		}

		// Check the cookies
		List<String> tokens = request.getCookies(getTokenKey());
		for (String token : tokens) {
			if (authenticator.checkToken(token)) {
				return;
			}
		}

		String username = request.get(getUsernameKey(), "");
		String password = request.get(getPasswordKey(), "");
		if (username.isEmpty() || password.isEmpty()) {
			throw new HttpUnauthorizedException();
		}

		Optional<String> token = authenticator.getToken(username, password);
		if (!token.isPresent()) {
			throw new HttpUnauthorizedException();
		}

		response.setCookie(getTokenKey(), token.get());
	}

}
