package com.CAT.BuffetAPI.Controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CAT.BuffetAPI.Entities.App_user;
import com.CAT.BuffetAPI.Service.AuthService;

@RestController
public class AuthController {
	
	@Autowired
	private AuthService auth;
	
	@RequestMapping("/user-auth")
	private Boolean Validate (@RequestBody App_user app) {
		//UUID idConverted = UUID.fromString(id);
		return auth.Validate(app.getAppuser_id(),app.getHash());
	}
}
