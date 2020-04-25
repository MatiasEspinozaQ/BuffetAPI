package com.CAT.BuffetAPI.Entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Service {
	@Id
	private String serv_id;	
	private String name;
	private int price;
	private String serv_desc;
	private int estimated_time;
	private String serv_status;
	private Date create_at;
	private Date update_at;
	private boolean deleted;
	public String getServ_id() {
		return serv_id;
	}
	public void setServ_id(String serv_id) {
		this.serv_id = serv_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getServ_desc() {
		return serv_desc;
	}
	public void setServ_desc(String serv_desc) {
		this.serv_desc = serv_desc;
	}
	public int getEstimated_time() {
		return estimated_time;
	}
	public void setEstimated_time(int estimated_time) {
		this.estimated_time = estimated_time;
	}
	public Date getCreate_at() {
		return create_at;
	}
	public void setCreate_at(Date create_at) {
		this.create_at = create_at;
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
	public String getServ_status() {
		return serv_status;
	}
	public void setServ_status(String serv_status) {
		this.serv_status = serv_status;
	}
	
	


}
