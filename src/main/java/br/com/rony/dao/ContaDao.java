package br.com.rony.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.rony.model.Conta;

@RequestScoped
public class ContaDao extends GenericDao<Conta> {

	public ContaDao() {
		this(null);
	}

	@Inject
	public ContaDao(EntityManager entityManager) {
		super(entityManager);
	}

	public Conta getCountByNumber(String numero) {
		Conta conta = null;
		try {
			conta = entityManager.createQuery("select c from Conta c where c.numero like(?1)", Conta.class)
					.setParameter(1, numero).getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		if (conta == null) {
			return null;
		} else {
			return conta;
		}
	}

	public Conta findByNumberAndPassword(String client_id, String client_secret) {

		try {
			Conta conta = entityManager
					.createQuery("select u from Conta u where u.numero = :numero and u.senha = :senha", Conta.class)
					.setParameter("numero", client_id).setParameter("senha", client_secret).getSingleResult();
			return conta;
		} catch (NoResultException e) {
			return null;
		}
	}

	public Conta getCountByNumberAndBank(String origem, String bancoOrigem) {
		try {
			Conta conta = entityManager
					.createQuery("select u from Conta u where u.numero = :numero and u.agencia.banco.codigo = :bancoOrigem", Conta.class)
					.setParameter("numero", origem).setParameter("bancoOrigem", bancoOrigem).getSingleResult();
			return conta;
		} catch (NoResultException e) {
			return null;
		}
	}
}
