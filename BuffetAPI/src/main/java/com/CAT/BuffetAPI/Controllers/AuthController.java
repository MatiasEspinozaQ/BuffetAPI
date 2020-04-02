package com.CAT.BuffetAPI.Controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.digest.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CAT.BuffetAPI.Entities.App_user;
import com.CAT.BuffetAPI.Services.App_UserService;
import com.CAT.BuffetAPI.Services.AuthService;
import com.fasterxml.jackson.databind.node.ObjectNode;

//Controlador dedicado a la inicio de sesion/autenticacion

@RestController
public class AuthController {
	
	@Autowired
	private AuthService auth;
	
	@Autowired
	private App_UserService app;
	//@Value("${jwt.secret}")
	public List<authCredentials> Credenciales = new ArrayList<authCredentials>();
	
	@PostMapping("/user-auth")
	public authCredentials Validate (@RequestBody ObjectNode json) {
		authCredentials credentials = new authCredentials();
		String mail;
		String password;
		mail = json.get("email").asText();
		password = json.get("hash").asText();
		App_user user = app.getByEmail(mail);
		if( auth.Validate(user.getAppuser_id(),DigestUtils.md5Hex(password)) && user != null){
			credentials.authorized = true;
			credentials.setToken(UUID.randomUUID().toString());
			credentials.setUsername(user.getUsername());
			Credenciales.add(credentials);
			return credentials;
		}
		else
		{
			return credentials;
		}
		
	}
	
	/* Für Later
	 @PostMapping("/sign-up")
     public void signUp(@RequestBody App_user user) {
		 user.setHash(DigestUtils.md5Hex(user.getHash()));
	     app.addUser(user);
	 }*/
	public class authCredentials{
		private String User_id = "";
		private boolean authorized = false;
		private String token = "";
		
		public String getUsername() {
			return User_id;
		}
		public void setUsername(String username) {
			User_id = username;
		}
		public boolean isAuthorized() {
			return authorized;
		}
		public void setAuthorized(boolean authorized) {
			this.authorized = authorized;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public authCredentials() {
			
		}
		
	}
	public class authForm{
		private String email;
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getHash() {
			return hash;
		}
		public void setHash(String hash) {
			this.hash = hash;
		}
		public authForm(String email, String hash) {
			super();
			this.email = email;
			this.hash = hash;
		}
		private String hash;
	}
}
