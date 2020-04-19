package com.CAT.BuffetAPI.Controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CAT.BuffetAPI.Entities.App_user;
import com.CAT.BuffetAPI.Entities.User_status;
import com.CAT.BuffetAPI.Entities.User_type;
import com.CAT.BuffetAPI.Repositories.userTypeRepository;
import com.CAT.BuffetAPI.Services.App_UserService;
import com.CAT.BuffetAPI.Services.AuthService;

@RestController
@RequestMapping("/user-adm")
public class App_UserController {

	@Autowired
	private App_UserService app;

	@Autowired
	private AuthService auth;

	@Autowired
	private userTypeRepository type;


	@RequestMapping("/users")
	private List<App_user> getAllUsers(HttpServletResponse res, @RequestHeader("token") String token)
	{
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

			if(userList == null){
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



	@RequestMapping(value="/users/{Id}", method = {RequestMethod.GET})
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

	@RequestMapping(value= "/users/{Id}", method = {RequestMethod.POST})
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
		System.out.println(Id);
		
		
		if( app.getAppUser(user.getAppuser_id()).isPresent()) {
			App_user old = app.getAppUser(user.getAppuser_id()).get();
			user.setAppuser_id(old.getAppuser_id());
			user.setLastlogin(old.getLastlogin());
			user.setCreated_at(old.getCreated_at());
			user.setUpdated_at(new Date());
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

	@RequestMapping(value = "/users/{Id}/change-type", method = {RequestMethod.POST})
	private ResponseEntity<JsonObject> CambiarEstado(HttpServletResponse res, @PathVariable String Id ,@RequestParam("user_type")String userType,@RequestHeader("token") String token)
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
		User_type theType = null;
		for(User_type types : type.findAll())
		{
			if(types.getUser_type_id().equals(userType))
			{
				theType = types;
			}
		}
		App_user user;
		user = app.getAppUser(Id).get();

		if(user!= null)
		{
			if(theType != null)
			{
				user.setUser_type_id(theType.getUser_type_id());
				app.updateUser(user);
				return new ResponseEntity<JsonObject>(errorHeaders, HttpStatus.OK); 
			}
			else
			{
				errorHeaders.set("error-code", "ERR-AUTH-002");
				errorHeaders.set("error-desc", "tipo de usuario no existe");
				return new ResponseEntity<JsonObject>(errorHeaders, HttpStatus.UNAUTHORIZED); 	
			}


		}
		else
		{
			errorHeaders.set("error-code", "ERR-AUTH-001");
			errorHeaders.set("error-desc", "Usuario no existe");
			return new ResponseEntity<JsonObject>(errorHeaders, HttpStatus.UNAUTHORIZED); 	
		}

	}

	@RequestMapping(value= "/users/{Id}", method = {RequestMethod.DELETE})
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
		allUsers = app.getAllUsers();
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

	@RequestMapping(value = "/users/deleted", method= {RequestMethod.GET})
	private List<App_user> GetAllDeletedUsers(HttpServletResponse res)
	{
		res.setStatus(200);
		return app.getAllDeleted();
	}	


}
