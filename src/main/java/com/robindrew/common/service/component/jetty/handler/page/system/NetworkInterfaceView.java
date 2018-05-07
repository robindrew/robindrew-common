package com.robindrew.common.service.component.jetty.handler.page.system;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class NetworkInterfaceView {

	private final NetworkInterface network;

	public NetworkInterfaceView(NetworkInterface network) {
		this.network = network;
	}

	public String getName() {
		return network.getName();
	}

	public String getDisplayName() {
		return network.getDisplayName();
	}

	public List<? extends InetAddress> getAddressList() {
		return Collections.list(network.getInetAddresses());
	}

}
