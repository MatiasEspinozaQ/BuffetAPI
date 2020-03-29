package com.CAT.BuffetAPI.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User_type {
	
	@Id
	private long user_type_id;
	private String name;
	public long getUser_type_id() {
		return user_type_id;
	}
	public void setUser_type_id(long user_type_id) {
		this.user_type_id = user_type_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
