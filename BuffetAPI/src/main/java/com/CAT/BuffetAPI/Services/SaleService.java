package com.CAT.BuffetAPI.Services;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CAT.BuffetAPI.Entities.Sale;
import com.CAT.BuffetAPI.Repositories.SaleRepository;
import com.CAT.BuffetAPI.Repositories.Sale_provisionRepository;

@Service
public class SaleService {

	@Autowired
	SaleRepository saleRepo;
	@Autowired
	Sale_provisionRepository saleProvRepo;
	
	public List<Sale> getAllSale(HashMap< String, Object> data)
	{
		return saleRepo.getData(data);
	}
	
	public void updateSale(Sale sale)
	{
		saleRepo.save(sale);
	}
	
	public void deleteSale(Sale sale)
	{
		saleRepo.delete(sale);
	}
	
	public Optional<Sale> getOneSale(String id) {
		return saleRepo.findById(id);
	}
}
