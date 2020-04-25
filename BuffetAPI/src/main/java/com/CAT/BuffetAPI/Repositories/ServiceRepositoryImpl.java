package com.CAT.BuffetAPI.Repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.CAT.BuffetAPI.Entities.Product;
import com.CAT.BuffetAPI.Entities.Service;


public class ServiceRepositoryImpl {
	@PersistenceContext
	private EntityManager em;
	
	
	public List<Service> getData(HashMap<String, Object> conditions)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Service> query= cb.createQuery(Service.class);
		Root<Service> root = query.from(Service.class);
		
		List<Predicate> predicates = new ArrayList<>();
		conditions.forEach((field,value) ->
		{
			switch (field)
			{
				
				case "name":
					predicates.add(cb.like(root.get(field),"%"+(String)value+"%"));
					break;
				case "serv_status":
					predicates.add(cb.like(root.get(field),"%"+(String)value+"%"));
					break;
				case "deleted":
					if(value.equals("true"))
					{
					System.out.println("añadido deleted");
					predicates.add(cb.equal(root.get("deleted"), 1));
					break;
					}
					else
					{
						predicates.add(cb.equal(root.get("deleted"),0));
					}
				
			}
			
		});
		query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(query).getResultList(); 		
	}
}
