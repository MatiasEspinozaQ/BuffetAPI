package com.CAT.BuffetAPI;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.CAT.BuffetAPI.Entities.App_user;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private App_user user;
 
    public OnRegistrationCompleteEvent(App_user user, Locale locale, String appUrl)
    {
        super(user);
         
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public App_user getUser() {
		return user;
	}

	public void setUser(App_user user) {
		this.user = user;
	}
  
}