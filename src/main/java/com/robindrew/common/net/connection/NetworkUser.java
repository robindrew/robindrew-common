package com.robindrew.common.net.connection;

public class NetworkUser implements INetworkUser {

	private final INetworkAddress address;
	private final String username;
	private final String password;

	public NetworkUser(INetworkAddress address, String username, String password) {
		if (address == null) {
			throw new NullPointerException("address");
		}
		if (username.isEmpty()) {
			throw new IllegalArgumentException("username is empty");
		}
		this.address = address;
		this.username = username;
		this.password = password;
	}

	@Override
	public INetworkAddress getAddress() {
		return address;
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
