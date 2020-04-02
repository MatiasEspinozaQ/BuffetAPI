package com.CAT.BuffetAPI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailConfigurations {
	@Value ("${Spring.mail.host}")
	private String host;
	@Value ("${Spring.mail.port}")
	private int port;
	@Value ("${Spring.mail.username}")
	private String user;
	@Value ("${Spring.mail.password}")
	private String Password;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}

}
