package com.CAT.BuffetAPI.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CAT.BuffetAPI.Entities.App_user;
import com.CAT.BuffetAPI.Service.App_UserService;

@RestController
public class App_UserController {
	
	@Autowired
	private App_UserService app;
	
	
	@RequestMapping("/app_users")
	private List<App_user> getAllUsers()
	{
		return app.getAllUsers();
	}
	
	
}
