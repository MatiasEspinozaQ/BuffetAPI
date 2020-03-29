package com.CAT.BuffetAPI.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User_status {
	
	@Id
	private long status_id;
	private String status;
	
	public long getStatus_id() {
		return status_id;
	}
	public void setStatus_id(long status_id) {
		this.status_id = status_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
		
}
