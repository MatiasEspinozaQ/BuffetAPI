package com.CAT.BuffetAPI.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.CAT.BuffetAPI.Entities.Public_status;
import com.CAT.BuffetAPI.Entities.Publication;
import com.CAT.BuffetAPI.Entities.User_status;

@RepositoryRestResource
public interface Public_statusRepository extends JpaRepository<Public_status, String>{
	
}
