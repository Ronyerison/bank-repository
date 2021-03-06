package br.com.rony.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.serialization.gson.WithoutRoot;
import br.com.caelum.vraptor.view.Results;
import br.com.rony.dao.AuthDao;
import br.com.rony.dao.ContaDao;
import br.com.rony.model.Auth;
import br.com.rony.model.Conta;

/**
 * Controlador de login da aplicação
 * @author Rony
 *
 */
@Controller
@Path("/login")
public class LoginController extends BaseController {

	@Inject
	private AuthDao authDao;

//	@Inject
//	private UserDao userDao;

	@Inject 
	private ContaDao contaDao;
	/**
	 * Método que realiza o login na aplicação seguindo protocolo OAuth 2.0.
	 * 
	 * @param client_id - Número da conta do cliente
	 * @param client_secret - Senha da conta do cliente
	 */
	@Consumes(value = {"application/x-www-form-urlencoded", "application/json"}, options = WithoutRoot.class)
	@Post("")
	public void save(String client_id, String client_secret) {

		System.out.println(client_id);
		System.out.println(client_secret);

		Conta contaExistente = this.contaDao.findByNumberAndPassword(client_id, client_secret);

		if (contaExistente != null) {

			// Caso exista autenticacao, irá deletar e criar uma nova

			Auth auth = this.authDao.findByConta(contaExistente);
			if (auth != null) {
				this.authDao.delete(auth);
			}

			// Caso usuário seja válido irá Logar no sistema informando
			// Token de acesso
			// refresh_token
			// client_id
			// expired_at.

			// Iremos criar o access_token, o client_id, e o refresh token utilizando um
			// Hash e gerando o Hash a partir
			// do usuário+senha+nome+timestamp criando assim uma aleatoriedade
			Timestamp dataDeHoje = new Timestamp(System.currentTimeMillis());

			String access_token = client_id.concat(client_secret.concat(dataDeHoje.toString()));

			String refresh_token = client_secret.concat(client_id.concat(dataDeHoje.toString()));

			String client_id2 = dataDeHoje.toString()
					.concat(client_id.concat(client_id.concat(client_secret.concat(contaExistente.getId().toString()))));

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			// O segundo paramentro mostra qnts segundos vc quer adicionar!
			calendar.add(Calendar.SECOND, 3600);

			Date expires_at = calendar.getTime();

			Auth newAuth = new Auth();
			newAuth.setAccess_token(gerarHash(access_token));
			newAuth.setClient_id(gerarHash(client_id2));
			newAuth.setExpires_at(expires_at);
			newAuth.setRefresh_token(gerarHash(refresh_token));

			newAuth.setConta(contaExistente);

			this.authDao.insert(newAuth);

			this.result.use(Results.json()).withoutRoot().from(newAuth).serialize();
		} else {

			this.result.use(Results.json()).withoutRoot().from("access_denied").serialize();
		}

	}
	
	@Get("/negado")
	public void accessoNegado() {
		this.result.use(Results.json()).withoutRoot().from("access_denied").serialize();
	}

	public String gerarHash(String input) {

		MessageDigest md = null;

		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		md.update(input.getBytes());
		String hex = (new HexBinaryAdapter()).marshal(md.digest());
		System.out.println(hex);
		return hex;
	}

}
