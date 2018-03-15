/**
 * 
 */
package br.com.rony.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author Rony
 *
 */
@Entity
public class Agencia implements Serializable{

	private static final long serialVersionUID = 3355114805841611032L;
	
	@Id @GeneratedValue
	private Long id;
	private Long numero;
	private String nome;
	@ManyToOne
	private Banco banco;
	
	@OneToMany(mappedBy="agencia")
	private List<Cliente> clientes;
	
	public Agencia() {
	}

	public Agencia(Long id, Long numero, String nome) {
		super();
		this.id = id;
		this.numero = numero;
		this.nome = nome;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	@Override
	public String toString() {
		return "Agencia [id=" + id + ", numero=" + numero + ", nome=" + nome + "]";
	}
	
	
}
