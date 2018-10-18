package com.robindrew.common.http.servlet.filter;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;
import com.robindrew.common.collect.copyonwrite.CopyOnWriteSet;
import com.robindrew.common.http.servlet.request.IHttpRequest;

public class HostHttpRequestFilter implements IHttpRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(HostHttpRequestFilter.class);

	private static final Set<String> DEFAULT_VALID_SET = createDefaultValidSet();

	private static Set<String> createDefaultValidSet() {
		Set<String> set = new LinkedHashSet<>();
		set.add("0:0:0:0:0:0:0:1");
		set.add("127.0.0.1");
		set.add("localhost");

		try {
			try {
				Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
				for (NetworkInterface network : Collections.list(nets)) {
					Enumeration<InetAddress> addresses = network.getInetAddresses();
					while (addresses.hasMoreElements()) {
						InetAddress element = addresses.nextElement();
						set.add(clean(element.getHostAddress()));
						set.add(clean(element.getHostName()));
					}
				}
			} catch (Exception e) {
				log.info("Unable to list network interfaces", e);
			}

		} catch (Exception e) {
			log.warn("Error resolving host", e);
		}
		return ImmutableSet.copyOf(set);
	}

	private static String clean(String address) {
		int percent = address.indexOf('%');
		if (percent != -1) {
			address = address.substring(0, percent);
		}
		return address;
	}

	private final AtomicBoolean enabled = new AtomicBoolean(true);
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
		addressOrHost = clean(addressOrHost);
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
		String host = request.getRemoteHost();
		if (isValid(host)) {
			return true;
		}

		return false;
	}

}
