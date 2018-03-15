package br.com.rony.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.rony.model.Cliente;

@RequestScoped
public class ClienteDao extends GenericDao<Cliente>{
	
	public ClienteDao() {
		this(null);
	}
	
	@Inject
	public ClienteDao(EntityManager entityManager) {
		super(entityManager);
	}
	
	public Cliente fincByCpf(String cpf) {

		Cliente cliente = null;
		try {
			cliente = entityManager.createQuery("select c from Cliente c where c.cpf like(?1)", Cliente.class)
					.setParameter(1, cpf).getSingleResult();
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
