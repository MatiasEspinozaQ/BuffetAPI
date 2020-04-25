package com.CAT.BuffetAPI.Services;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CAT.BuffetAPI.Entities.Product;
import com.CAT.BuffetAPI.Entities.Product_status;
import com.CAT.BuffetAPI.Entities.Service_status;
import com.CAT.BuffetAPI.Entities.Unit;
import com.CAT.BuffetAPI.Repositories.ProductRepository;
import com.CAT.BuffetAPI.Repositories.ProductStatusRepository;
import com.CAT.BuffetAPI.Repositories.ServiceRepository;
import com.CAT.BuffetAPI.Repositories.ServiceStatusRepository;
import com.CAT.BuffetAPI.Repositories.UnitRepository;



//CRUD para prestaciones, cualquier logica extra debe agregarse en el controlador.
@Service
public class PrestacionesService {


	@Autowired
	private ProductRepository productos;
	@Autowired
	private ServiceRepository servicios;
	@Autowired
	private ProductStatusRepository prodStatus;
	@Autowired
	private ServiceStatusRepository servStatus;
	@Autowired
	private UnitRepository units;
	
	public List<Product> getAllProducts(){
		List<Product> prod = new ArrayList<Product>();
		productos.findAll().forEach(
				p -> {
					if(!p.isDeleted()) {
						prod.add(p);
					}
				}
				);
		return prod;
	}
	
	public Optional<Product> getOneProduct(String id)
	{
		return productos.findById(id);
	}
	
	public Product UpdateProducto(Product product)
	{
		return productos.save(product);
	}
	
	public List<com.CAT.BuffetAPI.Entities.Service> getAllServices(){
		List<com.CAT.BuffetAPI.Entities.Service> serv = new ArrayList<com.CAT.BuffetAPI.Entities.Service>();
		servicios.findAll().forEach(
				p -> {
					if(!p.isDeleted()) {
						serv.add(p);
					}
				}
				);
		return serv;
	}
	
	public Optional<com.CAT.BuffetAPI.Entities.Service> getOneService(String id)
	{
		return servicios.findById(id);
	}
	
	public com.CAT.BuffetAPI.Entities.Service UpdateService(com.CAT.BuffetAPI.Entities.Service service)
	{
		return servicios.save(service);
	}

	public List<Unit> getAllUnits(){
		List<Unit> unidades = new ArrayList<Unit>();
		units.findAll().forEach(
				p -> {
					if(!p.isDeleted()) {
						unidades.add(p);
					}
				}
				);
		return unidades;
		
	}
	
	public List<Product_status> getAllProductStatus(){
		List<Product_status> estados = new ArrayList<Product_status>();
		prodStatus.findAll().forEach(
				p -> {
					if(!p.isDeleted()) {
						estados.add(p);
					}
				}
				);
		return estados;
		
	}
	
	public List<Service_status> getAllservStatus(){
		List<Service_status> estados = new ArrayList<Service_status>();
		servStatus.findAll().forEach(
				p -> {
					if(!p.isDeleted()) {
						estados.add(p);
					}
				}
				);
		return estados;
		
	}
	
	public List<Product> getDataProduct(HashMap data)
	{
		return productos.getData(data);
		
	}
	public List<com.CAT.BuffetAPI.Entities.Service> getDataService(HashMap data)
	{
		return servicios.getData(data);
		
	}
}
