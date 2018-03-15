package br.com.rony.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import br.com.rony.model.vo.ClienteVO;

@Entity
public class Cliente implements Serializable{

	private static final long serialVersionUID = 1059005464376904470L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String cpf;
	private String nome;
	
	@OneToMany(mappedBy="cliente", cascade=CascadeType.ALL)
	private List<Conta> contas;
	
	@ManyToOne
	private Agencia agencia;
	
	public Cliente() {
		// TODO Auto-generated constructor stub
	}
	
	public Cliente(ClienteVO clienteVO) {
		this.cpf = clienteVO.getCpf();
		this.nome = clienteVO.getNome();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Conta> getContas() {
		return contas;
	}
	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}
	public Agencia getAgencia() {
		return agencia;
	}
	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}
	
	
}
