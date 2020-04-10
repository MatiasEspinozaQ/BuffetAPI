package com.CAT.BuffetAPI.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.CAT.BuffetAPI.Entities.User_type;

@RepositoryRestResource
public interface userTypeRepository extends JpaRepository<User_type, String>{

}
