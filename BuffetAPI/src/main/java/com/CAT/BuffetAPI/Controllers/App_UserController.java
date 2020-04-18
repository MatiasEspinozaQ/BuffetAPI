package com.CAT.BuffetAPI.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CAT.BuffetAPI.Entities.App_user;
import com.CAT.BuffetAPI.Entities.User_status;
import com.CAT.BuffetAPI.Entities.User_type;
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
	
	
	
	@RequestMapping(value = "/users/{Id}", method= {RequestMethod.GET})
	private Optional<App_user> getSpecificUser(HttpServletResponse res, @RequestParam("data") String id,@RequestHeader("token") String token)
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

	@RequestMapping(value= "/users/{Id}", method = {RequestMethod.PUT})
	private ResponseEntity<JsonObject> UpdateUser(@PathVariable String id, @RequestBody App_user user)
	{
		
		HttpHeaders errorHeaders = new HttpHeaders();
		
		List<App_user> allUsers = new ArrayList<App_user>();
		boolean existe = false;
		allUsers = app.getAllUsers();
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
	
	@RequestMapping(value = "users/{id}/change-type", method = {RequestMethod.PUT})
	private ResponseEntity<JsonObject> CambiarEstado( @PathVariable String id ,String user_type )
	{
		HttpHeaders errorHeaders = new HttpHeaders();
		User_type theType = null;
		for(User_type types : type.findAll())
		{
			if(types.getName().equals(user_type))
			{
				theType = types;
			}
		}
		App_user user;
		user = app.getAppUser(id).get();
		
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
	
/*
	Crear API que retorne los datos de un Usuario (GET) //getone?
	Crear API que cambie los datos de un Usuario en base a un formulario (PUT) //Listo
	Crear API que cambie el tipo de un Usuario (PUT) //
	Crear API que que de de baja o alta a un Usuario (PUT)
	Crear API que retorne un listado de Usuarios eliminados usando composición para aplicar filtros (GET)
	Crear API que que elimine de manera lógica un Usuario (DELETE)
	*/
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
