package br.com.rony.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

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
	
	public List<Transacao> buscarTransacoesPorConta(Long contaId){
		List<Transacao> cliente = null;
		try {
			cliente = entityManager.createQuery("select t from Transacao t where t.contaOrigem.id = ?1 or t.contaDestino.id = ?1", Transacao.class)
					.setParameter(1, contaId).getResultList();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		if (cliente == null) {
			return null;
		} else {
			return cliente;
		}
	}
}
