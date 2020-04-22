package com.CAT.BuffetAPI.Repositories;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.CAT.BuffetAPI.Entities.App_user;
import com.CAT.BuffetAPI.Entities.Publication;

@RepositoryRestResource
public interface App_UserRepository extends JpaRepository<App_user, String>{
	App_user getByEmail(String email);
	App_user getByUsername(String username);
	
	List<App_user> getData(HashMap<String,Object> Conditions);
}
