package com.CAT.BuffetAPI.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CAT.BuffetAPI.Entities.Product_status;
import com.CAT.BuffetAPI.Entities.Sale_status;



public interface Sale_statusRepository extends JpaRepository<Sale_status,String> {

}
