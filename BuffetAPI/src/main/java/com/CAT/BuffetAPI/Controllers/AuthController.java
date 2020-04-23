package com.CAT.BuffetAPI.Controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletResponse;

import com.CAT.BuffetAPI.Entities.App_user;
import com.CAT.BuffetAPI.Entities.Verificationtoken;
import com.CAT.BuffetAPI.Repositories.App_UserRepository;
import com.CAT.BuffetAPI.Repositories.VerificationTokenRepository;
import com.CAT.BuffetAPI.Services.App_UserService;
import com.CAT.BuffetAPI.Services.AuthService;
import com.CAT.BuffetAPI.Services.EmailSenderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
		App_user user = app.getByUsername(mail); //se recupera un usuario con un mail igual al entregado en el login

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
			user.setLastlogin(createdat);
			app.updateUser(user);
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
	public String Register(@RequestBody App_user user , HttpServletResponse resp) {
		// Valida si existe el mail y username del nuevo APP_USER
		if(auth.RegisterValidation(user)) {
			// Setea datos generales
			user.setMail_confirmed(false);
			user.setUpdated_at(new Date());
			user.setCreated_at(new Date());
			// Agrega al Usuario a la BD. 
			// Al insertar, appuser_id se asigna automaticamente, así que hay que redefinirlo al que se creó
			user = app.addUser(user);

			// !BUG: Si hay cualquier atado insertando el VerificationToken a la BD, devuelve status 500 pero se crea el Ususario
			// Crea UUID sacando los guiones para cumplir requisitos de BD
			String tokenId = UUID.randomUUID().toString().replace("-", "");
			// Crea un nuevo VerificationToken con el token y el id del usuario
			Verificationtoken verificationToken = new Verificationtoken(tokenId, user.getAppuser_id());
			// Lo guarda en la BD
			verificationRepo.save(verificationToken);

			// Envia un mail al nuevo usuario con el token de verificación
			// TODO Que mande a Initial-D para que muestre un mensaje boni o algo
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(user.getEmail());
			mailMessage.setSubject("Complete Registration!");
			mailMessage.setFrom("userconfimationjaramillo@gmail.com");
			mailMessage.setText("To confirm your account, please click here : "
					+"http://localhost:8888/user-auth/confirm-account?token="+verificationToken.getToken());

			mailSender.sendEmail(mailMessage);

			// Status 200 y retorna el Id del APP_USER nuevo
			resp.setStatus(200);
			return user.getAppuser_id();
		}
		else
		{
			// 409 Conflict
			resp.setStatus(409);
			return "Nombre de Usuario o Mail ya existe";
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
			user.setMail_confirmed(true);
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

	private void log(String msg){
		System.out.println ("----------------------------------------------------------------");
		System.out.println (msg);
		System.out.println ("----------------------------------------------------------------");
	}

	// TODO Agregar todos los tipos de usuario como permitidos
	// TODO Recibir contraseña antigua también y comprobar que calce
	@RequestMapping(value = "/change-password", method = {RequestMethod.POST})
	private String ChangePassword(HttpServletResponse res, @RequestBody Map<String, String> passwords,@RequestHeader("token") String token)
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
			// Get the Id from the Token
			String Id = auth.extractId(token);

			// Get the User
			Optional<App_user> user = app.getAppUser(Id);

			// If there is no matching User
			if(!user.isPresent()){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}

			App_user updateUser = user.get();
			
			// Se asegura que la contraseña antigua calza con la del usuario
			String oldPsw = passwords.get("old_psw");
			if(updateUser.getHash().equals(oldPsw)){
				// 409 Conflict
				res.setStatus(409);
				return null;
			}

			// Cambia la contraseña
			String psw = passwords.get("psw");
			updateUser.setHash(psw);
			app.updateUser(updateUser);

			// 200 OK
			res.setStatus(200);
			return "Contraseña cambiada exitosamente";

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}

}
