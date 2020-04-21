package com.CAT.BuffetAPI.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CAT.BuffetAPI.Entities.App_user;
import com.CAT.BuffetAPI.Entities.Publication;
import com.CAT.BuffetAPI.Entities.User_status;
import com.CAT.BuffetAPI.Entities.User_type;
import com.CAT.BuffetAPI.Repositories.App_UserRepository;
import com.CAT.BuffetAPI.Repositories.PublicationRepository;
import com.CAT.BuffetAPI.Repositories.userStatusRepository;
import com.CAT.BuffetAPI.Repositories.userTypeRepository;



//CRUD para usuarios, cualquier logica extra debe agregarse en el controlador.
@Service
public class PublicationService {


	@Autowired
	private PublicationRepository pub;


	public List<Publication> getAllPublications(){
		List<Publication> publications = new ArrayList<Publication>();
		pub.findAll().forEach(
				p -> {
					if(!p.isDeleted()) {
						publications.add(p);
					}
				}
				);
		return publications;
	}
	
	public Optional<Publication> getOnePublication(String id)
	{
		return pub.findById(id);
	}
	
	public Publication UpdatePublication(Publication publicacion)
	{
		return pub.save(publicacion);
	}

}
