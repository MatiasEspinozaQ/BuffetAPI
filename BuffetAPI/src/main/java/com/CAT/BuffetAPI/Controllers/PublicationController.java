package com.CAT.BuffetAPI.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.CAT.BuffetAPI.Entities.Public_status;
import com.CAT.BuffetAPI.Entities.Publication;
import com.CAT.BuffetAPI.Entities.User_status;
import com.CAT.BuffetAPI.Entities.User_type;
import com.CAT.BuffetAPI.Repositories.Public_statusRepository;
import com.CAT.BuffetAPI.Repositories.PublicationRepository;
import com.CAT.BuffetAPI.Services.AuthService;
import com.CAT.BuffetAPI.Services.PublicationService;

@RestController
@RequestMapping("/pub-adm")
public class PublicationController {
	@Autowired
	private PublicationService pub;
	@Autowired
	private PublicationRepository pubrepo;
	@Autowired
	private Public_statusRepository statusRepo;

	@Autowired
	private AuthService auth;

	@RequestMapping("/publications")
	private List<Publication> getAllPublications(HttpServletResponse res, @RequestHeader("token") String token,
			@RequestParam(required = false) String comuna
			,@RequestParam(required = false) String public_status_id
			,@RequestParam(required = false) String bussiness_name
			,@RequestParam(required = false) String title
			,@RequestParam(required = false) String deleted)
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

			if(comuna!= null)
			{
				data.put("comuna", comuna);
			}
			if(public_status_id!=null)
			{
				data.put("public_status_id", public_status_id);
			}
			if(title != null)
			{
				data.put("title",title);
			}
			if(bussiness_name != null)
			{
				data.put("bussiness_name",bussiness_name);
			}
			if(deleted != null)
			{
				data.put("deleted",deleted);
			}
			else
			{
				data.put("deleted",0);
			}


			List<Publication> pubList = pubrepo.getData(data);

			if(pubList == null){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}
			

			// 200 OK
			res.setStatus(200);
			return pubrepo.getData(data);

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}



	@RequestMapping(value="/publications/{Id}", method = {RequestMethod.GET})
	private Optional<Publication> getSpecificPub(HttpServletResponse res, @PathVariable("Id") String id, @RequestHeader("token") String token)
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
			Optional<Publication> publication = pub.getOnePublication(id);

			// If there is no matching User
			if(!publication.isPresent()){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}

			// 200 OK
			res.setStatus(200);
			return publication;

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}


	@RequestMapping(value= "/publications/{Id}", method = {RequestMethod.DELETE})
	private ResponseEntity<JsonObject> DeletePub(HttpServletResponse res,@PathVariable String Id,@RequestHeader("token") String token)
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
		List<Publication> publicaciones = new ArrayList<Publication>();
		boolean existe = false;
		publicaciones = pub.getAllPublications();
		Publication publication = null;
		for(Publication u : publicaciones)
		{
			if(u.getPublic_id().equals(Id))
			{
				existe = true;
				publication = u;
				break;
			}
		}

		if(existe) {
			publication.setDeleted(true);
			pub.UpdatePublication(publication);
			return new ResponseEntity<JsonObject>(errorHeaders, HttpStatus.OK); 

		}
		else
		{
			errorHeaders.set("error-code", "ERR-AUTH-001");
			errorHeaders.set("error-desc", "Usuario no existe");
			return new ResponseEntity<JsonObject>(errorHeaders, HttpStatus.UNAUTHORIZED); 
		}


	}


	@RequestMapping(value= "/publications/{Id}/restore", method = {RequestMethod.PUT})
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
			// Get the Publication
			Optional<Publication> publication = pub.getOnePublication(Id);

			// If there is no matching User
			if(!publication.isPresent()){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}

			Publication resPub = publication.get();

			if(!resPub.isDeleted()){
				// 409 Conflict
				res.setStatus(409);
				return "La Publicación no está Eliminada";
			}
			
			resPub.setDeleted(false);
			pub.UpdatePublication(resPub);

			// 200 OK
			res.setStatus(200);
			return "Publicación Restaurada Exitosamente";

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}



	@RequestMapping(value = "/publications/{Id}/change-status", method = {RequestMethod.POST})
	private ResponseEntity<JsonObject> ChangeStatus(HttpServletResponse res, @PathVariable String Id ,@RequestParam("public_status_id")String public_status_id,@RequestHeader("token") String token)
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
		Public_status theStatus = null;
		for(Public_status status : statusRepo.findAll())
		{
			if(status.getPublic_status_id().equals(public_status_id))
			{
				theStatus = status;
			}
		}
		Publication publication;
		publication = pub.getOnePublication(Id).get();

		if(publication!= null)
		{
			if(theStatus != null)
			{
				publication.setPublic_status_id(theStatus.getPublic_status_id());
				pub.UpdatePublication(publication);
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


	@RequestMapping("/public-status")
	private List<Public_status> getAllStatus(HttpServletResponse res){

		try {
			// Get the all the Users
			List<Public_status> status = statusRepo.findAll();

			if(status == null){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}

			// 200 OK
			res.setStatus(200);
			return status;

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}

	
	@RequestMapping(value= "/publications/{Id}", method = {RequestMethod.POST})
	private ResponseEntity<JsonObject> UpdatePub(HttpServletResponse res,@PathVariable String Id, @RequestBody Publication publication,@RequestHeader("token") String token)
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
		List<Publication> allUsers = new ArrayList<Publication>();
		boolean existe = false;
		allUsers = pub.getAllPublications();
		for(Publication u : allUsers)
		{
			if(u.getPublic_id().equals(Id))
			{
				existe = true;
				break;
			}
		}

		if(existe) {

			pub.UpdatePublication(publication);
			return new ResponseEntity<JsonObject>(errorHeaders, HttpStatus.OK); 

		}
		else
		{

			errorHeaders.set("error-code", "ERR-AUTH-001");
			errorHeaders.set("error-desc", "Usuario no existe");
			return new ResponseEntity<JsonObject>(errorHeaders, HttpStatus.UNAUTHORIZED); 
		}
	}



}
