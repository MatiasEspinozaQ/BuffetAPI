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

import com.CAT.BuffetAPI.Entities.App_user;

public class App_UserRepositoryImpl {

	@PersistenceContext
	private EntityManager em;
	
	
	public List<App_user> getData(HashMap<String, Object> conditions)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<App_user> query= cb.createQuery(App_user.class);
		Root<App_user> root = query.from(App_user.class);
		System.out.println("creada query");
		List<Predicate> predicates = new ArrayList<>();
		conditions.forEach((field,value) ->
		{
			switch (field)
			{
				
				case "username":
					System.out.println("añadido username");
					predicates.add(cb.like(root.get(field),"%"+(String)value+"%"));
					break;
				case "email":
					System.out.println("añadido email");
					predicates.add(cb.like(root.get(field),"%"+(String)value+"%"));
					break;
				case "user_type_id":
					System.out.println("añadido tipo");
					predicates.add(cb.like(root.get(field),"%"+(String)value+"%"));
					break;
				case "status_id":
					System.out.println("añadido status");
					predicates.add(cb.like(root.get(field),"%"+(String)value+"%"));
					break;
				
			}
			
		});
		predicates.add(cb.equal(root.get("deleted"), 0)); //no usuarios eliminados
		System.out.println("añadido no deleteados");
		query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
		System.out.println("Query Lista");
		return em.createQuery(query).getResultList(); 		
	}
}
