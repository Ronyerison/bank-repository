package br.com.rony.model;

import java.util.List;

import javax.persistence.CascadeType;
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
	
	private String numero;
	
	private Double saldo;
	
	@ManyToOne
	private Agencia agencia;
	
	@ManyToOne(cascade= {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH})
	private Cliente cliente;
	
	@OneToMany
	private List<Transacao> transacoes;
	
	public Conta() {
		this.saldo = 0.0;
	}

	public Conta(String numero, Agencia agencia, Cliente cliente) {
		super();
		this.numero = numero;
		this.agencia = agencia;
		this.cliente = cliente;
		this.saldo = 0.0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
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

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	
	
}
