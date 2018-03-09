package com.robindrew.common.http.servlet.authenticate;

import com.robindrew.common.util.Check;

public class BasicAuthentication implements IBasicAuthentication {

	private final String username;
	private final String password;

	public BasicAuthentication(String username, String password) {
		this.username = Check.notNull("username", username);
		this.password = Check.notNull("password", password);
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

}
