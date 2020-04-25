package com.CAT.BuffetAPI.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.CAT.BuffetAPI.Entities.Unit;

@RepositoryRestResource
public interface UnitRepository extends JpaRepository<Unit,String> {

}
