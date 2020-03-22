package com.CAT.BuffetAPI;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cliente {
	@Id
	private long Cliente_id;
	private String Cliente_username;
	private String Cliente_Password;
	
	public long getId() {
		return Cliente_id;
	}
	public void setId(long id) {
		this.Cliente_id = id;
	}
	public String getUserName() {
		return Cliente_username;
	}
	public void setUserName(String userName) {
		this.Cliente_username = userName;
	}
	public String getPassword() {
		return Cliente_Password;
	}
	public void setPassword(String password) {
		Cliente_Password = password;
	}

}
