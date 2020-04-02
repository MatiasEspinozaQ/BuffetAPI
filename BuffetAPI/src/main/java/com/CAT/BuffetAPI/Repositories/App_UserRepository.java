package com.CAT.BuffetAPI.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.CAT.BuffetAPI.Entities.App_user;
import com.CAT.BuffetAPI.Entities.User_type;

@RepositoryRestResource
public interface App_UserRepository extends JpaRepository<App_user, String>{

	
}
