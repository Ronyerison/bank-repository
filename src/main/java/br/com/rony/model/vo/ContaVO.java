package br.com.rony.model.vo;

import java.io.Serializable;

public class ContaVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1946322941994441823L;
	
	private Long id;
	private Long numero;
	private Long cliente;
	private Long agencia;
	
	public ContaVO() {
		// TODO Auto-generated constructor stub
	}

	public ContaVO(Long id, Long numero, Long cliente, Long agencia) {
		super();
		this.id = id;
		this.numero = numero;
		this.cliente = cliente;
		this.agencia = agencia;
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

	public Long getCliente() {
		return cliente;
	}

	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}

	public Long getAgencia() {
		return agencia;
	}

	public void setAgencia(Long agencia) {
		this.agencia = agencia;
	}

	@Override
	public String toString() {
		return "ContaVO [id=" + id + ", numero=" + numero + ", cliente=" + cliente + ", agencia=" + agencia + "]";
	}
	
	
}
