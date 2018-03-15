package br.com.rony.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.rony.model.Agencia;

@RequestScoped
public class AgenciaDao extends GenericDao<Agencia>{

	public AgenciaDao() {
		this(null);
	}
	
	@Inject
	public AgenciaDao(EntityManager entityManager) {
		super(entityManager);
	}
	
	
}
