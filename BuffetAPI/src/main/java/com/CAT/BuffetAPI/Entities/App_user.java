package com.CAT.BuffetAPI.Entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.transaction.Transactional;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "APP_USER")
@Transactional
public class App_user {
	
	@Id
	@GeneratedValue(generator="db-guid")
	@GenericGenerator(name="db-guid", strategy = "guid") 
	private String appuser_id;
	
	private String username;
	private String hash;
	private String email;
	private String name;
	private String last_names;
	private String adress;
	private String phone;
	private Date birthday;
	private boolean mail_confirmed;
	private String rut;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date lastlogin;
	
	private String user_type_id;
	private String status_id;
	private boolean deleted;
	private Date updated_at;
	private Date created_at;
	
	public String getRut() {
		return rut;
	}
	public void setRut(String rut) {
		this.rut = rut;
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

	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean getMail_confirmed() {
		return mail_confirmed;
	}
	public void setMail_confirmed(boolean mail_confirmed) {
		this.mail_confirmed = mail_confirmed;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
}
