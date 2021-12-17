package com.robindrew.common.http.servlet.filter;

import static java.net.NetworkInterface.getNetworkInterfaces;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

public class NetworkHosts extends Thread {

	private static final Logger log = LoggerFactory.getLogger(NetworkHosts.class);

	public static String clean(String address) {
		int percent = address.indexOf('%');
		if (percent != -1) {
			address = address.substring(0, percent);
		}
		return address;
	}

	private static final NetworkHosts instance = new NetworkHosts();

	public static NetworkHosts getNetworkHosts() {
		return instance;
	}

	private final Set<String> set = new CopyOnWriteArraySet<>();

	private NetworkHosts() {
		super("NetworkHostsResolver");
		set.add("0:0:0:0:0:0:0:1");
		set.add("127.0.0.1");
		set.add("localhost");
		start();
	}

	public boolean contains(String host) {
		return set.contains(host);
	}

	public void run() {
		try {
			Stopwatch timer = Stopwatch.createStarted();
			List<InetAddress> addressList = getAddressList();
			for (InetAddress address : addressList) {
				set.add(clean(address.getHostAddress()));
			}
			for (InetAddress address : addressList) {
				set.add(clean(address.getHostName()));
			}
			timer.stop();

			for (String host : set) {
				log.info("[Resolved] {}", host);
			}
			log.info("[Resolved] {} hosts in {}", addressList.size(), timer);
		} catch (Exception e) {
			log.warn("Error resolving host", e);
		}
	}

	private List<InetAddress> getAddressList() throws SocketException {
		List<InetAddress> list = new ArrayList<>();
		Enumeration<NetworkInterface> networks = getNetworkInterfaces();
		while (networks.hasMoreElements()) {
			NetworkInterface network = networks.nextElement();
			Enumeration<InetAddress> addresses = network.getInetAddresses();
			while (addresses.hasMoreElements()) {
				InetAddress address = addresses.nextElement();
				list.add(address);
			}
		}
		return list;
	}

	public Set<String> getAll() {
		return new LinkedHashSet<>(set);
	}

}
