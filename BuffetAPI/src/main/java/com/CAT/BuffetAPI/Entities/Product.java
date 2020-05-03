package com.CAT.BuffetAPI.Entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Product {
	@Id
	@GeneratedValue(generator="db-guid")
	@GenericGenerator(name="db-guid", strategy = "guid") 
	private String product_id;
	private String name;
	private String product_desc;
	private String price;
	private int stock;
	private String stock_alert;
	private String brand;
	private String unit_id;
	private String product_status;
	private Date created_at;
	private Date updated_at;
	private boolean deleted;

	public String getProduct_id() {
		return this.product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProduct_desc() {
		return this.product_desc;
	}

	public void setProduct_desc(String product_desc) {
		this.product_desc = product_desc;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getStock() {
		return this.stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getStock_alert() {
		return this.stock_alert;
	}

	public void setStock_alert(String stock_alert) {
		this.stock_alert = stock_alert;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getUnit_id() {
		return this.unit_id;
	}

	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
	}

	public String getProduct_status() {
		return this.product_status;
	}

	public void setProduct_status(String product_status) {
		this.product_status = product_status;
	}

	public Date getCreated_at() {
		return this.created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return this.updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public boolean isDeleted() {
		return this.deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}
