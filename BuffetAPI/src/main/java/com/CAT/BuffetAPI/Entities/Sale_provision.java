package com.CAT.BuffetAPI.Entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Sale_provision {
	@Id
	private String provision_id;
    private String sale_id;
    private String product_id;
    private String serv_id;
    private int quantity;
    private int unit_price;
    private int total;
    private Date updated_at;
    private Date created_at;
    private boolean deleted;
	
    public String getProvision_id() {
		return provision_id;
	}
	public void setProvision_id(String provision_id) {
		this.provision_id = provision_id;
	}
	public String getSale_id() {
		return sale_id;
	}
	public void setSale_id(String sale_id) {
		this.sale_id = sale_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getServ_id() {
		return serv_id;
	}
	public void setServ_id(String serv_id) {
		this.serv_id = serv_id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getUnit_price() {
		return unit_price;
	}
	public void setUnit_price(int unit_price) {
		this.unit_price = unit_price;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
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
