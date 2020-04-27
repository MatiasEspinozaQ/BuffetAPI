package com.CAT.BuffetAPI.Controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

	private void log(String msg) {
		System.out.println(msg);
	}
	private void logLine() {
		System.out.println("----------------------------------------------------------------");
	}

	@RequestMapping("/users")
	private List<App_user> getAllUsers(
		HttpServletResponse res, 
		@RequestHeader("token") String token, 
		@RequestParam (required = false) String username,
		@RequestParam (required = false) String email,
		@RequestParam (required = false) String user_type_id,
		@RequestParam (required = false) String status_id,
		@RequestParam (required = false) String deleted)
	{
		logLine();

		if(token.isEmpty()){
			// 401 Unauthorized
			res.setStatus(401);
			log("No hay JWT presente: 401 Unauthorized");
			return null;
		}

		log("Revisando permisos...");
		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(!auth.Authorize(token, typesAllowed)){
			// 401 Unauthorized
			res.setStatus(401);
			log("Usuario no autorizado: 401 Unauthorized");
			return null;
		}
		log("Usuario autorizado");
		

		try {

			// Define filter data
			HashMap<String,Object> data = new HashMap<>();
			
			if(username!= null) 	data.put("username", username);
			if(email!=null)			data.put("email", email);
			if(user_type_id!= null)	data.put("user_type_id", user_type_id);
			if(status_id!=null)		data.put("status_id", status_id);

			if(deleted != null)		data.put("deleted", deleted);
			else					data.put("deleted", false);
			
			// Conseguir todos los usuarios de la BD
			log("Consiguiendo usuarios de la BD");
			List<App_user> userList = app.getData(data);
			
			// Revisar si la lista es válida
			if(userList == null){
				// 404 Not Found
				res.setStatus(404);
				log("Usuarios no encontrados: 404 Not Found");
				return null;
			}
			log("Usuarios Encontrados");

			// Eliminar los hash de los Usuarios por seguridad
			for (App_user app_user : userList) {
				app_user.setHash("");
			}

			// 200 OK
			res.setStatus(200);
			log("Proceso existoso: 200 OK");
			return userList;

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			log("Error conectando con la BD: 500 Internal Server Error\nERROR:\n");
			System.out.println(e.getMessage());
			return null;
		}
	}


	@RequestMapping(value="/users/{Id}", method = {RequestMethod.GET})
	private App_user getSpecificUser(HttpServletResponse res, @PathVariable("Id") String id, @RequestHeader("token") String token)
	{
		logLine();

		if(id.isEmpty()){
			// 400 Bad Request
			res.setStatus(400);
			log("No hay ningun Id en la URL: 400 Bad Request");
			return null;
		}

		// Revisar Autorización
		log("Revisando permisos...");
		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(token.isEmpty() || !auth.Authorize(token, typesAllowed)){
			// 401 Unauthorized
			res.setStatus(401);
			log("Usuario no autorizado: 401 Unauthorized");
			return null;
		}
		log("Usuario autorizado");

		try {
			// Conseguir el Usuario
			log("Consiguiendo usuario de la BD");
			Optional<App_user> user = app.getAppUser(id);

			// Si el Usuario no está presente
			if(!user.isPresent()){
				// 404 Not Found
				res.setStatus(404);
				log("Usuario no encontrado: 404 Not Found");
				return null;
			}

			App_user final_user = user.get();
			log("Usuario Encontrado");

			// Eliminar los hash de los Usuarios por seguridad
			final_user.setHash("");

			// 200 OK
			res.setStatus(200);
			log("Proceso existoso: 200 OK");
			return final_user;

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			log("Error conectando con la BD: 500 Internal Server Error\nERROR:\n");
			System.out.println(e.getMessage());
			return null;
		}
	}


	@RequestMapping(value= "/users/{Id}", method = {RequestMethod.POST})
	private String UpdateUser(HttpServletResponse res,@PathVariable String Id, @RequestBody App_user reqUser,@RequestHeader("token") String token)
	{
		if(token.isEmpty()){
			// 400 Bad Request
			res.setStatus(400);
			log("No hay JWT presente: 400 Bad Request");
			return null;
		}

		log("Revisando permisos...");
		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(!auth.Authorize(token, typesAllowed)){
			// 401 Unauthorized
			res.setStatus(401);
			log("Usuario no autorizado: 401 Unauthorized");
			return null;
		}
		log("Usuario autorizado");

		try {
			// Sava el Id del Usuario enviado en la request
			String userId = reqUser.getAppuser_id();
			
			log("Consiguiendo usuario de la BD");
			Optional<App_user> optUser = app.getAppUser(userId);
	
			// If there is no matching User
			if(!optUser.isPresent()){
				// 404 Not Found
				res.setStatus(404);
				log("Usuario no encontrado: 404 Not Found");
				return null;
			}

			App_user oldUser = optUser.get();
			log("Usuario Encontrado");
			
			log("Editando datos");
			reqUser.setAppuser_id(oldUser.getAppuser_id());
			reqUser.setLastlogin(oldUser.getLastlogin());
			reqUser.setCreated_at(oldUser.getCreated_at());
			reqUser.setHash(oldUser.getHash());
			reqUser.setUpdated_at(new Date());
			app.updateUser(reqUser);
			log("Datos editados con exito");
	
			// 200 OK
			res.setStatus(200);
			log("Proceso existoso: 200 OK");
			return "Usuario actualizado exitosamente";
		
		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			log("Error conectando con la BD: 500 Internal Server Error\nERROR:\n");
			return null;
		}
	}


	@RequestMapping(value = "/users/{Id}/change-type", method = {RequestMethod.POST})
	private ResponseEntity<JsonObject> ChangeType(HttpServletResponse res, @PathVariable String Id ,@RequestParam("user_type")String userType,@RequestHeader("token") String token)
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

	@RequestMapping(value = "/users/{Id}/change-status", method = {RequestMethod.POST})
	private String ChangeStatus(HttpServletResponse res, @PathVariable String Id ,@RequestParam("status")String status,@RequestHeader("token") String token)
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
			// Get the User
			Optional<App_user> user = app.getAppUser(Id);

			// If there is no matching User
			if(!user.isPresent()){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}

			App_user updateUser = user.get();
			
			updateUser.setStatus_id(status);
			app.updateUser(updateUser);

			// 200 OK
			res.setStatus(200);
			return "Status actualizado exitosamente";

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}


	@RequestMapping(value= "/users/{Id}", method = {RequestMethod.DELETE})
	private String DeleteUser(HttpServletResponse res,@PathVariable String Id,@RequestHeader("token") String token)
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
			// Get the User
			Optional<App_user> user = app.getAppUser(Id);

			// If there is no matching User
			if(!user.isPresent()){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}

			App_user delUser = user.get();
			
			delUser.setDeleted(true);
			app.updateUser(delUser);

			// 200 OK
			res.setStatus(200);
			return "Usuario Eliminado Exitosamente";

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}

	@RequestMapping(value= "/users/{Id}/restore", method = {RequestMethod.PUT})
	private String RestoreUser(HttpServletResponse res,@PathVariable String Id,@RequestHeader("token") String token)
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
			// Get the User
			Optional<App_user> user = app.getAppUser(Id);

			// If there is no matching User
			if(!user.isPresent()){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}

			App_user resUser = user.get();

			if(!resUser.isDeleted()){
				// 409 Conflict
				res.setStatus(409);
				return "El Usuario no está Eliminado";
			}
			
			resUser.setDeleted(false);
			app.updateUser(resUser);

			// 200 OK
			res.setStatus(200);
			return "Usuario Restaurado Exitosamente";

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
	private List<User_status> getAllStatus(HttpServletResponse res){

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

}
