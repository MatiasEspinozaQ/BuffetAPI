package com.CAT.BuffetAPI.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CAT.BuffetAPI.Entities.App_user;
import com.CAT.BuffetAPI.Entities.User_status;
import com.CAT.BuffetAPI.Entities.User_type;
import com.CAT.BuffetAPI.Services.App_UserService;
import com.CAT.BuffetAPI.Services.AuthService;

@RestController
@RequestMapping("/user-adm")
public class App_UserController {
	
	@Autowired
	private App_UserService app;
	
	@Autowired
	private AuthService auth;
	
	@RequestMapping("/users")
	private List<App_user> getAllUsers(HttpServletResponse res, @RequestHeader("token") String token)
	{
		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(auth.Authorize(token, typesAllowed))
		{
			res.setStatus(200);
			return app.getAllUsers();
		}
		else
		{
			res.setStatus(401);
			return null;
		}
	}
	
	
	
	@RequestMapping("/users/{Id}")
	private Optional<App_user> getSpecificUser(HttpServletResponse res, @PathVariable("Id") String id, @RequestHeader("token") String token)
	{
		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(auth.Authorize(token, typesAllowed))
		{
			res.setStatus(200);
			return app.getAppUser(id);
		}
		else
		{
			res.setStatus(401);
			return null;
		}
	}
	
	@RequestMapping("/user-type")
	private List<User_type> getAllTypes(HttpServletResponse res){

		try {
			// Get the all the Users
			List<User_type> typeList = app.getAllTypes();

			if(typeList == null){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}

			// 200 OK
			res.setStatus(200);
			return typeList;

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}

	@RequestMapping("/user-status")
	private List<User_status> getAllStatys(HttpServletResponse res){

		try {
			// Get the all the Users
			List<User_status> typeList = app.getAllStatus();

			if(typeList == null){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}

			// 200 OK
			res.setStatus(200);
			return typeList;

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
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
