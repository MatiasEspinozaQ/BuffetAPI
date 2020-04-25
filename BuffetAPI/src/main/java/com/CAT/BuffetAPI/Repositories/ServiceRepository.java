package com.CAT.BuffetAPI.Repositories;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.CAT.BuffetAPI.Entities.Product;
import com.CAT.BuffetAPI.Entities.Publication;
import com.CAT.BuffetAPI.Entities.Service;

@RepositoryRestResource
public interface ServiceRepository extends JpaRepository<Service,String> {

	List<Service> getData(HashMap<String,Object> Conditions);


}
