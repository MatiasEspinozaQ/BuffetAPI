package com.CAT.BuffetAPI.Repositories;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.CAT.BuffetAPI.Entities.Publication;

@RepositoryRestResource
public interface PublicationRepository extends JpaRepository<Publication, String>{
	
	List<Publication> getData(HashMap<String,Object> Conditions);
}
