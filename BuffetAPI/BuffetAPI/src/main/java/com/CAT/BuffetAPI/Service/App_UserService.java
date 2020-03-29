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
public class App_UserService {
	
	
	@Autowired
	private appUserRepository appUserRepository;
	
	public List<App_user> getAllUsers(){
		List<App_user> listaUsuarios = new ArrayList<App_user>();
		appUserRepository.findAll().forEach(listaUsuarios::add);
		return listaUsuarios;
	}
	
	public Optional<App_user> getAppUser(String id)
	{
		return appUserRepository.findById(id);
		
	}
	
	public void addUser(App_user user) {
		appUserRepository.save(user);
	}
	
	public void updateUser(App_user user) {
		
		 appUserRepository.save(user);
	}
	
	public void deleteUser(App_user user) {
		 appUserRepository.delete(user);
	}
}
