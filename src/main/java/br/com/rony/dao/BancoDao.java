package br.com.rony.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

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
	
}
