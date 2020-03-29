package com.CAT.BuffetAPI.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CAT.BuffetAPI.Entities.App_user;
import com.CAT.BuffetAPI.Repositories.appUserRepository;



@Service
public class AuthService {
	
	
	@Autowired
	private appUserRepository appUserRepository;
	
	
	public boolean Validate(String id, String passwd) {
		
		 App_user finalUser = null;
		 
		 finalUser = appUserRepository.findById(id).orElse(null);
		 
	
		 if(finalUser != null && finalUser.getHash().equals(passwd))
		 {
			 return true;
		 }
		 else
		 {
			 return false;
		 }
	}
}
