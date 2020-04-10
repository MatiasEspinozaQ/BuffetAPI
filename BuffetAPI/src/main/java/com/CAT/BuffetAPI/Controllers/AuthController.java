package com.CAT.BuffetAPI.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.CAT.BuffetAPI.Entities.App_user;
import com.CAT.BuffetAPI.Entities.Verificationtoken;
import com.CAT.BuffetAPI.Repositories.App_UserRepository;
import com.CAT.BuffetAPI.Repositories.VerificationTokenRepository;
import com.CAT.BuffetAPI.Services.App_UserService;
import com.CAT.BuffetAPI.Services.AuthService;
import com.CAT.BuffetAPI.Services.EmailSenderService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//Controlador dedicado a la inicio de sesion/autenticacion
@RestController
@RequestMapping("/user-auth")
public class AuthController {
	static final long ONE_MINUTE_IN_MILLIS=60000;
	@Autowired
	private AuthService auth;
	
	@Autowired
	private App_UserService app;
	
	@Value ("${secretKey}")
	private String SecretKey;
	
	@Autowired
	VerificationTokenRepository verificationRepo;
	
	@Autowired
	private App_UserRepository Userrepo;
	
	@Autowired
	private EmailSenderService mailSender;
	
	
	@PostMapping(consumes = "application/x-www-form-urlencoded")
	public ResponseEntity<JsonObject> Validate (App_user form_user) {
		String mail;
		String password;
		Long tiempo = System.currentTimeMillis();

		mail = form_user.getUsername(); 
		password = form_user.getHash();
		//String apiKeySecretBytes = DigestUtils.sha256(SecretKey);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date createdat =(new Date(tiempo));
		Date exp= new Date(tiempo+(ONE_MINUTE_IN_MILLIS * 30)); //aca se setea la fecha de expiracion, esta seteado en 30 minutos a partir de la fecha de creacion
		sdf.applyPattern("yyyy/MM/dd");
		App_user user = app.getByEmail(mail); //se recupera un usuario con un mail igual al entregado en el login

		if(user == null){
			HttpHeaders errorHeaders = new HttpHeaders();
    		errorHeaders.set("error-code", "ERR-AUTH-001");
    		errorHeaders.set("error-desc", "Usuario no existe");
			return new ResponseEntity<JsonObject>(errorHeaders, HttpStatus.UNAUTHORIZED);
		}

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
			HttpHeaders errorHeaders = new HttpHeaders();
    			errorHeaders.set("error-code", "ERR-AUTH-002");
    			errorHeaders.set("error-desc", "Credenciales invalidas");
			return new ResponseEntity<JsonObject>(errorHeaders, HttpStatus.UNAUTHORIZED);
		}
		
	}
	
	
	 @PostMapping("/register")
     public void Register(@RequestBody App_user user , HttpServletResponse resp) {
		 if(auth.RegisterValidation(user)) {
	     app.addUser(user);
	     	resp.setStatus(200);  
	     	Verificationtoken verificationToken = new Verificationtoken(UUID.randomUUID().toString(),user.getAppuser_id());
	     	verificationRepo.save(verificationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("userconfimationjaramillo@gmail.com");
            mailMessage.setText("To confirm your account, please click here : "
            +"http://localhost:8888/user-auth/confirm-account?token="+verificationToken.getToken());

            mailSender.sendEmail(mailMessage);

		 }
		 else
		 {
			resp.setStatus(403);
			
		 }
	 }
	 
	    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
	    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
	    {
	    	Verificationtoken token = verificationRepo.findByToken(confirmationToken);

	        if(token != null)
	        {
	        	Optional<App_user> aux;
	        	aux = Userrepo.findById(token.getApp_userid());
	        	App_user user;
	            user = aux.get();
	            user.setStatus_id("2");
	            app.updateUser(user);
	            verificationRepo.deleteById(token.getToken());
	            modelAndView.setViewName("accountVerified");
	        }
	        else
	        {
	            modelAndView.addObject("message","The link is invalid or broken!");
	            modelAndView.setViewName("error");
	        }

	        return modelAndView;
	    }
	    
	    @RequestMapping(value="/Recover-pass", method= {RequestMethod.GET, RequestMethod.POST},consumes = "application/x-www-form-urlencoded")
	    public void Recuperacion(@RequestParam("email") String email, HttpServletResponse resp)
	    {
	    	if(auth.RecoverPassword(email)) {
	    		resp.setStatus(200);
	    	}
	    	else
	    	{
	    		resp.setStatus(404);
	    	}
	    	
	    }
	
}
