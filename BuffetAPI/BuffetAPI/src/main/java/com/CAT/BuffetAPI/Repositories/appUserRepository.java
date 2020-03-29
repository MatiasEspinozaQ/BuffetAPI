package com.CAT.BuffetAPI.Repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.CAT.BuffetAPI.Entities.App_user;

@RepositoryRestResource
public interface appUserRepository extends CrudRepository<App_user, String>{

}
