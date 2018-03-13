package br.com.rony.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.rony.enums.TipoTransacao;

@Entity
public class Transacao implements Serializable{

	private static final long serialVersionUID = 2283166381838671533L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private TipoTransacao tipoTransacao;
	private Double valor;
	@ManyToOne
	private Conta contaOrigem;
	@ManyToOne
	private Conta contaDestino;
	
	public Transacao() {
		// TODO Auto-generated constructor stub
	}
	
	public Transacao(TipoTransacao tipoTransacao, Double valor, Conta contaOrigem, Conta contaDestino) {
		super();
		this.tipoTransacao = tipoTransacao;
		this.valor = valor;
		this.contaOrigem = contaOrigem;
		this.contaDestino = contaDestino;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TipoTransacao getTipoTransacao() {
		return tipoTransacao;
	}
	public void setTipoTransacao(TipoTransacao tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Conta getContaOrigem() {
		return contaOrigem;
	}
	public void setContaOrigem(Conta contaOrigem) {
		this.contaOrigem = contaOrigem;
	}
	public Conta getContaDestino() {
		return contaDestino;
	}
	public void setContaDestino(Conta contaDestino) {
		this.contaDestino = contaDestino;
	}
	
	
}
