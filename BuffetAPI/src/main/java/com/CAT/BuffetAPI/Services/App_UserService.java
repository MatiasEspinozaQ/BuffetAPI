package com.CAT.BuffetAPI.Services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.CAT.BuffetAPI.Entities.App_user;
import com.CAT.BuffetAPI.Entities.VerificationToken;
import com.CAT.BuffetAPI.Repositories.App_UserRepository;
import com.CAT.BuffetAPI.Repositories.VerificationTokenRepository;



//CRUD para usuarios, cualquier logica extra debe agregarse en el controlador.
@Service
public class App_UserService {
	
	
	@Autowired
	private App_UserRepository app_UserRepository;
	@Autowired
    private VerificationTokenRepository tokenRepository;
 
	//Muestra todos los usuarios que no esten eliminados logicamente
	public List<App_user> getAllUsers()
	{
		//Se crea lista que guardara todos los usuarios
		List<App_user> listaUsuarios = new ArrayList<App_user>();
		//Se revisan todos los usuarios y se añaden aquellos donde isDelete tiene valor negativo
		app_UserRepository.findAll().forEach(
				p ->{
				if(!p.isIsdelete()) {
					listaUsuarios.add(p);
				}
			}
			);
		return listaUsuarios;
	}
	
	public List<App_user> getAllMecha()
	{
		List<App_user> meca = new ArrayList<App_user>();
		app_UserRepository.findAll().forEach(u ->{
			if( u.getUser_type_id().equals("6"))
			meca.add(u);	
		}
	);
		return meca;
	}
	
	public App_user getByEmail(String email)
	{
		 List<App_user> losMismisimos = new ArrayList<App_user>();
		 Iterator<App_user> it = losMismisimos.iterator();
		 App_user elMismisimo = null;
		app_UserRepository.findAll().forEach(u->{losMismisimos.add(u);});
	
		for(App_user user:losMismisimos) {
			if(user.getEmail().equals(email)) {
			elMismisimo = user;
			break;
			}
		}
		
			return elMismisimo;
	}


	//retorna un usuario en especifico, sino retorna excepcion.
	public Optional<App_user> getAppUser(String id)
	{
		return app_UserRepository.findById(id);	
	}
	
	//añade un usuario
	public void addUser(App_user user) {
		app_UserRepository.save(user);
	}
	
	//similar al metodo anterior pero esta separado para hacer mas simple futuros cambios.
	public void updateUser(App_user user) {
		
		 app_UserRepository.save(user);
	}
	
	//Elimina un usuario.
	public void deleteUser(App_user user) {
		 app_UserRepository.delete(user);
	}
	
	 public void createVerificationToken(App_user user, String token) {
	        VerificationToken myToken = new VerificationToken(token, user);
	        tokenRepository.save(myToken);
	    }
}
