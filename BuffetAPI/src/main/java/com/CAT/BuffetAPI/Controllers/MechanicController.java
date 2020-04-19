package com.CAT.BuffetAPI.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.CAT.BuffetAPI.Entities.App_user;
import com.CAT.BuffetAPI.Services.App_UserService;
import com.CAT.BuffetAPI.Services.AuthService;

@Controller
@RequestMapping("/mech-adm")
public class MechanicController {
	@Autowired
	App_UserService app;
	
	@Autowired
	private AuthService auth;

	@RequestMapping("/mechanics")
	private List<App_user> getAllMecha(HttpServletResponse res, @RequestHeader("token") String token)
	{
		System.out.println("jiajiajia entramos");
		if(token.isEmpty()){
			// 400 Bad Request
			res.setStatus(400);
			return null;
		}

		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(!auth.Authorize(token, typesAllowed)){
			// 401 Unauthorized
			res.setStatus(401);
			return null;
		}

		try {
			// Get the all the Users
			List<App_user> userList = app.getAllUsers();
			
			
			if(userList.isEmpty()){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}

			// 200 OK
			res.setStatus(200);
			return userList;

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}



	@RequestMapping(value="/mechanics/{Id}", method = {RequestMethod.GET})
	private Optional<App_user> getSpecificUser(HttpServletResponse res, @PathVariable("Id") String id, @RequestHeader("token") String token)
	{
		if(id.isEmpty() || token.isEmpty()){
			// 400 Bad Request
			res.setStatus(400);
			return null;
		}

		// Check for authorization
		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(!auth.Authorize(token, typesAllowed)){
			// 401 Unauthorized
			res.setStatus(401);
			return null;
		}

		try {
			// Get the User
			Optional<App_user> user = app.getAppUser(id);

			// If there is no matching User
			if(!user.isPresent()){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}

			// 200 OK
			res.setStatus(200);
			return user;

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}

	

	@RequestMapping(value= "/mechanics/{Id}", method = {RequestMethod.POST})
	private ResponseEntity<JsonObject> UpdateUser(HttpServletResponse res,@PathVariable String Id, @RequestBody App_user user,@RequestHeader("token") String token)
	{
		if(token.isEmpty()){
			// 400 Bad Request
			res.setStatus(400);
			return null;
		}
		HttpHeaders errorHeaders = new HttpHeaders();
		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(!auth.Authorize(token, typesAllowed)){
			// 401 Unauthorized
			res.setStatus(401);
			return null;
		}
		List<App_user> allUsers = new ArrayList<App_user>();
		boolean existe = false;
		allUsers = app.getAllMecha();
		for(App_user u : allUsers)
		{
			if(u.getAppuser_id().equals(Id))
			{
				existe = true;
				break;
			}
		}

		if(existe) {
			app.updateUser(user);
			return new ResponseEntity<JsonObject>(errorHeaders, HttpStatus.OK); 

		}
		else
		{
			errorHeaders.set("error-code", "ERR-AUTH-001");
			errorHeaders.set("error-desc", "Usuario no existe");
			return new ResponseEntity<JsonObject>(errorHeaders, HttpStatus.UNAUTHORIZED); 
		}
	}

	

	@RequestMapping(value= "/mechanics/{Id}", method = {RequestMethod.DELETE})
	private ResponseEntity<JsonObject> DeleteUser(HttpServletResponse res,@PathVariable String Id,@RequestHeader("token") String token)
	{
		if(token.isEmpty()){
			// 400 Bad Request
			res.setStatus(400);
			return null;
		}
		HttpHeaders errorHeaders = new HttpHeaders();
		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(!auth.Authorize(token, typesAllowed)){
			// 401 Unauthorized
			res.setStatus(401);
			return null;
		}
		List<App_user> allUsers = new ArrayList<App_user>();
		boolean existe = false;
		allUsers = app.getAllMecha();
		App_user user = null;
		for(App_user u : allUsers)
		{
			if(u.getAppuser_id().equals(Id))
			{
				existe = true;
				user = u;
				break;
			}
		}

		if(existe) {
			user.setDeleted(true);
			app.updateUser(user);
			return new ResponseEntity<JsonObject>(errorHeaders, HttpStatus.OK); 

		}
		else
		{
			errorHeaders.set("error-code", "ERR-AUTH-001");
			errorHeaders.set("error-desc", "Usuario no existe");
			return new ResponseEntity<JsonObject>(errorHeaders, HttpStatus.UNAUTHORIZED); 
		}


	}

	@RequestMapping(value = "/mechanics/Deleted", method= {RequestMethod.GET})
	private List<App_user> GetAllDeletedUsers(HttpServletResponse res)
	{
		List<App_user> users = new ArrayList<App_user>();
		for(App_user u : app.getAllDeleted())
		{
			if(u.isDeleted() && u.getUser_type_id().equals("MEC"))
			{
				users.add(u);
			}
		}
		res.setStatus(200);
		return users;
	
	}	

		
}
