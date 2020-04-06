package com.CAT.BuffetAPI.Controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.CAT.BuffetAPI.Entities.App_user;
import com.CAT.BuffetAPI.Services.App_UserService;
import com.CAT.BuffetAPI.Services.AuthService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.mail.iap.Response;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//Controlador dedicado a la inicio de sesion/autenticacion
@RestController
public class AuthController {
	static final long ONE_MINUTE_IN_MILLIS=60000;
	@Autowired
	private AuthService auth;
	
	@Autowired
	private App_UserService app;
	
	@Value ("${secretKey}")
	private String SecretKey;
	
	/*Recibe un json con un mail y una contraseña de la forma:
	 * {
	 * 		"email" : "email@servidor.com"
	 * 		"hash"  : "contraseña"
	 * }
	 *
	 *
	 *devuelve token
	
	*/
	
	
	@PostMapping("/user-auth")
	public ResponseEntity<JsonObject> Validate (@RequestBody ObjectNode json) {
		String mail;
		String password;
		Long tiempo = System.currentTimeMillis();
		mail = json.get("username").asText(); //cambiado de email a username
		password = json.get("hash").asText();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date createdat =(new Date(tiempo));
		Date exp= new Date(tiempo+(ONE_MINUTE_IN_MILLIS * 30)); //aca se setea la fecha de expiracion, esta seteado en 30 minutos a partir de la fecha de creacion
		sdf.applyPattern("yyyy/MM/dd");
		App_user user = app.getByEmail(mail); //se recupera un usuario con un mail igual al entregado en el login
 		if( auth.Validate(user.getAppuser_id(),password )&& user != null){  //Se revisa que la contraseña corresponda al id de la persona.
			String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, SecretKey)
					.setSubject(user.getUsername())
					.setIssuedAt(createdat)
					.setExpiration(exp)
					.claim("iss", "buffetapi.jaramillo.cl")
					.claim("buffetapi.jaramillo.cl/is_admin", user.getUser_type_id().equals("1"))
					.claim("userId", user.getAppuser_id())
					.claim("Usertype", user.getUser_type_id())
					.compact();
			JsonObject jerson = Json.createObjectBuilder().add("JWT", jwt).build();
			return new ResponseEntity<JsonObject>(jerson, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<JsonObject>(HttpStatus.UNAUTHORIZED); //se retornan valores por defecto ("", falso, "")
		}
		
	}
	
	
	 @PostMapping("/register")
     public void signUp(@RequestBody App_user user , HttpServletResponse resp) {
		 if(auth.RegisterValidation(user)) {
	     app.addUser(user);
	     	resp.setStatus(200);    
	    
		 }
		 else
		 {
			resp.setStatus(403);
			
		 }
	 }
	
	
}
