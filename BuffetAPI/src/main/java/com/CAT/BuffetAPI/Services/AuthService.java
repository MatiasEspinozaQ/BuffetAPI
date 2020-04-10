package com.CAT.BuffetAPI.Services;



import java.util.List;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.codec.binary.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.CAT.BuffetAPI.Entities.App_user;
import com.CAT.BuffetAPI.Repositories.App_UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;




@Service
public class AuthService {
	
	
	@Autowired
	private App_UserRepository appUserRepository;
	@Autowired
	private App_UserService appService;
	
	@Value ("${secretKey}")
	private String SecretKey;
	
	@Value ("${secretKeyPassword}")
	private String KeyForRecovery;
	
	@Autowired
	private EmailSenderService mailSender;
	
	public Claims getClaims(String jwt) {
		Claims claims = Jwts.parser()
	            .setSigningKey(DatatypeConverter.parseBase64Binary(SecretKey))
	            .parseClaimsJws(jwt).getBody();
	    return claims;
	}
	
	public boolean Authorize(HttpServletRequest req, HttpServletResponse res,List<String> typesAllowed)
	{
		
		return true;
	
	}
	
	public boolean Validate(String id, String passwd) {
		
		 App_user finalUser = null;
		 
		 finalUser = appUserRepository.getOne(id);
		 
	
		 if(finalUser != null && finalUser.getHash().equals(passwd))
		 {
			 return true;
		 }
		 else
		 {
			 return false;
		 }
	}
	
	public boolean RegisterValidation(App_user user) {
		boolean sameUsername = false;
		boolean sameEmail = false;
		
		List<App_user> users = appUserRepository.findAll();
		
		for(App_user u : users)
		{
			if(u.getUsername().equals(user.getUsername()))
			{
				sameUsername = true;
			}
			if(u.getEmail().equals(user.getEmail()))
			{
				sameEmail = true;
			}	
		}
		if(sameUsername || sameEmail)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public boolean RecoverPassword(String email)
	{
		App_user user;
		String newPassword;
		user = appService.getByEmail(email);
		
		if(user != null)
		{
			try {
				newPassword = UUID.randomUUID().toString();
				Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
				SecretKeySpec secret_key = new SecretKeySpec(KeyForRecovery.getBytes(), "HmacSHA256");
				sha256_HMAC.init(secret_key);
				newPassword = Base64.encodeBase64String(sha256_HMAC.doFinal(newPassword.getBytes()));
				user.setHash(newPassword);
				 SimpleMailMessage mailMessage = new SimpleMailMessage();
		         mailMessage.setTo(user.getEmail());
		         mailMessage.setSubject("Nueva Contraseña!");
		         mailMessage.setFrom("userconfimationjaramillo@gmail.com");
		         mailMessage.setText("Ya que olvidaste tu contraseña hemos creado una nueva para ti :3\n\nTu nueva contraseña es "+newPassword+"\nPodras volver a cambiarla desde la aplicación");

		         
				mailSender.sendEmail(mailMessage);
				appService.updateUser(user);
				return true;
			}
			catch(Exception e)
			{
				return false;
			}
		}
		else
		{
			return false;
		}

	}
}
