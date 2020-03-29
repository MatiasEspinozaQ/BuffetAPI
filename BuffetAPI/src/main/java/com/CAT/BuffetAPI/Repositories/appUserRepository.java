package com.CAT.BuffetAPI.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.CAT.BuffetAPI.Entities.App_user;

@RepositoryRestResource
public interface appUserRepository extends JpaRepository<App_user, String>{

}
