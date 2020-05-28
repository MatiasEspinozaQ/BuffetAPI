package com.CAT.BuffetAPI.Entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Sale {

	@Id
	private String sale_id;
	private String seller_id;
	private String cashier_id;
	private String appuser_id;
	private String sale_status_id;
	private Date date_order;
	private Date sale_date;
	private long code;
	private String payment_method;
	private int subtotal;
	private String total;
	private Date updated_at;
	private Date created_at;
	private boolean deleted;
	
	public String getSale_id() {
		return sale_id;
	}
	public void setSale_id(String sale_id) {
		this.sale_id = sale_id;
	}
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public String getCashier_id() {
		return cashier_id;
	}
	public void setCashier_id(String cashier_id) {
		this.cashier_id = cashier_id;
	}
	public String getAppuser_id() {
		return appuser_id;
	}
	public void setAppuser_id(String appuser_id) {
		this.appuser_id = appuser_id;
	}
	public String getSale_status_id() {
		return sale_status_id;
	}
	public void setSale_status_id(String sale_status_id) {
		this.sale_status_id = sale_status_id;
	}
	public Date getDate_order() {
		return date_order;
	}
	public void setDate_order(Date date_order) {
		this.date_order = date_order;
	}
	public Date getSale_date() {
		return sale_date;
	}
	public void setSale_date(Date sale_date) {
		this.sale_date = sale_date;
	}
	public long getCode() {
		return code;
	}
	public void setCode(long code) {
		this.code = code;
	}
	public String getPayment_method() {
		return payment_method;
	}
	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}
	public int getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(int subtotal) {
		this.subtotal = subtotal;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
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