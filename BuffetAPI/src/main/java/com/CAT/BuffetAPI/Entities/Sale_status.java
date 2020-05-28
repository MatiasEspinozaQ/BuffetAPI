package com.CAT.BuffetAPI.Entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Sale_status {
	@Id
	private String sale_status_id;
	private String name;
	private Date updated_at;
	private Date created_at;
	private boolean deleted;
	
	public String getSale_status_id() {
		return sale_status_id;
	}
	public void setSale_status_id(String sale_status_id) {
		this.sale_status_id = sale_status_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
}
