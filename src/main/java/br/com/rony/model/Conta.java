package br.com.rony.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Conta {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Long numero;
	@ManyToOne
	private Agencia agencia;
	@ManyToOne
	private Cliente cliente;
	@OneToMany
	private List<Transacao> transacoes;
	
	public Conta() {
		// TODO Auto-generated constructor stub
	}

	public Conta(Long numero, Agencia agencia, Cliente cliente) {
		super();
		this.numero = numero;
		this.agencia = agencia;
		this.cliente = cliente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Transacao> getTransacoes() {
		return transacoes;
	}

	public void setTransacoes(List<Transacao> transacoes) {
		this.transacoes = transacoes;
	}
	
	
}
