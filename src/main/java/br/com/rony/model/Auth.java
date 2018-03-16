package br.com.rony.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Auth implements Serializable{
		
	private static final long serialVersionUID = -3959770240092900505L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String client_id;
    private String refresh_token;
    private String access_token;
    private Date expires_at;
    
    @OneToOne
    private Conta conta;

    public Integer getId()
    {
       return this.id;
    }

    public void setId(Integer id)
    {
       this.id = id;
    }
	
	public String getClient_id() {
		return client_id;
	}
	
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	
	public String getRefresh_token() {
		return refresh_token;
	}
	
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	
	public String getAccess_token() {
		return access_token;
	}
	
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
	public Date getExpires_at() {
		return expires_at;
	}
	
	public void setExpires_at(Date expires_at) {
		this.expires_at = expires_at;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	@Override
	public String toString() {
		return "Auth [id=" + id + ", client_id=" + client_id + ", refresh_token=" + refresh_token + ", access_token="
				+ access_token + ", expires_at=" + expires_at + ", conta=" + conta + "]";
	}

}
