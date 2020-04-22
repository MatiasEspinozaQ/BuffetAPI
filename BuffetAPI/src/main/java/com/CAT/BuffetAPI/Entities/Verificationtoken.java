package com.CAT.BuffetAPI.Entities;


import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Verificationtoken {
    private static final int EXPIRATION = 60 * 24;
     @Id
    private String token;

    private String Appuser_id;
    
    public String getApp_userid() {
		return Appuser_id;
	}

	public void setApp_userid(String app_userid) {
		Appuser_id = app_userid;
	}

	private Date expiry_date;
    
    public Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
    // standard constructors, getters and setters

	public Verificationtoken(String token, String appuser_id) {
		super();
		this.token = token;
		this.expiry_date = calculateExpiryDate(EXPIRATION);
		this.Appuser_id = appuser_id;
	}


	public Verificationtoken() {
		super();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


	public Date getExpiry_Date() {
		return expiry_date;
	}

	public void setExpiry_Date(Date expiry_date) {
		this.expiry_date = expiry_date;
	}
}