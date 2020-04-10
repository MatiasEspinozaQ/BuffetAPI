package com.CAT.BuffetAPI.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.CAT.BuffetAPI.Entities.User_status;

@RepositoryRestResource
public interface userStatusRepository extends JpaRepository<User_status, String>{

}
