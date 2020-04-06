package com.CAT.BuffetAPI.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CAT.BuffetAPI.Entities.App_user;
import com.CAT.BuffetAPI.Repositories.App_UserRepository;




@Service
public class AuthService {
	
	
	@Autowired
	private App_UserRepository appUserRepository;
	
	
	public boolean Validate(String id, String passwd) {
		
		 App_user finalUser = null;
		 
		 finalUser = appUserRepository.getOne(id);
		 
	
		 if(finalUser != null && finalUser.getHash().equals(passwd))
		 {
			 return true;
		 }
		 else
		 {
			 return false;
		 }
	}
	
	public boolean RegisterValidation(App_user user) {
		boolean sameUsername = false;
		boolean sameEmail = false;
		
		List<App_user> users = appUserRepository.findAll();
		
		for(App_user u : users)
		{
			if(u.getUsername().equals(user.getUsername()))
			{
				sameUsername = true;
			}
			
			if(u.getEmail().equals(user.getEmail()))
			{
				sameEmail = true;
			}
			
		}
		
		
		if(sameUsername || sameEmail)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}
