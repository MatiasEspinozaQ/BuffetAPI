package com.CAT.BuffetAPI.Controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.codec.digest.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CAT.BuffetAPI.Entities.App_user;
import com.CAT.BuffetAPI.Services.App_UserService;
import com.CAT.BuffetAPI.Services.AuthService;

//Controlador dedicado a la inicio de sesion/autenticacion

@RestController
public class AuthController {
	
	@Autowired
	private AuthService auth;
	private App_UserService app;
	
	@RequestMapping("/user-auth")
	public Boolean Validate (@RequestBody App_user app) {
		return auth.Validate(app.getAppuser_id(),DigestUtils.md5Hex(app.getHash()));
	}
	
	/* Für Later
	 @PostMapping("/sign-up")
     public void signUp(@RequestBody App_user user) {
		 user.setHash(DigestUtils.md5Hex(user.getHash()));
	     app.addUser(user);
	 }*/
}
