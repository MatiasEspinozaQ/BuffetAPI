package com.CAT.BuffetAPI.Entities;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "APP_USER")
public class App_user {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "appuser_id", columnDefinition = "VARCHAR(255)")
	private String appuser_id;
	
	private String username;
	private String hash;
	private String email;
	private String name;
	private String last_names;
	private String adress;
	private String phone;
	private String description;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date lastlogin;
	private String user_type_id;
	private String status_id;
	private boolean isdelete;
	public String getAppuser_id() {
		return appuser_id;
	}
	public void setAppuser_id(String appuser_id) {
		this.appuser_id = appuser_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLast_names() {
		return last_names;
	}
	public void setLast_names(String last_names) {
		this.last_names = last_names;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getLastlogin() {
		return lastlogin;
	}
	public void setLastlogin(Date lastlogin) {
		this.lastlogin = lastlogin;
	}
	public String getUser_type_id() {
		return user_type_id;
	}
	public void setUser_type_id(String user_type_id) {
		this.user_type_id = user_type_id;
	}
	public String getStatus_id() {
		return status_id;
	}
	public void setStatus_id(String status_id) {
		this.status_id = status_id;
	}
	public boolean isIsdelete() {
		return isdelete;
	}
	public void setIsdelete(boolean isdelete) {
		this.isdelete = isdelete;
	}


	
}
