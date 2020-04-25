package com.CAT.BuffetAPI.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.CAT.BuffetAPI.Entities.Service_status;

@RepositoryRestResource
public interface ServiceStatusRepository extends JpaRepository<Service_status,String> {


}
