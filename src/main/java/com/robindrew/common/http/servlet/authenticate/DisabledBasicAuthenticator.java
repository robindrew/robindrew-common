package com.robindrew.common.http.servlet.authenticate;

public class DisabledBasicAuthenticator implements IBasicAuthenticator {

	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public boolean authenticate(IBasicAuthentication authentication) {
		return true;
	}

}
