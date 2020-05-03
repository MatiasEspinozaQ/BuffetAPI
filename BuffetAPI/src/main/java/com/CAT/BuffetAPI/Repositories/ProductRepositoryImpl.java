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


public class ProductRepositoryImpl {
	@PersistenceContext
	private EntityManager em;
	
	
	public List<Product> getData(HashMap<String, Object> conditions)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Product> query= cb.createQuery(Product.class);
		Root<Product> root = query.from(Product.class);
		
		List<Predicate> predicates = new ArrayList<>();
		conditions.forEach((field,value) ->
		{
			switch (field)
			{
				case "brand":
					predicates.add(cb.like(root.get(field),"%"+(String)value+"%"));
					break;
				case "name":
					predicates.add(cb.like(root.get(field),"%"+(String)value+"%"));
					break;
				case "product_status":
					predicates.add(cb.like(root.get(field),"%"+(String)value+"%"));
					break;
				case "deleted":
					if(value.equals("true"))
						predicates.add(cb.equal(root.get("deleted"), 1));
					else
						predicates.add(cb.equal(root.get("deleted"),0));
					break;
			}
		});
		query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(query).getResultList();
	}
}
