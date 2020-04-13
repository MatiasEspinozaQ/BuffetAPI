package com.CAT.BuffetAPI.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.CAT.BuffetAPI.Entities.App_user;

@RepositoryRestResource
public interface App_UserRepository extends JpaRepository<App_user, String>{
	App_user getByEmail(String email);
	App_user getByUsername(String username);
}
