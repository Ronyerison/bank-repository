package br.com.rony.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.rony.model.Auth;
import br.com.rony.model.User;

@RequestScoped
public class AuthDao extends GenericDao<Auth> {

	public AuthDao() {
		this(null);
	}

	@Inject
	public AuthDao(EntityManager entityManager) {
		super(entityManager);
	}

	public Auth findByUser(User user) {
		Auth auth = null;
		try {
			auth = entityManager.createQuery("select u from Auth u where u.user = ?1", Auth.class).setParameter(1, user)
					.getSingleResult();
		} catch (NoResultException nre) {

		}

		if (auth == null) {
			return null;
		} else {
			return auth;
		}
	}
}
