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
import org.springframework.web.bind.annotation.RequestParam;

import com.CAT.BuffetAPI.Entities.App_user;
import com.CAT.BuffetAPI.Entities.User_type;
import com.CAT.BuffetAPI.Repositories.userTypeRepository;
import com.CAT.BuffetAPI.Services.App_UserService;
import com.CAT.BuffetAPI.Services.AuthService;

@Controller
@RequestMapping("/mech-adm")
public class MechanicController {
	@Autowired
	App_UserService app;
	
	@Autowired
	private AuthService auth;

	@Autowired
	private userTypeRepository type;
	
	@RequestMapping("/mechanics")
	private List<App_user> getAllUsers(HttpServletResponse res, @RequestHeader("token") String token)
	{
		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(auth.Authorize(token, typesAllowed))
		{
			res.setStatus(200);
			return app.getAllMecha();
		}
		else
		{
			res.setStatus(401);
			return null;
		}
	}



	@RequestMapping(value = "/mechanics/{Id}", method= {RequestMethod.GET})
	private Optional<App_user> getSpecificUser(HttpServletResponse res, @RequestParam("data") String id,@RequestHeader("token") String token)
	{
		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(auth.Authorize(token, typesAllowed))
		{
			App_user usuario = app.getAppUser(id).get();
			if(usuario!= null && usuario.getUser_type_id().equals("MEC"))
			{
				res.setStatus(200);
				return app.getAppUser(id);
			}
			else
			{
				res.setStatus(404);
				return null;
			}
		}
		else
		{
			res.setStatus(401);
			return null;
		}
	}



	@RequestMapping(value= "/mechanics/{Id}", method = {RequestMethod.PUT})
	private ResponseEntity<JsonObject> UpdateUser(@PathVariable String id, @RequestBody App_user user,@RequestHeader("token") String token)
	{
		HttpHeaders errorHeaders = new HttpHeaders();
		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(auth.Authorize(token, typesAllowed))
		{
			List<App_user> allUsers = new ArrayList<App_user>();
			boolean existe = false;
			allUsers = app.getAllMecha();
			for(App_user u : allUsers)
			{
				if(u.getAppuser_id().equals(id))
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
		else
		{
			errorHeaders.set("error-code", "ERR-AUTH-004");
			errorHeaders.set("error-desc", "Token incorrecto");
			return new ResponseEntity<JsonObject>(errorHeaders, HttpStatus.UNAUTHORIZED); 
		}
	}

	@RequestMapping(value = "/mechanics/{id}/change-type", method = {RequestMethod.PUT})
	private ResponseEntity<JsonObject> CambiarEstado( @PathVariable String id ,@RequestParam("user_type")String userType,@RequestHeader("token") String token)
	{
		HttpHeaders errorHeaders = new HttpHeaders();
		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(auth.Authorize(token, typesAllowed))
		{
			User_type theType = null;
			for(User_type types : type.findAll())
			{
				if(types.getUser_type_id().equals(userType))
				{
					theType = types;
				}
			}
			App_user user;
			user = app.getAppUser(id).get();

			if(user!= null)
			{
				if(!user.getUser_type_id().equals("MEC"))
				{
					errorHeaders.set("error-code", "ERR-AUTH-005");
					errorHeaders.set("error-desc", "El usuario no corresponde a un mecanico");
					return new ResponseEntity<JsonObject>(errorHeaders, HttpStatus.UNAUTHORIZED); 	
				}
				
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
		else
		{
			errorHeaders.set("error-code", "ERR-AUTH-004");
			errorHeaders.set("error-desc", "Token incorrecto");
			return new ResponseEntity<JsonObject>(errorHeaders, HttpStatus.UNAUTHORIZED); 
		}


	}

	@RequestMapping(value= "/mechanics/{Id}", method = {RequestMethod.DELETE})
	private ResponseEntity<JsonObject> DeleteUser(@PathVariable String Id,@RequestHeader("token") String token)
	{
		HttpHeaders errorHeaders = new HttpHeaders();
		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(auth.Authorize(token, typesAllowed))
		{
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
		else
		{
			errorHeaders.set("error-code", "ERR-AUTH-004");
			errorHeaders.set("error-desc", "Token incorrecto");
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
