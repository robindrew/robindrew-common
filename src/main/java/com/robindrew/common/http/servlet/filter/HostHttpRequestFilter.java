package com.robindrew.common.http.servlet.filter;

import static com.robindrew.common.http.servlet.filter.NetworkHosts.getNetworkHosts;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

import com.robindrew.common.collect.copyonwrite.CopyOnWriteSet;
import com.robindrew.common.http.servlet.request.IHttpRequest;

public class HostHttpRequestFilter implements IHttpRequestFilter {

	private final AtomicBoolean enabled = new AtomicBoolean(true);
	private final Set<String> validSet = new CopyOnWriteSet<>();

	@Override
	public boolean isEnabled() {
		return enabled.get();
	}

	@Override
	public void setEnabled(boolean enable) {
		enabled.set(enable);
	}

	public Set<String> getAllValid() {
		Set<String> set = new TreeSet<>();
		set.addAll(getNetworkHosts().getAll());
		set.addAll(validSet);
		return set;
	}

	public HostHttpRequestFilter addValid(String addressOrHost) {
		validSet.add(addressOrHost);
		return this;
	}

	public HostHttpRequestFilter addValid(Iterable<? extends String> addressOrHosts) {
		for (String addressOrHost : addressOrHosts) {
			addValid(addressOrHost);
		}
		return this;
	}

	public HostHttpRequestFilter removeValid(String addressOrHost) {
		validSet.remove(addressOrHost);
		return this;
	}

	public HostHttpRequestFilter removeValid(Iterable<? extends String> addressOrHosts) {
		for (String addressOrHost : addressOrHosts) {
			removeValid(addressOrHost);
		}
		return this;
	}

	public boolean isValid(String addressOrHost) {
		addressOrHost = NetworkHosts.clean(addressOrHost);
		return validSet.contains(addressOrHost) || getNetworkHosts().contains(addressOrHost);
	}

	@Override
	public boolean isValid(IHttpRequest request) {

		// First check the address (might avoid host resolution)
		String address = request.getRemoteAddr();
		if (isValid(address)) {
			return true;
		}

		// Now check the host (might avoid host resolution)
		String host = request.getRemoteHost();
		if (isValid(host)) {
			return true;
		}

		return false;
	}

}
