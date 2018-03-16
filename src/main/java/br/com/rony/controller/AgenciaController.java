package br.com.rony.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.view.Results;
import br.com.rony.dao.AgenciaDao;
import br.com.rony.dao.BancoDao;
import br.com.rony.model.Agencia;
import br.com.rony.model.Banco;

/**
 * 
 * Controlador para gerenciar as requisições para agencia
 * @author Rony
 *
 */
@Controller
@Path("/agencia")
public class AgenciaController extends BaseController{

	@Inject
	private AgenciaDao agenciaDao;
	
	@Inject 
	private BancoDao bancoDao;
	
	public AgenciaController() {
		super();
	}
	
	/**
	 * Método para cadastrar uma nova agencia de uma instituição financeira
	 * @param bancoId - identificador do banco
	 * @param agencia - uma agencia para ser cadastrada
	 */
	@Consumes(value = "application/json")
	@Post("")
	public void cadastro(Long bancoId, Agencia agencia) {
		try {
			//verificando se o banco existe
			Banco banco = bancoDao.find(bancoId);
			if(banco != null) {
				agencia.setBanco(banco);
				agenciaDao.insert(agencia);
				addSucessMessage("Agencia cadastrada com sucesso!");
			}else {
				addErrorMessage("Problema ao salvar agencia, banco não cadastrado!");
			}
		}catch (Exception e) {
			e.printStackTrace();
			addErrorMessage("Erro ao salvar agencia, Problema Interno!");
		}
		result.use(Results.json()).withoutRoot().from(messages).serialize();
	}
}
