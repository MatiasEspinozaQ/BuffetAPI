package com.CAT.BuffetAPI.Services;



import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

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

	public boolean Authorize(String token,List<String> typesAllowed)
	{
		try {
			System.out.println("1");
			Claims claims = getClaims(token);
			System.out.println("2");
			for(String tipo : typesAllowed)
			{
				System.out.println(tipo);
			}
			
			System.out.println(claims.get("Usertype",String.class));
			if(typesAllowed.contains(claims.get("Usertype",String.class)))
			{
				return true;
			}
			else
			{	System.out.println("3");
				return false;
			}
		}
		catch(Exception e)
		{
			return false;
		}


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
				String hashedPassword;
				hashedPassword = encode(KeyForRecovery, newPassword);
				user.setHash(new String(hashedPassword));
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
	
	public static String encode(String key, String data) throws Exception {
		  Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		  SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
		  sha256_HMAC.init(secret_key);

		  return Hex.encodeHexString(sha256_HMAC.doFinal(data.getBytes("UTF-8")));
		}
}
