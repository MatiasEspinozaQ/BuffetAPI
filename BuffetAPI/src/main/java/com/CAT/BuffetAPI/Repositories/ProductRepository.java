package com.CAT.BuffetAPI.Repositories;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.CAT.BuffetAPI.Entities.Product;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product,String> {
	List<Product> getData(HashMap<String,Object> Conditions);
}
