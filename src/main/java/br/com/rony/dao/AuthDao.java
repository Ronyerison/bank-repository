package br.com.rony.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.rony.model.Auth;
import br.com.rony.model.Conta;

@RequestScoped
public class AuthDao extends GenericDao<Auth> {

	public AuthDao() {
		this(null);
	}

	@Inject
	public AuthDao(EntityManager entityManager) {
		super(entityManager);
	}

	public Auth findByConta(Conta conta) {
		Auth auth = null;
		try {
			auth = entityManager.createQuery("select u from Auth u where u.conta = ?1", Auth.class).setParameter(1, conta)
					.getSingleResult();
		} catch (NoResultException nre) {

		}

		if (auth == null) {
			return null;
		} else {
			return auth;
		}
	}

	public Auth findAuthByAccessToken(String access_token) {
		Auth auth = null;
		try {
			auth = entityManager.createQuery("select u from Auth u where u.access_token = ?1", Auth.class)
					.setParameter(1, access_token).getSingleResult();
		} catch (NoResultException nre) {

		}

		if (auth == null) {
			return null;
		} else {
			return auth;
		}
	}
}
