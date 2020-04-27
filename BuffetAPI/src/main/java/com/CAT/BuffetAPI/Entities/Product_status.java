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

	public String getStatus_id() {
		return this.status_id;
	}

	public void setStatus_id(String status_id) {
		this.status_id = status_id;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreate_at() {
		return this.create_at;
	}

	public void setCreate_at(Date create_at) {
		this.create_at = create_at;
	}

	public Date getUpdate_at() {
		return this.update_at;
	}

	public void setUpdate_at(Date update_at) {
		this.update_at = update_at;
	}

	public boolean isDeleted() {
		return this.deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}


}
