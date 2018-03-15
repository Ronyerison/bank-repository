package br.com.rony.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.view.Results;
import br.com.rony.dao.AgenciaDao;
import br.com.rony.dao.BancoDao;
import br.com.rony.model.Agencia;
import br.com.rony.model.Banco;

@Controller
public class AgenciaController extends BaseController{

	@Inject
	private AgenciaDao agenciaDao;
	
	@Inject 
	private BancoDao bancoDao;
	
	public AgenciaController() {
		super();
	}
	
	@Consumes(value = "application/json")
	@Post("/agencia")
	public void cadastro(Long bancoId, Agencia agencia) {
		try {
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
