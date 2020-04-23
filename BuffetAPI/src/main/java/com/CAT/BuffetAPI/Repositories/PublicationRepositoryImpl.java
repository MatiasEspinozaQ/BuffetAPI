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


public class PublicationRepositoryImpl {
	@PersistenceContext
	private EntityManager em;
	
	
	public List<Publication> getData(HashMap<String, Object> conditions)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Publication> query= cb.createQuery(Publication.class);
		Root<Publication> root = query.from(Publication.class);
		
		List<Predicate> predicates = new ArrayList<>();
		conditions.forEach((field,value) ->
		{
			switch (field)
			{
				
				case "region":
					predicates.add(cb.like(root.get(field),"%"+(String)value+"%"));
					break;
				case "public_status_id":
					predicates.add(cb.like(root.get(field),"%"+(String)value+"%"));
					break;
				case "title":
					predicates.add(cb.like(root.get(field),"%"+(String)value+"%"));
					break;
				case "bussiness_name":
					predicates.add(cb.like(root.get(field),"%"+(String)value+"%"));
					break;
				
			}
			
		});
		query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
		return em.createQuery(query).getResultList(); 		
	}
}
