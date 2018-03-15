package br.com.rony.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.rony.model.User;

@RequestScoped
public class UserDao extends GenericDao<User> {

	public UserDao() {
		this(null);
	}

	@Inject
	public UserDao(EntityManager entityManager) {
		super(entityManager);
	}

	public User findByUsername(String username) {

		User user = null;
		try {
			user = entityManager.createQuery("select u from User u where u.username = ?1", User.class)
					.setParameter(1, username).getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		if (user == null) {
			return null;
		} else {
			return user;
		}
	}

	public User findByUsernameAndPassword(String username, String password) {

		User user = null;
		try {
			user = entityManager
					.createQuery("select u from User u where u.username = ?1 and u.password =?2", User.class)
					.setParameter(1, username).setParameter(2, password).getSingleResult();
		} catch (NoResultException nre) {
		}

		if (user == null) {
			return null;
		} else {
			return user;
		}
	}
}
