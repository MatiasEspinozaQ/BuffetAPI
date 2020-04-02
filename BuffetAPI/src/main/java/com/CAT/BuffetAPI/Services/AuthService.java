package com.CAT.BuffetAPI.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
}
