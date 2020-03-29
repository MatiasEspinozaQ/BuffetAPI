package com.CAT.BuffetAPI.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CAT.BuffetAPI.Entities.App_user;
import com.CAT.BuffetAPI.Repositories.appUserRepository;


//CRUD para usuarios, cualquier logica extra debe agregarse en el controlador.
@Service
public class App_UserService {
	
	
	@Autowired
	private appUserRepository appUserRepository;
	
	//Muestra todos los usuarios que no esten eliminados logicamente
	public List<App_user> getAllUsers()
	{
		//Se crea lista que guardara todos los usuarios
		List<App_user> listaUsuarios = new ArrayList<App_user>();
		//Se revisan todos los usuarios y se añaden aquellos donde isDelete tiene valor negativo
		appUserRepository.findAll().forEach(
				p ->{
				if(!p.isIsdelete()) {
					listaUsuarios.add(p);
				}
			}
			);
		return listaUsuarios;
	}
	
	//retorna un usuario en especifico, sino retorna excepcion.
	public Optional<App_user> getAppUser(String id)
	{
		return appUserRepository.findById(id);	
	}
	
	//añade un usuario
	public void addUser(App_user user) {
		appUserRepository.save(user);
	}
	
	//similar al metodo anterior pero esta separado para hacer mas simple futuros cambios.
	public void updateUser(App_user user) {
		
		 appUserRepository.save(user);
	}
	
	//Elimina un usuario.
	public void deleteUser(App_user user) {
		 appUserRepository.delete(user);
	}
}
