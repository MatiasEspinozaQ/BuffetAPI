package com.CAT.BuffetAPI.Controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RestController;

import com.CAT.BuffetAPI.Entities.App_user;
import com.CAT.BuffetAPI.Entities.User_type;
import com.CAT.BuffetAPI.Repositories.userTypeRepository;
import com.CAT.BuffetAPI.Services.App_UserService;
import com.CAT.BuffetAPI.Services.AuthService;

@RestController
@RequestMapping("/mech-adm")
public class MechanicController {
	@Autowired
	App_UserService app;
	
	@Autowired
	private AuthService auth;
	

	@RequestMapping("/mechanics")
	private List<App_user> getAllMecha(HttpServletResponse res, @RequestHeader("token") String token,
													@RequestParam (required = false) String username,
													@RequestParam (required = false) String email,
													@RequestParam (required = false) String status_id,
													@RequestParam (required = false) String deleted)
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
				HashMap<String,Object> data = new HashMap<>();
				
				if(username!= null)
				{
					data.put("username", username);
				}
				if(email!=null)
				{
					data.put("email", email);
				}
				if(status_id!=null)
				{
					data.put("status_id", status_id);
				}
				if(deleted != null)
				{
					data.put("deleted", deleted);
				}
				else
				{
					data.put("deleted", false);
				}
				
				System.out.println("preAsignacion");
				List<App_user> userList = app.getData(data);
				System.out.println("postAsignacion");
				if(userList == null){
					// 404 Not Found
					res.setStatus(404);
					return null;
				}
				List<App_user> onlyMec = new ArrayList<App_user>();
				for (App_user app_user : userList) {
					app_user.setHash("");
					if(app_user.getUser_type_id().equals("MEC"))
						onlyMec.add(app_user);
				}

				// 200 OK
				res.setStatus(200);
				return onlyMec;
			
		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	
	}


	@RequestMapping(value="/mechanics/{Id}", method = {RequestMethod.GET})
	private Optional<App_user> getSpecificMech(HttpServletResponse res, @PathVariable("Id") String id, @RequestHeader("token") String token)
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
			if(!user.get().getUser_type_id().equals("MEC")) {
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
	private String UpdateMech(HttpServletResponse res,@PathVariable String Id, @RequestBody App_user reqUser,@RequestHeader("token") String token)
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
			String userId = reqUser.getAppuser_id();
			Optional<App_user> optUser = app.getAppUser(userId);
	
			// If there is no matching User
			if(!optUser.isPresent()){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}
	
			App_user oldUser = optUser.get();
			
			reqUser.setAppuser_id(oldUser.getAppuser_id());
			reqUser.setLastlogin(oldUser.getLastlogin());
			reqUser.setCreated_at(oldUser.getCreated_at());
			reqUser.setHash(oldUser.getHash());
			reqUser.setUpdated_at(new Date());
			app.updateUser(reqUser);
	
			// 200 OK
			res.setStatus(200);
			return "Usuario actualizado exitosamente";
		
		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}


	@RequestMapping(value = "/mechanics/{Id}/change-status", method = {RequestMethod.POST})
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

			// Revisa si el Usuario es un Mecánico
			if(!updateUser.getUser_type_id().equals("MEC")){
				// 409 Conflict
				res.setStatus(409);
				return null;
			}
			
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


	@RequestMapping(value= "/mechanics/{Id}", method = {RequestMethod.DELETE})
	private String DeleteMech(HttpServletResponse res,@PathVariable String Id,@RequestHeader("token") String token)
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
			return "Mecánico Eliminado Exitosamente";

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}

	@RequestMapping(value= "/mechanics/{Id}/restore", method = {RequestMethod.PUT})
	private String RestoreMech(HttpServletResponse res,@PathVariable String Id,@RequestHeader("token") String token)
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
				return "El Mecánico no está Eliminado";
			}
			
			resUser.setDeleted(false);
			app.updateUser(resUser);

			// 200 OK
			res.setStatus(200);
			return "Mecánico Restaurado Exitosamente";

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}

}
