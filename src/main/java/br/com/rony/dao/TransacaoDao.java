package br.com.rony.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.rony.model.Transacao;

@RequestScoped
public class TransacaoDao extends GenericDao<Transacao>{
	
	public TransacaoDao() {
		this(null);
	}
	
	@Inject
	public TransacaoDao(EntityManager entityManager) {
		super(entityManager);
	}
}
