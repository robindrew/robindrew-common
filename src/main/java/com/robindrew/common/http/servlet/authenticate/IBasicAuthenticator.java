package com.robindrew.common.http.servlet.authenticate;

public interface IBasicAuthenticator {

	/**
	 * Returns true if basic authentication is enabled.
	 * @return true if basic authentication is enabled.
	 */
	boolean isEnabled();

	/**
	 * Returns true if and only if the authentication details are valid and authorized.
	 * @param authentication the authentication.
	 * @return true if authorized.
	 */
	boolean authenticate(IBasicAuthentication authentication);

}
