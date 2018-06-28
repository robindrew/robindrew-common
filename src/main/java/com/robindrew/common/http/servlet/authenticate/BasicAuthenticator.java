package com.robindrew.common.http.servlet.authenticate;

import static com.robindrew.common.util.Check.notNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicAuthenticator implements IBasicAuthenticator {

	private static final Logger log = LoggerFactory.getLogger(BasicAuthenticator.class);

	private final Map<String, IBasicAuthentication> authMap = new ConcurrentHashMap<>();

	@Override
	public boolean isEnabled() {
		// Authentication is only enabled if credentials have been added...
		return !authMap.isEmpty();
	}

	public void addAuthentication(IBasicAuthentication auth) {
		notNull("auth", auth);
		authMap.put(auth.getUsername(), auth);
	}

	@Override
	public boolean authenticate(IBasicAuthentication auth) {
		if (!isEnabled()) {
			return true;
		}

		IBasicAuthentication expected = authMap.get(auth.getUsername());
		if (expected == null || !expected.getPassword().equals(auth.getPassword())) {
			log.warn("[Authenitcation Failed] {}", auth.getUsername());
			return false;
		}

		log.info("[Authenitcated Succeeded] {}", auth.getUsername());
		return true;
	}

}
