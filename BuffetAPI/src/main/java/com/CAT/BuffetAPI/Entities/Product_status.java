package com.CAT.BuffetAPI.Entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product_status {
	@Id
	private String status_id;
	private String status;
	private Date create_at;
	private Date update_at;
	private boolean deleted;
	
	public String getPublic_status_id() {
		return status_id;
	}
	public void setPublic_status_id(String public_status_id) {
		this.status_id = public_status_id;
	}
	public String getStatus_name() {
		return status;
	}
	public void setStatus_name(String status_name) {
		this.status = status_name;
	}
	public Date getCreated_at() {
		return create_at;
	}
	public void setCreated_at(Date created_at) {
		this.create_at = created_at;
	}
	public Date getUpdate_at() {
		return update_at;
	}
	public void setUpdate_at(Date update_at) {
		this.update_at = update_at;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}


}
