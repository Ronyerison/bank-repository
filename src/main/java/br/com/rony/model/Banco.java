package br.com.rony.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Classe modelo para representar um banco
 * @author Rony
 *
 */
@Entity
public class Banco implements Serializable{

	private static final long serialVersionUID = -6589534307839802677L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String codigo;
	
	@OneToMany(mappedBy="banco")
	private List<Agencia> agencias;
	
	public Banco() {
	}

	public Banco(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public Banco(Long id, String nome, String codigo) {
		super();
		this.id = id;
		this.nome = nome;
		this.codigo = codigo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Agencia> getAgencias() {
		return agencias;
	}

	public void setAgencias(List<Agencia> agencias) {
		this.agencias = agencias;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public String toString() {
		return "Banco [id=" + id + ", nome=" + nome + ", codigo=" + codigo + "]";
	}

	
}
