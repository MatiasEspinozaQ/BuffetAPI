package com.CAT.BuffetAPI.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CAT.BuffetAPI.Entities.App_user;
import com.CAT.BuffetAPI.Services.App_UserService;

@RestController
public class App_UserController {
	
	@Autowired
	private App_UserService app;
	
	
	@RequestMapping("/app_users")
	private List<App_user> getAllUsers()
	{
		return app.getAllUsers();
	}
	
	
	
	@RequestMapping("/app_users/")
	private Optional<App_user> getSpecificUser(@RequestBody App_user user)
	{
		return app.getAppUser(user.getAppuser_id());
	}
	
	@RequestMapping("/Mecanicos")
	private List<App_user> addUser()
	{
		return app.getAllMecha();
	}
	
	/*
	@RequestMapping("/app_users")
	private List<App_user> updateUser()
	{
		return app.getAllUsers();
	}
	
	@RequestMapping("/app_users")
	private List<App_user> deleteUser()
	{
		return app.getAllUsers();
	}
	*/

	
	
}
