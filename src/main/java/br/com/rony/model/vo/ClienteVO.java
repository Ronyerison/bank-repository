package br.com.rony.model.vo;

import java.io.Serializable;

public class ClienteVO implements Serializable{
	
	private static final long serialVersionUID = 1396523375377140439L;
	private Long id;
	private String cpf;
	private String nome;
	private Long agencia;
	private String senha;
	
	public ClienteVO() {
		// TODO Auto-generated constructor stub
	}
	
	public ClienteVO(Long id, String cpf, String nome, Long agencia, String senha) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.nome = nome;
		this.agencia = agencia;
		this.senha = senha;
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
	public Long getAgencia() {
		return agencia;
	}
	public void setAgencia(Long agencia) {
		this.agencia = agencia;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "ClienteVO [id=" + id + ", cpf=" + cpf + ", nome=" + nome + ", agencia=" + agencia + ", senha=" + senha
				+ "]";
	}
	
	
	
}
