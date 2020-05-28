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

import com.CAT.BuffetAPI.Entities.Publication;
import com.CAT.BuffetAPI.Entities.Sale;


public class SaleRepositoryImpl {
	@PersistenceContext
	private EntityManager em;
	
	
	public List<Sale> getData(HashMap<String, Object> conditions)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Sale> query= cb.createQuery(Sale.class);
		Root<Sale> root = query.from(Sale.class);
		
		List<Predicate> predicates = new ArrayList<>();
		conditions.forEach((field,value) ->
		{
			switch (field)
			{
				
				case "appuser_id":
					predicates.add(cb.like(root.get(field),"%"+(String)value+"%"));
					break;
				case "cashier_id":
					predicates.add(cb.like(root.get(field),"%"+(String)value+"%"));
					break;
				case "seller_id":
					predicates.add(cb.like(root.get(field),"%"+(String)value+"%"));
					break;
				case "sale_status_id":
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
