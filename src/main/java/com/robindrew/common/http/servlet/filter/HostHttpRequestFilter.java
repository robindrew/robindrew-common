package com.robindrew.common.http.servlet.filter;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.common.collect.ImmutableSet;
import com.robindrew.common.collect.copyonwrite.CopyOnWriteSet;
import com.robindrew.common.http.servlet.request.IHttpRequest;

public class HostHttpRequestFilter implements IHttpRequestFilter {

	private final AtomicBoolean enabled = new AtomicBoolean(true);

	private static final Set<String> DEFAULT_VALID_SET = ImmutableSet.of("0:0:0:0:0:0:0:1", "127.0.0.1", "localhost");

	private final Set<String> validSet = new CopyOnWriteSet<>();

	public HostHttpRequestFilter() {
		validSet.addAll(DEFAULT_VALID_SET);
	}

	@Override
	public boolean isEnabled() {
		return enabled.get();
	}

	@Override
	public void setEnabled(boolean enable) {
		enabled.set(enable);
	}

	public Set<String> getAllValid() {
		return new TreeSet<>(validSet);
	}

	public void addValid(String addressOrHost) {
		validSet.add(addressOrHost);
	}

	public void removeValid(String addressOrHost) {
		validSet.remove(addressOrHost);
	}

	public boolean isValid(String addressOrHost) {
		return validSet.contains(addressOrHost);
	}

	@Override
	public boolean isValid(IHttpRequest request) {

		// First check the address (might avoid host resolution)
		String address = request.getRemoteAddr();
		if (isValid(address)) {
			return true;
		}

		// Now check the host (might avoid host resolution)
		String host = request.getRemoteAddr();
		if (isValid(host)) {
			return true;
		}

		return false;
	}

}
