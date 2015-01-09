package br.com.tiago.drone.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.tiago.drone.dao.UserDAO;
import br.com.tiago.drone.model.User;

@Stateless
public class UserDAOImpl implements UserDAO, Serializable {

	private static final long serialVersionUID = -8646938288410458688L;

	@PersistenceContext(name = "test")
	private EntityManager em;

	@Override
	public User findById(Long id) throws Exception {
		try {
			return em.find(User.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public User findByUserName(String userName) throws Exception {
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<User> query = builder.createQuery(User.class);

			Root<User> entity = query.from(User.class);

			query.select(query.from(User.class)).where(
					builder.like(entity.get("userName").as(String.class),
							userName));
			return em.createQuery(query).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<User> findAll() throws Exception {
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<User> query = builder.createQuery(User.class);

			query.select(query.from(User.class));
			return em.createQuery(query).getResultList();
		} catch (Exception e) {
			System.out.println("findAll() - Retornado null...");
			return null;
		}
	}

	@Override
	public void save(User user) throws Exception {
		em.persist(user);
	}

}
