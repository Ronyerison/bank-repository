package br.com.rony.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.rony.model.Banco;

@RequestScoped
public class BancoDao extends GenericDao<Banco>{

	public BancoDao() {
		this(null);
	}
	
	@Inject
	public BancoDao(EntityManager entityManager) {
		super(entityManager);
	}
	
	public Banco getByNumber(String numero) {
		Banco banco = null;
		try {
			banco = entityManager.createQuery("select c from Banco c where c.codigo like(?1)", Banco.class)
					.setParameter(1, numero).getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		if (banco == null) {
			return null;
		} else {
			return banco;
		}
	}

	
}
