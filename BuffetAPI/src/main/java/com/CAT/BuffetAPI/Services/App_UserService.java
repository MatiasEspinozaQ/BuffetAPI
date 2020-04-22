package com.CAT.BuffetAPI.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CAT.BuffetAPI.Entities.App_user;
import com.CAT.BuffetAPI.Entities.User_status;
import com.CAT.BuffetAPI.Entities.User_type;
import com.CAT.BuffetAPI.Repositories.App_UserRepository;
import com.CAT.BuffetAPI.Repositories.userStatusRepository;
import com.CAT.BuffetAPI.Repositories.userTypeRepository;



//CRUD para usuarios, cualquier logica extra debe agregarse en el controlador.
@Service
public class App_UserService {


	@Autowired
	private App_UserRepository app_UserRepository;
	 @PersistenceContext
     private EntityManager em;

	//Muestra todos los usuarios que no esten eliminados logicamente
	public List<App_user> getAllUsers()
	{
		//Se crea lista que guardara todos los usuarios
		List<App_user> listaUsuarios = new ArrayList<App_user>();

		//Se revisan todos los usuarios y se añaden aquellos donde isDelete tiene valor negativo
		app_UserRepository.findAll().forEach( p ->{
			if(!p.isDeleted()) {
				listaUsuarios.add(p);
			}
		});

		return listaUsuarios;
	}

	public List<App_user> getAllDeleted()
	{
		//Se crea lista que guardara todos los usuarios
		List<App_user> listaUsuarios = new ArrayList<App_user>();
		//Se revisan todos los usuarios y se añaden aquellos donde isDelete tiene valor negativo
		app_UserRepository.findAll().forEach(
				p ->{
					if(p.isDeleted()) {
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
			if( u.getUser_type_id().equals("MEC") && !u.isDeleted())
			{	
				meca.add(u);
			}
		}
		);
		return meca;
	}

	public App_user getByEmail(String email)
	{
		return app_UserRepository.getByEmail(email);
	}

	public App_user getByUsername(String username)
	{
		return app_UserRepository.getByUsername(username);
	}

	//retorna un usuario en especifico, sino retorna excepcion.
	public Optional<App_user> getAppUser(String id)
	{
		return app_UserRepository.findById(id);	
	}

	//añade un usuario
	public App_user addUser(App_user user) {
		return app_UserRepository.save(user);
	}

	//similar al metodo anterior pero esta separado para hacer mas simple futuros cambios.
	public void updateUser(App_user user) {
		app_UserRepository.save(user);
	}

	//Elimina un usuario.
	public void deleteUser(App_user user) {
		app_UserRepository.delete(user);
	}


	@Autowired
	private userTypeRepository user_appRepository;

	public List<User_type> getAllTypes(){
		List<User_type> typeList = new ArrayList<User_type>();
		user_appRepository.findAll().forEach(
				p -> {
					if(!p.isDeleted()) {
						typeList.add(p);
					}
				}
				);
		return typeList;
	}


	@Autowired
	private userStatusRepository user_statusRepository;

	public List<User_status> getAllStatus(){
		List<User_status> statusList = new ArrayList<User_status>();
		user_statusRepository.findAll().forEach(
				p -> {
					if(!p.isDeleted()) {
						statusList.add(p);
					}
				}
				);
		return statusList;
	}

}
