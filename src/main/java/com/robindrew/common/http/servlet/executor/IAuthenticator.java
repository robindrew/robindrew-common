package com.robindrew.common.http.servlet.executor;

import java.util.Optional;

public interface IAuthenticator {

	Optional<String> getToken(String username, String password);

	boolean checkToken(String token);
}
